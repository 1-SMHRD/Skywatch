# flask_wtf은 html의 <input> 처럼 쉽게 해줄수있는 작성 폼
# 필요한 작성하기위해 패키지 가져옴
from flask_wtf import Form
from wtforms import TextAreaField, IntegerField, SubmitField, RadioField, SelectField
from wtforms import validators, ValidationError



