import easyocr
import re

def ocr_progress(img_path):
    '''
    여권 이미지 ocr 진행
    
        input  : ocr진행할 이미지 경로
        output : ocr 결과 대문자 변환 후 추출
    '''
    reader = easyocr.Reader(['ko', 'en'])
    passport_information = reader.readtext(img_path)
    
    ocr_text = ''
    
    for r in range(len(passport_information)):
        ocr_text += passport_information[r][1]+' '

    ocr_text= ocr_text.upper()
    
    return ocr_text

def pattern_extraction(ocr_text):
    '''
    여권에서 중요정보만 추출하기
    
        input  : ocr 텍스트 추출 결과
        output : 여권 중요정보 ('E0000783880546502056M3007099340005096<089288')
    '''
    pattern = r"([A-Z]\d{8}|[A-Z]{2}\d{7}).{0,35}"
    ocr_text = ocr_text.replace(" ", "")
    match = re.search(pattern, ocr_text[-90:]) 
    
    if match:
        result = match.group()
        
    return result

def digit_check(passport_st, digit_st):
    '''
    위조여권 검증 코드
    
        input  : passport 정보, 검증 번호
        output : 위조 결과        
    '''
    pattern = [7, 3, 1]

    passport_num_dict={'A':10,'B':11,'C':12,'D':13,'E':14,'F':15,'G':16,'H':17,'I':18,'J':19,
                      'K':20,'L':21,'M':22,'N':23,'O':24,'P':25,'Q':26,'R':27,'S':28,
                      'T':29,'U':30,'V':31,'W':32,'X':33,'Y':34,'Z':35, '<':0}

    index = 0
    res = 0
    
    for p in range(len(passport_st)):
        a = passport_st[p]
        if a in passport_num_dict:
             a = passport_num_dict[a]

        num = pattern[index]
        index = (index + 1) % len(pattern)

        res +=int(a)*num

    if res % 10 != int(digit_st):
        return '위조여권'

    else:
        return '통과'
    
    
def passport_forgery_result(result):
    '''
    위조 여권 확인 코드
    
        input  : pattern_extraction 후의 결과
        output : 위변조 검증 결과
    '''
    tests = []

    tests.append(digit_check(result[:9], result[9]))
    tests.append(digit_check(result[13:19], result[19]))
    tests.append(digit_check(result[21:27], result[27]))
    tests.append(digit_check(result[28:-2], result[-2]))

    passport_last = result[:9] + result[9] + result[13:19] + result[19] + result[21:27] + result[27] + result[28:-2] + result[-2]
    tests.append(digit_check(passport_last, result[-1]))

    if any(passing != '통과' for passing in tests):
        return '위조'

    else:
        return '정상'

def match_country(text):
    '''
    여권 내에 나라 정보 추출 코드
    
        input   : 여권 ocr추출 결과
        output  : 여권의 나라
    '''
    text = text[-150:]
    # 정규표현식 패턴을 생성합니다. 'usa', 'kor', 'jpn', 'chn' 중 하나와 일치하는 단어를 찾습니다.
    pattern = r'(?:USA|KOR|JPN|CHN)'
    # 정규표현식을 텍스트에 적용하여 매칭되는 단어를 추출합니다.
    matches = re.findall(pattern, text, flags=re.IGNORECASE)

    return matches[0]

def text_execution(img_path):
    text = ocr_progress(img_path)
    country = match_country(text)
    Intermediate_results = pattern_extraction(text)
    final_result = passport_forgery_result(Intermediate_results)
    
    return final_result, country