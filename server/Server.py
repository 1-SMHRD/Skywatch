import socket
import cv2
import numpy
import base64
import time
import sys
import os
from datetime import datetime
from djitellopy import Tello

drone = Tello()
drone.connect()
print(drone.get_battery())

drone.streamon()

TCP_IP = "220.80.88.45"
TCP_PORT = 8899

class ServerSocket:
    def __init__(self, ip, port):
        self.TCP_IP = ip
        self.TCP_PORT = port
        # self.createVideoDir()
        self.socketOpen()
        
    def socketClose(self):
        self.sock.close()
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {TCP_PORT} ] is close")
        
    def socketOpen(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.bind((self.TCP_IP, self.TCP_PORT))
        self.sock.listen(99)
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {TCP_PORT} ] is open")
        
        self.client_conn, self.addr = self.sock.accept()
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {TCP_PORT} ] is connected with client")
        self.sendVideo()

    def sendVideo(self):
        cnt = 0
        startTime = time.localtime()

        try:
            # while capture.isOpened():
            while True:
                # result, frame = capture.read()
                frame = drone.get_frame_read().frame
                frame = cv2.resize(frame, (1280, 720))
                
                # cv2.imshow("drone", frame)
                result, frame = cv2.imencode('.jpg', frame, [cv2.IMWRITE_JPEG_QUALITY, 90])
                
                data = numpy.array(frame)
                stringData = base64.b64encode(data)
                length = str(len(stringData))
                
                self.client_conn.sendall(length.encode('utf-8').ljust(64))
                self.client_conn.sendall(stringData)
                
                print(f"send image {cnt} : {length}bytes")
                cv2.waitKey(33)
                time.sleep(0.001)
                cnt += 1
        except Exception as e:
            print(e)
            self.socketClose()
            time.sleep(1)
            self.socketOpen()
    
    def createVideoDir(self):
        now = time.localtime()
        folder_name = "./" + str(self.TCP_PORT) +"_drone_videos"
        os.makedirs(folder_name, exist_ok=True)
        
        folder_name = folder_name + "/" + self.getDate(now)
        os.makedirs(folder_name, exist_ok=True)
        
    def getDate(self, now):
        year = str(now.tm_year)
        month = str(now.tm_mon)
        day = str(now.tm_mday)
        
        if len(month) == 1:
            month = '0' + month
        if len(day) == 1:
            day = '0' + day
        
        return (year + '.' + month + '.' + day)
    
    def getTime(self, now):
        return (str(now.tm_hour) + '_' + str(now.tm_min) + '_' + str(now.tm_sec))
        
def main():
    server = ServerSocket(TCP_IP, TCP_PORT)

if __name__ == "__main__":
    main()