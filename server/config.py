"""
데이터 베이스 설정파일 내용
"""
# 삭제할 파일임
import pymysql

# 연결성공
conn = pymysql.connect(host='project-db-stu.ddns.net',port=3307,user='sky',password='1q2w3e',db='sky',charset='utf8')
if conn.open:
    with conn.cursor() as curs:
        print('connected')

# 커서생성
cursor = conn.cursor()

cursor.execute("select * from test")

# 실행결과 가져올때 쓰는것
# fetchall는 여러개
# fetchone는 한개
result = cursor.fetchone()

# 테이블만들기
#cursor.execute('create table test(id varchar(40), password varchar(40), droneName varchar(20))')

#cursor.execute("insert into test values ('test','1234','tello')")

conn.commit()
conn.close()

print(result)