#!/usr/bin/env python
# coding: utf-8

# In[3]:


import cv2
import numpy as np
import h5py

def save_preprocessed_image_to_hdf5(image_path, output_hdf5_path, country):
    # 이미지 전처리 함수 호출
    result_prepro = image_prepro_country(image_path, country)

    # HDF5 파일 생성 또는 열기
    hdf5_file = h5py.File(output_hdf5_path, 'w')

    # 이미지 전처리 결과를 HDF5 데이터셋으로 저장
    hdf5_file.create_dataset('preprocessed_image', data=result_prepro)

    # 파일 저장 종료
    hdf5_file.close()

# 이미지 전처리 및 HDF5 파일로 저장 예제
image_path = 'your_image.jpg'  # 이미지 파일 경로
output_hdf5_path = 'preprocessed_image.h5'  # 저장할 HDF5 파일 경로
country = 'USA'  # 국가 정보

# 이미지 전처리 결과를 HDF5 파일로 저장
save_preprocessed_image_to_hdf5(image_path, output_hdf5_path, country)


# In[ ]:





# In[ ]:





# In[ ]:





# In[ ]:





# In[ ]:





# In[ ]:




