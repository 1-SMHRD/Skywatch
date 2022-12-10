from flask import Flask, Blueprint, request, render_template


kakao = Blueprint('KakaoMap',__name__,'/KakaoMap')

@kakao.route('/kakaoMap result', methods = ['POST','GET'])
def kakaomap():
    if request.method == 'POST':
        result = request.form
        return render_template("information/crackdown_inquiry.html",prediction = result)

