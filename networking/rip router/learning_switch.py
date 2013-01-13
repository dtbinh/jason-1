from sim.api import *
from sim.basics import *

'''
Create your learning switch in this file.
'''
class LearningSwitch(Entity):
    def __init__(self):
        # Add your code here!
        self.portTable = dict()

    def handle_rx (self, packet, port):
        # Add your code here!
        self.portTable[packet.src] = port
        """ If a packet comes in destined to an Entity from whom you have never
        seen a packet before, broadcast the packet on all ports except the port
        which the packet came in on."""
        if not self.portTable.has_key(packet.dst):
            self.send(packet, port, flood=True)
        else:
            """If a packet arrives destined to an Entity from whom you have
            previously received a packet, only send the packet out on the port
            from which packets from that Entity previously arrived."""
            destinationPort = self.portTable[packet.dst]
            self.send(packet, destinationPort, flood=False)



