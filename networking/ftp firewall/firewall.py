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
    self.ftpAddress = {} # Key: Destaddress, Value: List of allowed ports
    self.timers = {} # key: (Destaddress, dataPorts), Value: Timer
    self.buffer = {} # key: srcip, Value: the buffer
    log.debug("Firewall initialized.")
  def _handle_ConnectionIn (self, event, flow, packet):
    """
    New connection event handler.
    You can alter what happens with the connection by altering the
    action property of the event.
    """
    if int(flow.dstport) >= 0 and int(flow.dstport) <= 1023:
      if int(flow.dstport) == 21:
          if self.buffer.has_key((str(flow.dst), int(flow.srcport))):
              del self.buffer[(str(flow.dst), int(flow.srcport))]
          log.debug("ftp connection")
          event.action.monitor_backward = True
          return
      log.debug("Allowed connection [" + str(flow.src) + ":" + str(flow.srcport) + "," + str(flow.dst) + ":" + str(flow.dstport) + "]" )
      event.action.forward = True
    else:
        if self.ftpAddress.has_key(str(flow.dst)):
            if int(flow.dstport) in self.ftpAddress[str(flow.dst)]:
                if self.timers.has_key((str(flow.dst), int(flow.dstport))):
                    if len(self.timers[(str(flow.dst), int(flow.dstport))]) >0:
                        self.timers[(str(flow.dst), int(flow.dstport))][0].cancel()
                        self.timers[(str(flow.dst), int(flow.dstport))].pop(0)
                    if len(self.timers[(str(flow.dst), int(flow.dstport))]) ==0:
                        del self.timers[(str(flow.dst), int(flow.dstport))]
                self.ftpAddress[str(flow.dst)].remove(int(flow.dstport))
                event.action.forward = True
                log.debug(self.ftpAddress)
                log.debug("ftp data connection established on port: " + str(flow.dstport))
                return
        event.action.deny = True
        

  def _handle_DeferredConnectionIn (self, event, flow, packet):
    """
    Deferred connection event handler.
    If the initial connection handler defers its decision, this
    handler will be called when the first actual payload data
    comes across the connection.
    """
    pass
    
  def _handle_MonitorData (self, event, packet, reverse):
    """
    Monitoring event handler.
    Called when data passes over the connection if monitoring
    has been enabled by a prior event handler.
    """
    srcport = packet.payload.payload.srcport # srcport in TCP Header
    dstport = packet.payload.payload.dstport # dstport in TCP Header
    srcport = int(srcport)
    dstport = int(dstport)
    srcip = packet.payload.srcip 
    srcip = str(srcip)
    dstip = packet.payload.dstip
    dstip = str(dstip)
    data = str(packet.payload.payload.payload)
    
    if srcport == 21:
        if "\n" in data:
            if self.buffer.has_key((srcip, dstport)):
                temp = self.buffer[(srcip, dstport)]
                data = temp + data
            splitData = data.split("\n")
            temp = splitData[-1]
            self.buffer[(srcip, dstport)] = temp
            i = 0
            while i < len(splitData)-1:
                temp = splitData[i]
                if len(temp) > 8:
                    self.checkPASVandEPSV(srcip, temp)
                i+=1
        else:
            if self.buffer.has_key((srcip, dstport)):
                temp = self.buffer[(srcip, dstport)]
                temp = temp + data
                self.buffer[(srcip, dstport)] = temp
            else:
                self.buffet[(srcip, dstport)] = data
        

    event.action.forward = True
    #log.debug("Monitored connection [" + srcip + ":"+ str(srcport) + "," + dstip + ":" + str(dstport))
    
    
  def checkPASVandEPSV(self, srcip, data):
    if "229" in data[:3]:
        log.debug(data)
        p = re.compile('\d+')
        numbers = p.findall(data)
        portnum = int(numbers[len(numbers)-1])
        if self.ftpAddress.has_key(srcip):
            self.ftpAddress[srcip].append(portnum)
        else:
            self.ftpAddress[srcip]= []
            self.ftpAddress[srcip].append(portnum)
        self.setTimer(srcip, portnum)
        log.debug(self.ftpAddress)
        log.debug("229 successful")
    if "227" in data[:3]:
        log.debug(data)
        p = re.compile('\d+')
        numbers = p.findall(data)
        octet1 = p.findall(data)[-2]
        octet2 = p.findall(data)[-1]
        portnum = int(octet1)*256 + int(octet2)
        if self.ftpAddress.has_key(srcip):
            self.ftpAddress[srcip].append(portnum)
        else:
            self.ftpAddress[srcip]= []
            self.ftpAddress[srcip].append(portnum)
        self.setTimer(srcip, portnum)
        log.debug(self.ftpAddress)
        log.debug("227 successful")
        
  def setTimer(self, destAddress, dataPort):
     if self.timers.has_key((destAddress, dataPort)):
         self.timers[(destAddress, dataPort)].append(Timer(10.0, self.timeoutFunc, args=(destAddress, dataPort)))
     else:
         self.timers[(destAddress, dataPort)] = []
         self.timers[(destAddress, dataPort)].append(Timer(10.0, self.timeoutFunc, args=(destAddress, dataPort)))
         

  def timeoutFunc(self, destAddress, dataPort):
      if self.timers.has_key((destAddress, dataPort)):
          if len(self.timers[(destAddress, dataPort)]) >0:
              self.timers[(destAddress, dataPort)].pop(0)
          if len(self.timers[(destAddress, dataPort)]) ==0:
              del self.timers[(destAddress, dataPort)]
      if self.ftpAddress.has_key(destAddress):
          if dataPort in self.ftpAddress[destAddress]:
              self.ftpAddress[destAddress].remove(dataPort)
      log.debug("timedout")
      log.debug(self.ftpAddress)
      
    

