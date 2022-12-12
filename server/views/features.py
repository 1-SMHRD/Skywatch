from flask import Blueprint, render_template, flash, Flask,url_for,request,redirect
from module import dbmodule
import time
import os


features  = Blueprint('features',__name__,url_prefix='/features')


# 단속구역 자율주행
# get, post
@features.route('/autonomous driving')
def autonomous_driving():
    return redirect('main.index')

# 불법 주정차 차량 정보 수집

# 수집 정보 저장

# 단속 정보 조회

# 드론 등록 및 조작

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

