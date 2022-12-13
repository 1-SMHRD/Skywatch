import socket
import cv2
import numpy
import base64
import time
import sys
import os
from datetime import datetime
from djitellopy import Tello


class ServerSocket:
    def __init__(self, ip, port):
        self.TCP_IP = ip
        self.TCP_PORT = port
        # self.createVideoDir()
        self.socketOpen()
        
    def socketClose(self):
        self.sock.close()
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {self.TCP_PORT} ] is close")
        
    def socketOpen(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # WinError10048 해결
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.sock.bind((self.TCP_IP, self.TCP_PORT))
        self.sock.listen(99)
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {self.TCP_PORT} ] is open")
        
        self.client_conn, self.addr = self.sock.accept()
        print(f"Server Socket [ TCP_IP: {self.TCP_IP}, TCP_PORT: {self.TCP_PORT} ] is connected with client")
        getmsg = bytearray(self.client_conn.recv(1024))[2:]
        msg = getmsg.decode("utf-8")
        
        if msg == "/image":
            print("sendImage()")
            self.sendImage()
        elif msg == "/drone":
            print("sendVideo()")
            self.sendVideo()
        else :
            print("===" + msg + "===")

    def sendVideo(self):
        
        """ drone = Tello()
        drone.connect()
        print(drone.get_battery())
        drone.streamon() """
        
        cnt = 0
        startTime = time.localtime()

        capture = cv2.VideoCapture("big_buck_bunny_720p_10mb.mp4")

        try:
            while capture.isOpened():
            # while True:
                result, frame = capture.read()
                # frame = drone.get_frame_read().frame
                frame = cv2.resize(frame, (1280, 720))
                
                # cv2.imshow("drone", frame)
                result, frame = cv2.imencode('.jpg', frame, [cv2.IMWRITE_JPEG_QUALITY, 90])
                
                data = numpy.array(frame)
                stringData = base64.b64encode(data)
                length = len(stringData)
                
                self.client_conn.sendall(length.to_bytes(4, byteorder="big"))
            
                while len(stringData):
                    if len(stringData) < 1024:
                        self.client_conn.sendall(stringData)
                        stringData = []
                    else:
                        self.client_conn.sendall(stringData[:1024])
                        stringData = stringData[1024:]
                
                print(f"send image {cnt} : {length}bytes")
                cv2.waitKey(33)
                time.sleep(0.001)
                cnt += 1
        except Exception as e:
            print(e)
            self.client_conn.close()
            self.socketClose()
            time.sleep(1)
            self.socketOpen()
        
        self.client_conn.close()
            
    def sendImage(self):
        for i in range(3):
            getDir = bytearray(self.client_conn.recv(1024))[2:].decode("utf-8")

            print(os.getcwd() + getDir)
            img = cv2.imread(os.getcwd() + getDir)
            
            result, img = cv2.imencode('.jpg', img, [cv2.IMWRITE_JPEG_QUALITY, 90])
            
            data = numpy.array(img)
            stringData = base64.b64encode(data)
            length = len(stringData)
            
            print(length)
            
            self.client_conn.sendall(length.to_bytes(4, byteorder="big"))
            
            while len(stringData):
                if len(stringData) < 1024:
                    self.client_conn.sendall(stringData)
                    stringData = []
                else:
                    self.client_conn.sendall(stringData[:1024])
                    stringData = stringData[1024:]
                    
        time.sleep(0.01)
        self.client_conn.close()
        self.socketClose()
        time.sleep(0.01)
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
        
""" def main():
    server = ServerSocket(TCP_IP, TCP_PORT)

if __name__ == "__main__":
    main() """