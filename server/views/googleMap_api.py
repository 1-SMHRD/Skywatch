from flask import Flask, Blueprint, request, render_template
from flask_googlemaps import GoogleMaps, Map,icons,get_address,get_coordinates
from socket import *

google = Blueprint('googleMap',__name__,'/googleMap')

"""
이거는 flask_googlemap 라이브러리에서 있는 gmapjs.html 있는데
수정 안하면 구글 맵 지도 아예 안뜬다. 따라서 수정해야한다.
임시적으로 메모장에다가 적을예정이니 참고하세요
"""



# api 실행했을때 에러가 뜨면은 데이터베이스의 area에 뭔가 잘못된 글자가 들어 있다는 뜻임
# 데이터베이스 area 테스트 완료
datas = []
from module.dbmodule import Database
a = Database()
sql = "select * from tb_area"
row = a.executeAll(sql)
#
# for i in row:
#     print(i['regulation_area'])
#     datas.append(i['regulation_area'])
#
#
# print(datas)
#
# print(datas[10:])
#
# for i in datas[10:]:
#     print(i)
#
# print({sq for sq in datas[10:]})
# 주소텍스트에서 좌표 가져오기 테스트 완료
# API_KEy는 개인용키이므로 유출하지마셈

@google.route('/googleMap_api')
def googlemap():
    # 뷰에서 지도생성
    # 이거는 싱글 지도(기본 지도)임
    gmap = Map(
        identifier="gmap",
        varname="gmap",
        lat=35.149681,
        lng=126.919929,
        markers = [(35.149681,126.919929)],
        language="ko",
        style="height:600px;width:1100px;margin:0;",
    )
    test = []
    for r in row:
        print(r['regulation_area'])
        test.append(r['regulation_area'])
    final_test = []
    for j in test[40:]:
        # 지오코딩 -> 데이터베이스의 주소를 위도,경도로 바꿀수있게 하는 라이브러리
        result = get_coordinates(API_KEY='AIzaSyBx6q68vuftoJ5VoCP6RjJotaUwlbNJADg', address_text=j)
        final_result = (result['lat'],result['lng'],j)
        print(final_result)
        print(type(final_result))
        final_test.append(final_result)
    print(final_test)

    multimap = Map(
        identifier="multimap",
        varname="multimap",
        lat=35.149681,
        lng=126.919929,
        language="ko",
        markers={
            # icons.dots.green: [(35.149681,126.919929, "hello")],
            # icons.dots.blue: [(35.149681,126.919400, "hi!")],
            icons.dots.blue: [sq for sq in final_test]
        },
        style="height:600px;width:1100px;margin:0;",
    )

    return render_template("information/crackdown_inquiry.html",gmap=gmap,multimap=multimap,test=test)