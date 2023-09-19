import passport_text_checker2
import image_preprocessing
import similar_image_check

def forgery_inspection_result(image_path, uv_path):
    name, made_date, passport_num, text_result, country = passport_text_checker2.text_execution(image_path)
    similar_result = similar_image_check.run_similar_image(uv_path, country, './DATA/'+country.lower()+'/mask')
    
    if text_result != '정상' or similar_result != '정상':
        
        return [name, made_date, passport_num, '위조', country]

    else:
        return [name, made_date, passport_num, '정상', country]