from flask import Flask, request, jsonify, send_from_directory, session, redirect, render_template
import passport_all_run
import ssl
import os
from flask_session import Session 
import tempfile

import secrets

app = Flask(__name__)

# 무작위 문자열 생성 (예: 32바이트 길이)
secret_key = secrets.token_hex(32)
print(secret_key)
# 세션을 위한 시크릿 키 설정
app.secret_key = secret_key

# Flask-Session 확장을 초기화하고 세션 저장소를 설정합니다
app.config['SESSION_TYPE'] = 'filesystem'  # 세션 데이터를 파일 시스템에 저장하도록 설정 (다른 옵션도 가능)
Session(app)

# SSL 인증서 검증을 우회하는 설정
ssl._create_default_https_context = ssl._create_unverified_context

# 이미지를 저장할 임시 디렉토리 설정
UPLOAD_FOLDER = tempfile.mkdtemp()
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/process-images', methods=['POST'])
def process_images():
    try:
        image1 = request.files['image1']
        image2 = request.files['image2']

        if image1 and image2:
            # 이미지를 임시 파일로 저장
            image1_path = os.path.join(app.config['UPLOAD_FOLDER'], image1.filename)
            image2_path = os.path.join(app.config['UPLOAD_FOLDER'], image2.filename)
            image1.save(image1_path)
            image2.save(image2_path)
            print(image1_path)
            # passport_all_run.forgery_inspection_result 함수 호출하여 결과 얻기
            result = passport_all_run.forgery_inspection_result(image1_path, image2_path)


            # 결과를 세션에 저장
            session['result'] = {'results': result}
            print(result)
            # '/result' 엔드포인트로 리다이렉트
            return redirect('/result')
        else:
            return jsonify({'error': 'Image files not provided.'}), 400
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/result')
def result():
    # 세션에서 JSON 데이터 가져오기
#     result_data = session.get('result', {})
    result_data = session['result']
    print(result_data)
    # 'result.html'을 렌더링하면서 JSON 데이터 전달
    return render_template('result.html', result_data=result_data)

if __name__ == '__main__':
    app.run(host='localhost', port=5001, debug=True)
