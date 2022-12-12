from flask import Blueprint, redirect, flash, Flask,url_for,request,render_template, Response
from module import dbmodule
from socket import *
import time
import os
import datetime
from module.tello_module import Tello


features  = Blueprint('features',__name__,url_prefix='/features')


# 단속구역 자율주행
# get, post
# @features.route('/autonomous driving')
# def autonomous_driving():
#     return redirect('main.index')

# ----------------
# 정보 수집 및 저장
def getDate(now):
    year = str(now.tm_year)
    month = str(now.tm_mon)
    day = str(now.tm_mday)
    
    if len(month) == 1:
        month = '0' + month
    if len(day) == 1:
        day = '0' + day
    
    return (year + '-' + month + '-' + day)
    
def getTime(now):
    return (str(now.tm_hour) + '.' + str(now.tm_min) + '.' + str(now.tm_sec))

@features.route("/setinfo")
def setInfo_car():
    data = ''
    
    now = time.localtime()
    
    regulation_date = getDate(now)
    regulation_time = getTime(now)
    car_num = str("123가4568", "utf-8")
    regulation_area = str("abc abc", "utf-8")

    os.chdir("../")
    imgdir_parking = "/drone_img/parking/" + "2022-12-03_15.09.00" + ".png"
    imgdir_numPlate = "/drone_img/numPlate/" + "2022-12-03_15.09.00" + ".jpg"
    
    db_tbareatest = dbmodule.Database()
    query_insert = f"insert into tb_area_test values ('{regulation_date}', '{regulation_time}', '{car_num}', '{regulation_area}', '{imgdir_parking}', '{imgdir_numPlate})"
    db_tbareatest.execute(query_insert)
    db_tbareatest.commit()
    

@features.route("/getDate_android", methods=['POST'])
def getDate_car():
    data = ''
    
    getDate = request.form["sendDate"]
    print("getDate: ", getDate)
    
    db_tbArea = dbmodule.Database()
    sql = f"select * from tb_area_test where regulation_date = '{getDate}'"
    row = db_tbArea.executeAll(sql)
    
    print("row len", len(row))
    print(type(row))
    print(row)

    return row

@features.route("/getCarNum_android", methods=['POST'])
def getNum_car():
    data = ''
    
    getCarNum = request.form["carNum"]
    print("getCarNum: ", getCarNum)
    
    db_tbArea = dbmodule.Database()
    sql = f"select * from tb_area_test where car_num = '{getCarNum}'"
    row = db_tbArea.executeAll(sql)
    
    print("row len", len(row))
    print(type(row))
    print(row)

    return row

@features.route("/getArea_android", methods=["POST"])
def getImgDir():
    msg = ''
    
    getData = request.form["sendArea"]
    print(getData)
    msg = "success"
    
    return msg

"""
    플라스크-드론 실시간 영상, 동작 조작 기능
"""

video_camera = None
global_frame = None
camera_frame = None
tello = None
tello = Tello()

# FPS
FPS = 25

# 실시간 영상 찍을수있는 함수
def video_stream():
    content = ''
    global video_camera
    global global_frame
    global camera_frame

    if video_camera == None:
        video_camera = tello
        if not video_camera.connect():
            content = '텔로 연결 하십시오'
            print(content)
            return
        if not video_camera.set_speed(10):
            content = '속도를 가능한 낮게 설정하지 마십시오.'
            print(content)
            return
        if not video_camera.streamoff():
            content = '비디오 끄지 않았습니다.'
            print(content)
            return
        if not video_camera.streamon():
            content = '비디오 시작하지 않았습니다.'
            print(content)
            return

    while True:
        frame_read = video_camera.get_frame_read()

        should_stop = False
        video_camera.get_battery()

        if True:
            print("디버그모드 활성화함")
        while not should_stop:
            if frame_read.stopped:
                frame_read.stop()
                break

            ori_frame = frame_read.frame
            frame = ori_frame
            if ori_frame.any():
                success, jpeg = cv2.imencode('.jpg',ori_frame)
                if success:
                    frame = jpeg.tobytes()

            if frame != None:
                global_frame = frame
                yield (b'--frame\r\n'b'Conetent-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
            else:
                yield (b'--frame\r\n'b'Conetent-Type: image/jpeg\r\n\r\n' + global_frame + b'\r\n\r\n')


#영상 프레임 얻게하는것
@staticmethod
def get_frame(self):
    ret, frame = self.cap.read()

    if ret:
        ret, jpeg = cv2.imenocde('.jpg',frame)

        if self.is_record:
            if self.out == None:
                fourcc = cv2.VideoWriter_fourcc(*'MJPG')
                self.out = cv2.VideoWriter('./static/video.avi',fourcc,20.0,(640,480))
            ret,frame = self.cap.read()
            if ret:
                self.out.write(frame)
        else:
            if self.out != None:
                self.out.release()
                self.out = None

        return jpeg.tobytes()

# 실시간 영상
@features.route('/video_feed')
def video_feed():
    return Response(video_stream(),mimetype="multipart/x-mixed-replace; boundary=frame")

@features.route('/takeOff')
def takeOff():
    drone_terbang = tello
    drone_state = 'Drone Takeoff'
    if not drone_terbang.takeoff():
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/Land')
def Land():
    drone_terbang = tello
    drone_state = 'Drone Landing'
    if not drone_terbang.land():
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/Right')
def Right():
    drone_terbang = tello
    drone_state = 'Drone move right'
    if not drone_terbang.move_right(100):
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")
@features.route('/Left')
def Left():
    drone_terbang = tello
    drone_state = 'Drone move left'
    if not drone_terbang.move_left(100):
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/forward')
def Forward():
    drone_terbang = tello
    drone_state = 'Drone move forward'
    if not drone_terbang.move_forward(100):
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/back')
def Back():
    drone_terbange = tello
    drone_state = 'Drone move back'
    if not drone_terbange.move_back(100):
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")


@features.route('/cw')
def Rotate_clockwise():
    drone_terbange = tello
    drone_state = 'Drone rotate_clockwise'
    if not drone_terbange.rotate_clockwise(100):
        print(drone_state)
        return render_template("area/features.html",drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/ccw')
def Rotate_counter_clockwise():
    drone_terbange = tello
    drone_state = 'Drone rotate_counter_clockwise'
    if not drone_terbange.rotate_counter_clockwise(100):
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")


@features.route('/flip')
def Flip():
    drone_terbange = tello
    drone_state = 'Drone flip'
    if not drone_terbange.flip("l"):
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/flip_left')
def Flip_left():
    drone_terbange = tello
    drone_state = 'Drone move flip_left'
    if not drone_terbange.flip_left():
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")


@features.route('/flip_right')
def Flip_right():
    drone_terbange = tello
    drone_state = 'Drone move flip_right'
    if not drone_terbange.flip_right():
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")

@features.route('/flip_forward')
def Flip_forward():
    drone_terbange = tello
    drone_state = 'Drone move flip_forward'
    if not drone_terbange.flip_forward():
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")
@features.route('/flip_back')
def Flip_back():
    drone_terbange = tello
    drone_state = 'Drone move flip_back'
    if not drone_terbange.flip_back():
        print(drone_state)
        return render_template("area/features.html", drone_state=drone_state)
    return render_template("area/features.html")