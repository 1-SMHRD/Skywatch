from flask import Flask, render_template
from views.main import main as main
from views.login_views import logins
from views.features import features
from views.KakaoMap_api import kakao
from socketclass import ServerSocket
import threading

app = Flask(__name__)
app.secret_key = 'hello'

# 추가할 모듈이 있다면 추가
# config 파일이 있다면 추가

# 앞으로 새로운 폴더를 만들어서 파일을 추가할 예정
# from app.main.[파일 이름] --> app 폴더 아래에 main 폴더 아래에 [파일 이름].py를 import

# 위에서 추가한 파일을 연동해주는 역할
# app.resgister_blueprint(추가한 파일)

app.register_blueprint(main)
app.register_blueprint(logins)
app.register_blueprint(features)
app.register_blueprint(kakao)
if __name__ == '__main__':
    t = threading.Thread(target=ServerSocket, args=("220.80.88.45", 8089))
    t.start()
    app.run(host="0.0.0.0")

