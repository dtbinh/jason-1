from sim.api import *
from sim.basics import *

'''
Create your RIP router in this file.
'''
class RIPRouter (Entity):
    def __init__(self, tablea = None):
        # Add your code here!
        self.table = {} # routing table key: destination -> Value: secondHashMap. secondHashMap: key: firstHop -> Value: distance
        self.firstHopPorts = {} # key: firstHop-> Value: portNumber

    def handle_rx (self, packet, port):
        
        if isinstance(packet, DiscoveryPacket):
            # handling discovery packets- link up or link down
            if packet.is_link_up:
                self.table[packet.src] = {} # initialize the second hashTable here
                self.table[packet.src][packet.src] = 1
                self.firstHopPorts[packet.src] = port
                self.send_updates_to_neighbors()
            else:
                # link-down cases
                routingTable_updated = False
                for dest in self.table.keys():
                    if packet.src in self.table[dest]:
                        shortestHop = self.shortest_hop(dest)
                        if shortestHop == packet.src:
                            routingTable_updated = True
                        del self.table[dest][packet.src]
                if packet.src in self.firstHopPorts:
                    del self.firstHopPorts[packet.src]
                    routingTable_updated = True

                
                if routingTable_updated: 
                    self.send_updates_to_neighbors()
        elif isinstance(packet, RoutingUpdate):
            routingTable_updated = False
            destinations = []
            deledes = []
            for destination in packet.all_dests():
                if destination == self:
                    continue
                destinations.append(destination)
                if packet.get_distance(destination) < 100:
                    if destination not in self.table:
                        self.table[destination] = {}
                        self.table[destination][packet.src] = 1 + packet.get_distance(destination)
                        routingTable_updated = True

                    else:
                        if packet.src not in self.table[destination]:
                            self.table[destination][packet.src] = 1 + packet.get_distance(destination)
                            shortestHop = self.shortest_hop(destination)
                            if shortestHop == packet.src:
                                routingTable_updated = True
                        else:
                            if 1 + packet.get_distance(destination) < self.table[destination][packet.src]:
                                self.table[destination][packet.src] = 1 + packet.get_distance(destination)
                                routingTable_updated = True
                else:
                    if destination in self.table:
                        if packet.src in self.table[destination]:
                            del self.table[destination][packet.src]
                            if self.table[destination] == {}:
                                deledes.append(destination)
                            
            
            destDelete = []
            for dest in self.table.keys():
                if dest not in destinations:
                    if packet.src in self.table[dest]:
                        shortestHop = self.shortest_hop(dest)
                        if shortestHop == packet.src:
                            routingTable_updated = True
                        del self.table[dest][packet.src]
                   
            if routingTable_updated:
               self.send_updates_to_neighbors()
                                
                        
                    
                
           
        else:
            # for normal packets, just send the packet to the specified port
            if packet.dst != self:
                self.send_or_forward_packets(packet, port)
            
            
                    

            
                
                
    def shortest_hop(self, destination):
        shortest_dis = 100
        shortestHop = None
        for firstHop in self.table[destination].keys():
            if self.table[destination][firstHop] < shortest_dis:
                shortestHop=firstHop
                shortest_dis=self.table[destination][firstHop]
            elif self.table[destination][firstHop] == shortest_dis and shortest_dis != 100:
                if self.firstHopPorts[shortestHop] > self.firstHopPorts[firstHop]:
                    shortestHop=firstHop
                    shortest_dis =self.table[destination][firstHop]

        return shortestHop
                    
            
  

    def send_or_forward_packets(self, packet, port):
        firstHop = self.shortest_hop(packet.dst)
        if firstHop != None:
            if port != self.firstHopPorts[firstHop]:
                self.send(packet, self.firstHopPorts[firstHop])
        
    
        
    def find_shortest_paths(self):
        # returns a list of shortest paths with (destination, distance) as each element node
        shortestList = []
        for destination in self.table.keys():
            shortestDistance = 100
            shortestfirstHop = None
            for firstHop in self.table[destination].keys():
                if self.table[destination][firstHop] < shortestDistance:
                    shortestDistance = self.table[destination][firstHop]
                    shortestfirstHop = firstHop
                elif self.table[destination][firstHop] == shortestDistance and shortestDistance !=100:
                    if self.firstHopPorts[shortestfirstHop] > self.firstHopPorts[firstHop]:
                        shortestfirstHop = firstHop
                        shortestDistance = self.table[destination][firstHop]
            if shortestDistance != 100:
                shortestList.append((destination, shortestDistance, shortestfirstHop))
        return shortestList

    
    def send_updates_to_neighbors(self):
        shortestList = self.find_shortest_paths()
        for firstHop in self.firstHopPorts.keys():
            shortestDistanceList = []
            for (destination, shortestDistance, shortestfirstHop) in shortestList:
                if destination == firstHop:
                    continue
                dist = shortestDistance
                if shortestfirstHop == firstHop:
                    dist = 100
                shortestDistanceList.append((destination, dist))
            packet = RoutingUpdate()
            for (dest, dist) in shortestDistanceList:
                print (dest, dist)
                packet.add_destination(dest, dist)
            self.send(packet, self.firstHopPorts[firstHop]) 
        
            
        
    
        
       
