import socket
import cv2
import numpy
import base64
import time
import sys
import os
from datetime import datetime

TCP_IP = "220.80.88.45"
TCP_PORT = 8899

class ServerSocket:
    def __init__(self, ip, port):
        self.TCP_IP = ip
        self.TCP_PORT = port
        self.createVideoDir()
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
        
        capture = cv2.VideoCapture('big_buck_bunny_720p_10mb.mp4')
        
        if capture.isOpened():
            fps = capture.get(cv2.CAP_PROP_FPS)
            f_count = capture.get(cv2.CAP_PROP_FRAME_COUNT)
            f_w = round(capture.get(cv2.CAP_PROP_FRAME_WIDTH))
            f_h = round(capture.get(cv2.CAP_PROP_FRAME_HEIGHT))
            
            # name = "drone_" + getDate(startTime) + '_' + getTime(startTime) +".mp4"
            # out = cv2.VideoWriter()
        
        if not capture.isOpened():
            print("Camera open failed!")
        else:
            capture.set(cv2.CAP_PROP_FRAME_WIDTH, f_w)
            capture.set(cv2.CAP_PROP_FRAME_HEIGHT, f_h)

            try:
                while capture.isOpened():
                    result, frame = capture.read()

                    result, frame = cv2.imencode('.jpg', frame, [cv2.IMWRITE_JPEG_QUALITY, 90])

                    data = numpy.array(frame)
                    stringData = base64.b64encode(data)
                    length = str(len(stringData))
                    
                    self.client_conn.sendall(length.encode('utf-8').ljust(64))
                    self.client_conn.sendall(stringData)
                    
                    print(f"send image {cnt} : {length}bytes")
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