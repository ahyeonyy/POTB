import cv2
import numpy as np
import matplotlib.pyplot as plt

def usa_img_uv_prepro(image_path):
    image = cv2.imread(image_path)
    # 이미지를 HSV 색상 공간으로 변환
    hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # 빨간색 범위 설정
    lower_red1 = np.array([0, 50, 50])
    upper_red1 = np.array([20, 255, 255])
    lower_red2 = np.array([145, 50, 50])
    upper_red2 = np.array([190, 255, 255])

    # 노란색 범위 설정
    lower_yellow = np.array([20, 50, 50])
    upper_yellow = np.array([50, 255, 255])

    # 초록색 범위 설정
    lower_green = np.array([40, 50, 50])
    upper_green = np.array([85, 255, 255])

    # 흰색 범위 설정
    lower_white = np.array([0, 0, 200])
    upper_white = np.array([180, 30, 255])
    
    # 빨간색, 노란색, 초록색, 청록색 영역 추출
    red_mask1 = cv2.inRange(hsv_image, lower_red1, upper_red1)
    red_mask2 = cv2.inRange(hsv_image, lower_red2, upper_red2)
    yellow_mask = cv2.inRange(hsv_image, lower_yellow, upper_yellow)
    green_mask = cv2.inRange(hsv_image, lower_green, upper_green)
    white_mask = cv2.inRange(hsv_image, lower_white, upper_white)

    # 결과 이미지 생성
    result = np.zeros_like(image)
    result[(red_mask1 != 0) | (red_mask2 != 0) | (yellow_mask != 0) | (green_mask != 0) | (white_mask != 0)] = [255, 255, 255]

    return result

def jpn_img_uv_prepro(image_path):
    image = cv2.imread(image_path)
    # 이미지를 HSV 색상 공간으로 변환
    hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # 빨간색 범위 설정
    lower_red1 = np.array([0, 50, 50])
    upper_red1 = np.array([20, 255, 255])
    lower_red2 = np.array([145, 70, 70])
    upper_red2 = np.array([190, 255, 255])

    lower_yellow = np.array([20, 50, 100])  # 형광노랑으로 표현되는 범위의 하한값
    upper_yellow = np.array([50, 255, 255])  # 형광노랑으로 표현되는 범위의 상한값


    # 초록색 범위 설정
    lower_green = np.array([40, 50, 50])
    upper_green = np.array([85, 255, 255])

    # 빨간색, 노란색, 초록색, 청록색 영역 추출
    red_mask1 = cv2.inRange(hsv_image, lower_red1, upper_red1)
    red_mask2 = cv2.inRange(hsv_image, lower_red2, upper_red2)
    yellow_mask = cv2.inRange(hsv_image, lower_yellow, upper_yellow)
    green_mask = cv2.inRange(hsv_image, lower_green, upper_green)

    # 결과 이미지 생성
    result = np.zeros_like(image)
    result[(red_mask1 != 0) | (red_mask2 != 0) | (yellow_mask != 0) | (green_mask != 0)] = [255, 255, 255]

    # 결과 출력
    return result

def chn_img_uv_prepro(imgae_path):
    image = cv2.imread(imgae_path)
    # 이미지를 HSV 색상 공간으로 변환
    hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # 빨간색 범위 설정
    lower_red1 = np.array([0, 50, 50])
    upper_red1 = np.array([20, 255, 255])
    lower_red2 = np.array([145, 70, 70])
    upper_red2 = np.array([190, 255, 255])

    # 노란색 범위 설정
    lower_yellow = np.array([20, 50, 50])
    upper_yellow = np.array([50, 255, 255])

    # 초록색 범위 설정
    lower_green = np.array([40, 50, 50])
    upper_green = np.array([85, 255, 255])

    # 빨간색, 노란색, 초록색, 청록색 영역 추출
    red_mask1 = cv2.inRange(hsv_image, lower_red1, upper_red1)
    red_mask2 = cv2.inRange(hsv_image, lower_red2, upper_red2)
    yellow_mask = cv2.inRange(hsv_image, lower_yellow, upper_yellow)
    green_mask = cv2.inRange(hsv_image, lower_green, upper_green)

    # 결과 이미지 생성
    result = np.zeros_like(image)
    result[(red_mask1 != 0) | (red_mask2 != 0) | (yellow_mask != 0) | (green_mask != 0)] = [255, 255, 255]
    
    # 결과 출력
    return result

def kor_img_uv_prepro(image_path):
    image = cv2.imread(image_path)
    # 이미지를 HSV 색상 공간으로 변환
    hsv_image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # 빨간색 범위 설정
    lower_red1 = np.array([0, 50, 50])
    upper_red1 = np.array([20, 255, 255])
    lower_red2 = np.array([145, 70, 70])
    upper_red2 = np.array([190, 255, 255])

    # 노란색 범위 설정
    lower_yellow = np.array([20, 50, 50])
    upper_yellow = np.array([50, 255, 255])

    # 초록색 범위 설정
    lower_green = np.array([40, 50, 50])
    upper_green = np.array([85, 255, 255])

    # 빨간색, 노란색, 초록색, 청록색 영역 추출
    red_mask1 = cv2.inRange(hsv_image, lower_red1, upper_red1)
    red_mask2 = cv2.inRange(hsv_image, lower_red2, upper_red2)
    yellow_mask = cv2.inRange(hsv_image, lower_yellow, upper_yellow)
    green_mask = cv2.inRange(hsv_image, lower_green, upper_green)

    # 결과 이미지 생성
    result = np.zeros_like(image)
    result[(red_mask1 != 0) | (red_mask2 != 0) | (yellow_mask != 0) | (green_mask != 0)] = [255, 255, 255]
    
    return result

def image_prepro_country(image_path, country):
    if country == 'USA':
        result_prepro = usa_img_uv_prepro(image_path)
        
    elif country == 'JPN':
        result_prepro = jpn_img_uv_prepro(image_path)
        
    elif country == 'CHN':
        result_prepro = chn_img_uv_prepro(ir_path, uv_path)
        
    else:
        result_prepro = kor_img_uv_prepro(image_path)
        
    return result_prepro