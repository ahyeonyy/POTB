import cv2
import numpy as np
import os
import image_preprocessing
import glob

def compare_images(input_image, mask_images):
    # 이미지 파일을 그레이스케일로 로드
    img1 = cv2.cvtColor(input_image, cv2.COLOR_BGR2GRAY)
    img2 = cv2.imread(mask_images, cv2.IMREAD_GRAYSCALE)

    # 이미지 크기를 통일시키기 위해 크기 조정 (옵션)
    img1 = cv2.resize(img1, (300, 300))
    img2 = cv2.resize(img2, (300, 300))

    # 이미지 유사도를 계산하기 위해 히스토그램 기반 비교 방법인 '코렐레이션' 사용
    correlation = cv2.matchTemplate(img1, img2, cv2.TM_CCOEFF_NORMED)
    similarity = correlation[0][0]  # 이미지 유사도 값

    return similarity

def find_most_similar_image(input_image, country, folder_path):
    # 폴더 내의 이미지 파일들을 가져옴
    image_files = glob.glob('./DATA/'+country+'/mask/*.png')

    most_similar_image = None
    max_similarity = -1

    # 폴더 내 이미지들과 유사도 비교
    for image_file in image_files:
        image_path = image_file
        similarity = compare_images(input_image, image_path)

        # 최대 유사도 갱신
        if similarity > max_similarity:
            max_similarity = similarity
            most_similar_image = image_path

    return most_similar_image, max_similarity

def run_similar_image(input_uv_path, country, image_folder_path):
    input_image = image_preprocessing.image_prepro_country(input_uv_path, country)
    most_similar_image, similarity = find_most_similar_image(input_image, country, image_folder_path)
    
    if similarity < 0.65:
        return "위조"
    else:
        return "정상"