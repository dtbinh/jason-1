from pox.core import core
from pox.lib.addresses import * 
from pox.lib.packet import *
from pox.lib.recoco.recoco import *
import fileinput
import re
# Get a logger
log = core.getLogger("fw")

class Firewall (object):
  """
  Firewall class.
  Extend this to implement some firewall functionality.
  Don't change the name or anything -- the eecore component
  expects it to be firewall.Firewall.
  """
  def __init__(self):
    """
    Constructor.
    Put your initialization code here.
    """
    self.debug = True
    self.debug2 = True
    self.banned_ports = set()
    for line in fileinput.input('/root/pox/ext/banned-ports.txt'):
        portNumber = int(line.rstrip())
        log.debug(portNumber)
        self.banned_ports.add(portNumber)
    self.banned_domains = set()
    for line in fileinput.input('/root/pox/ext/banned-domains.txt'):
        domain = str(line.rstrip()) # Return a copy of the string with trailing characters removed. 
        log.debug(domain)
        self.banned_domains.add(domain)
    self.monitered_strings = set() # (address, search_string)
    for line in fileinput.input('/root/pox/ext/monitored-strings.txt'):
        temp = str(line.rstrip())
        address, search_string = temp.split(':')
        address = str(address)
        search_string = str(search_string)
        log.debug(address + ':' + search_string)
        self.monitered_strings.add((address, search_string))
    self.counts = dict() # key: (address, search_string, srcport, dstport), value: number of times the search_string appears
    self.countsIncomingbuffer = dict() # key: (address, srcport, dstport), value: string (initialized to empty string) 
    self.countsOutgoingbuffer = dict() # key: (address, srcport, dstport), value: string (initialized to empty string)
    self.countsBuffetSize = dict() # key: address, value: len(LongestString) -1
    self.timers = dict() # a timer for each TCP connection. key: (address, srcport, dstport) Value: Timers (initialized to 30.0 seconds)
    self.timersStatus = dict() # a status of timer for each TCP connection key: same as above Value: True when timer is on, false otherwise
    log.debug("Firewall initialized.")
    self.timerInitiated = 0
    
  def _handle_ConnectionIn (self, event, flow, packet):
    """
    New connection event handler.
    You can alter what happens with the connection by altering the
    action property of the event.
    """
    
    if flow.dstport in self.banned_ports:
        log.debug("Denied Connection [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
        event.action.deny = True
        return
    forward = True
    dst_address = str(flow.dst) # the IP Address for destination
    longestString = 0
    """ cancel the timer if timer exists on this address, srcport, dstport (this is when disconnect occurs and hasn't been timed out yet"""
    if (dst_address, int(flow.srcport), int(flow.dstport)) in self.timersStatus.keys():
        if self.timersStatus[(dst_address, int(flow.srcport), int(flow.dstport))]:
            self.timers[(dst_address, int(flow.srcport), int(flow.dstport))].cancel()
            self.writeToFile(dst_address, int(flow.srcport), int(flow.dstport)) 
    
    for address, search_string in self.monitered_strings:
        if dst_address == address:
            log.debug(address + ':' + search_string + ":" + str(flow.dstport))
            self.counts[(address, search_string, int(flow.srcport), int(flow.dstport))] = 0
            if len(search_string)>longestString:
                longestString = len(search_string)
                self.countsBuffetSize[address] = longestString
            log.debug("1." + address + ":" + str(flow.dstport) + ":" + str(flow.srcport))
            self.countsIncomingbuffer[(address, int(flow.dstport), int(flow.srcport))] = "" # set incoming buffer and outgoing buffer to empty string
            self.countsOutgoingbuffer[(address, int(flow.srcport), int(flow.dstport))] = "" 
            log.debug("2." + address + ":" + str(flow.dstport) + ":" + str(flow.srcport))
            forward = False
    log.debug("Longest string is" + str(longestString))
    if forward:
        if flow.dstport == 80:
            log.debug("Deferred connection [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
            event.action.defer = True
            return
        log.debug("Allowed connection [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
        event.action.forward = True
        return
    else:
        """ initiate timer on this address/port again"""
        self.timers[(dst_address, int(flow.srcport), int(flow.dstport))] = Timer(30.0, self.writeToFile, args=(dst_address, int(flow.srcport), int(flow.dstport)))
        log.debug("timer started...")
        self.timerInitiated += 1
        self.timersStatus[(dst_address, int(flow.srcport), int(flow.dstport))] = True
        log.debug("Deferred monitored connection [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
        event.action.defer = True
        
   
        
        
      
    
  def _handle_DeferredConnectionIn (self, event, flow, packet):
    """
    Deferred connection event handler.
    If the initial connection handler defers its decision, this
    handler will be called when the first actual payload data
    comes across the connection.
    """

    
    """ check for banned_domains here"""
    forward = True
    log.debug("handle deferred connection")
    user=re.compile("Host: (.*?)\r") # consulted from http://stackoverflow.com/questions/10832974/python-regular-expression-for-http-request-header
    host = user.findall(packet.payload.payload.payload)
    if (host[0] in self.banned_domains):
        log.debug(host[0])
        forward = False
    for banned_domains in self.banned_domains:
        if banned_domains in host[0]:
            log.debug(host[0])
            forward = False 
    if forward:
        log.debug("Allowed Connection2 [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
        for monitored_address, search_string in self.monitered_strings:
            if str(flow.dst) == monitored_address:
                log.debug("MoniteredConnection")
                event.action.monitor_forward = True
                event.action.monitor_backward = True
        event.action.forward = True
    else:
        log.debug("Denied Connection2 [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
        event.action.deny = True
    
    
  def _handle_MonitorData (self, event, packet, reverse):
    """
    Monitoring event handler.
    Called when data passes over the connection if monitoring
    has been enabled by a prior event handler.
    """
    """ for every port in every pair of src_destination, we need a buffer for income and another for outgoing"""
    
    
    srcport = packet.payload.payload.srcport # srcport in TCP Header
    dstport = packet.payload.payload.dstport # dstport in TCP Header
    srcport = int(srcport)
    dstport = int(dstport)
    srcip = packet.payload.srcip 
    srcip = str(srcip)
    dstip = packet.payload.dstip
    dstip = str(dstip)
    data = packet.payload.payload.payload
    if self.debug:
        log.debug(data)
        self.debug =False
    #log.debug(str(srcport) + " : " + str(dstport) + " : " + srcip + " : " + dstip)
    if reverse: # for incoming packet/data
        """ shut off the timer first"""
        if not self.timersStatus[(srcip, dstport, srcport)]:
            log.debug("reverse-Timed Out already!!!, should already be writing to file/this connection is closed- please re-establish connection again...")
            return
        self.timers[(srcip, dstport, srcport)].cancel()
        buffered = str(self.countsIncomingbuffer[(srcip, srcport, dstport)])
        data = buffered + data
        log.debug("transfered back to :" + str(dstport))
        for ip, search_string in self.monitered_strings:
            if ip == srcip:
                number = data.count(search_string)
                self.counts[(ip, search_string, dstport, srcport)] += number
        for ip, search_string in self.monitered_strings:
            if ip == srcip:
                number = buffered.count(search_string)
                self.counts[(ip, search_string, dstport, srcport)] -= number
        bufferLength = self.countsBuffetSize[srcip]
        if len(data) < bufferLength:
            bufferedData = data
        else:
            bufferedData = data[len(data)-bufferLength:len(data)]
        self.countsIncomingbuffer[(srcip, srcport, dstport)] = bufferedData
        data = "" # save space/memory
        """ start up the timer again"""
        self.timers[(srcip, dstport, srcport)] = Timer(30.0, self.writeToFile, args=(srcip, dstport, srcport))

        log.debug("successfully runned incoming")
    else: # for outgoing packet/data
        """ shut off the timer first"""
        if not self.timersStatus[(dstip, srcport, dstport)]:
            log.debug("Timed Out Already!!!, should already be writing to file/this connection is closed- please re-establish connection again...")
            return
        log.debug(data)
        self.timers[(dstip, srcport, dstport)].cancel()
        buffered = str(self.countsOutgoingbuffer[(dstip, srcport, dstport)])
        data = buffered + data
        log.debug("transfered forward to :" + str(dstport))

        for ip, search_string in self.monitered_strings:
            if ip == dstip:
                number = data.count(search_string)
                self.counts[(dstip, search_string, srcport, dstport)] += number
        for ip, search_string in self.monitered_strings:
            if ip == dstip:
                number = buffered.count(search_string)
                self.counts[(dstip, search_string, srcport, dstport)] -= number
                log.debug([dstip, search_string, srcport, dstport])
        bufferLength = self.countsBuffetSize[dstip]
        if len(data) < bufferLength:
            bufferedData = data
        else:
            bufferedData = data[len(data)-bufferLength:len(data)]
        self.countsOutgoingbuffer[(dstip, srcport, dstport)] = bufferedData
        data = "" # save space/memory
        
            
        """ start up the timer again """
        self.timers[(dstip, srcport, dstport)] =  Timer(30.0, self.writeToFile, args=(dstip, srcport, dstport))
        log.debug("successfully runned outgoing")
        
  def writeToFile(self, address, srcport, dstport):
      """ time to write to file!!!!!! and the project should be done after this:)"""
      # open a counts.txt file
      """ clean the buffer"""
      self.countsIncomingbuffer[(address, dstport, srcport)] = ""
      self.countsOutgoingbuffer[(address, srcport, dstport)] = ""
      
      self.timerInitiated -=1
      if self.timerInitiated < 0:
          log.debug("Something went wrong during the connection!! please check your connection again...")
          return
      self.timersStatus[(address, srcport, dstport)] = False
      log.debug("timer if off!!!!!")
      fo = open('/root/pox/ext/counts.txt', 'a')
      search_set = set()
      
      for ip_address, search_string in self.monitered_strings:
          if ip_address == address:
              search_set.add(search_string)
      for search_string in search_set:
          count = self.counts[(address, search_string, srcport, dstport)]
          log.debug(search_string)
          self.counts[(address, search_string, srcport, dstport)] = 0
          fo.write(str(address) + "," + str(dstport) + "," + search_string + "," + str(count) + "\n")
          fo.flush()
      fo.close()
      log.debug("done writing.....")
      log.debug("timer initiated: " + str(self.timerInitiated))
    
        
    
    

