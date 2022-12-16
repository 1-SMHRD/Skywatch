from flask import Flask, Blueprint, request, render_template
from flask_googlemaps import GoogleMaps, Map,icons,get_address,get_coordinates
from socket import *

google = Blueprint('googleMap',__name__,'/googleMap')

"""
이거는 flask_googlemap 라이브러리에서 있는 gmapjs.html 있는데
수정 안하면 구글 맵 지도 아예 안뜬다. 따라서 수정해야한다.
임시적으로 메모장에다가 적을예정이니 참고하세요
"""
@google.route('/googleMap_api')
def googlemap():
    # 뷰에서 지도생성
    clickmap = Map(
        identifier="clickmap",
        varname="clickmap",
        lat=35.149681,
        lng=126.919929,
        report_clickpos=True,
        report_markerClickpos=True,
        # 보고서 마크를 클릭합니다.
        # 보고서를 클릭합니다.
        # markerClickpos_uri = 마커마우스 오른쪽버튼을 누릅시오
        # 보고하다
        clickpos_uri="/clickpost",
        language="ko",
        markers={
            icons.dots.blue: [s for s in results]
        },
        style="height:600px;width:1100px;margin:0;",
    )

    return render_template("information/crackdown_inquiry.html",clickmap = clickmap)

results = []
@google.route('/clickpost',methods = ["POST"])
def clickpost():
    lat = request.form["lat"] #위도
    lng = request.form["lng"] # 경도
    print(lat)
    print(lng)
    results.append((lat,lng))
    print(results)
    return results