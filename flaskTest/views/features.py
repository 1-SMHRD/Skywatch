from flask import Blueprint, render_template, flash, Flask,url_for,request,redirect


features  = Blueprint('features',__name__,url_prefix='/features')


# 단속구역 자율주행
# get, post
@features.route('/autonomous driving')
def autonomous_driving():
    return redirect('main.index')

# 불법 주정차 차량 정보 수집



#수집 정보 저장



#단속 정보 조회


#드론 등록 및 조작




