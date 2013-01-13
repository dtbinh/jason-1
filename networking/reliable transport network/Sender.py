import sys
import getopt

import Checksum
import BasicSender

'''
This is a skeleton sender class. Create a fantastic transport protocol here.
'''
class Sender(BasicSender.BasicSender):
    def readAndSendPackets(self, sliding_window, seqno, msg_type, msg):
        while not msg_type == 'end' and len(sliding_window) <5:
            next_msg = self.infile.read(1400)
            msg_type = 'data'
            if seqno == 0:
                msg_type = 'start'
            elif next_msg == "":
                msg_type = 'end'
            packet = self.make_packet(msg_type,seqno,msg)
            # print "sent: %s" % packet
            self.send(packet)
            msg = next_msg
            seqno += 1
            sliding_window.append(packet)
        return (sliding_window, seqno, msg_type, msg)

    def sendAllSlidingWindowPackets(self, sliding_window):
        for packet in sliding_window:
            self.send(packet)
   
    # Main sending loop.
    
    def start(self):
        seqno = 0
        msg = self.infile.read(1400) # 1472 for entire packet
        msg_type = None
        sliding_window = []
        highestresponse = 0
        done = False
        sliding_window, seqno, msg_type, msg = self.readAndSendPackets(sliding_window, seqno, msg_type, msg)
        while not done:
            response = self.receive(0.5)
            if response == None:
                self.sendAllSlidingWindowPackets(sliding_window)
                continue
            if not Checksum.validate_checksum(response):
                # should ignore incorrect checksum- corrupt packets
                continue
            response_seqno = int(self.split_packet(response)[1])
            if response_seqno == seqno:
                if msg_type == 'end':
                    done = True
                    continue
                # clear the window and ready for next round
                highestresponse = response_seqno
                sliding_window=[]
                sliding_window, seqno, msg_type, msg = self.readAndSendPackets(sliding_window, seqno, msg_type, msg)
                continue
                if response_seqno < highestresponse:
                    # ignore old acks
                    continue
                elif response_seqno > highestresponse:
                    highestresponse = response_seqno
                    deleteWindowPackets = seqno - highestresponse
                    temp = len(sliding_window)
                    while deleteWindowPackets<temp:
                        """print "deleteWindowPackets", deleteWindowPackets
                        print "sliding_window", len(sliding_window)
                        print "highestresponse", highestresponse
                        print "seqno", seqno"""
                        sliding_window.pop(0)
                        deleteWindowPackets +=1
                    self.sendAllSlidingWindowPackets(sliding_window)
                    if msg_type != 'end':
                        sliding_window, seqno, msg_type, msg = self.readAndSendPackets(sliding_window, seqno, msg_type, msg)
                    continue
                else:
                    # clearly there is a missing packet here! What I do here is just send the next packet to avoid
                    # duplicate sending
                    self.send(sliding_window[0])
                    continue
                    
                    
        self.infile.close()
    
'''
This will be run if you run this script from the command line. You should not
change any of this; the grader may rely on the behavior here to test your
submission.
'''
if __name__ == "__main__":
    def usage():
        print "BEARS-TP Sender"
        print "-f FILE | --file=FILE The file to transfer; if empty reads from STDIN"
        print "-p PORT | --port=PORT The destination port, defaults to 33122"
        print "-a ADDRESS | --address=ADDRESS The receiver address or hostname, defaults to localhost"
        print "-d | --debug Print debug messages"
        print "-h | --help Print this usage message"

    try:
        opts, args = getopt.getopt(sys.argv[1:],
                               "f:p:a:d", ["file=", "port=", "address=", "debug="])
    except:
        usage()
        exit()

    port = 33122
    dest = "localhost"
    filename = None
    debug = False

    for o,a in opts:
        if o in ("-f", "--file="):
            filename = a
        elif o in ("-p", "--port="):
            port = int(a)
        elif o in ("-a", "--address="):
            dest = a
        elif o in ("-d", "--debug="):
            debug = True

    s = Sender(dest,port,filename,debug)
    try:
        s.start()
    except (KeyboardInterrupt, SystemExit):
        exit()
