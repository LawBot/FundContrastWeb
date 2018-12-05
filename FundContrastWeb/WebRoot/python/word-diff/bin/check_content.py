#!/usr/bin/python
# -*- coding: UTF-8 -*-

import string
# import pprint
import win32com.client as win32
from zhon.hanzi import punctuation
from read_config import getConfig


# 英文标点符号识别警报
def find_en_punctuation(line, limit):
    p_list = []
    for word in line:
        # if word in string.punctuation:
        if word in limit:
            p_list.append(word)
    if p_list:
        return p_list
    else:
        return None


# 全角字符报警
# def find_q_str(line):
#     q_list = []
#     for word in line:
#         inside_code = ord(word)
#         if inside_code >= 65281 and inside_code <= 65374 and word not in punctuation:
#             q_list.append(word)
#     if q_list:
#         return q_list
#     else:
#         return None
# 全角字符报警
def find_q_str(line, limit):
    p_list = []
    for word in line:
        if word in limit:
            p_list.append(word)
    if p_list:
        return p_list
    else:
        return None


# 判断字符是否为中文
def is_Chinese(word):
    for ch in word:
        if '\u4e00' <= ch <= '\u9fff':
            return True
    return False


# 判断是否为英文
def is_alphabet(uchar):
    if (uchar >= u'\u0041' and uchar <= u'\u005a') or (uchar >= u'\u0061' and uchar <= u'\u007a'):
        return True
    else:
        return False


# 字体检查
def find_font_exp(doc_path, allow_fonts):
    try:
        word = win32.gencache.EnsureDispatch('Word.Application')
        document = word.Documents.Open(doc_path)
        font_exps = []
        for para in document.Paragraphs:
            # pprint.pprint("段落==>%s(%s)" % (para.Range.Text, para.Range.Font.Name))
            para_exp = []
            is_exp = False
            for char in para.Range.Characters:
                char_font = char.Font.Name
                char_str = str(char)
                # pprint.pprint("字======>%s:%s" % (char_str, char_font))
                char_isExp = False
                if is_Chinese(char_str) and char_font not in allow_fonts[0] and char_font != "":
                    is_exp = True
                    char_isExp = True
                elif is_alphabet(char_str) and char_font not in allow_fonts[1] and char_font != "":
                    is_exp = True
                    char_isExp = True
                para_exp.append([char_str, char_font, char_isExp])
            if is_exp:
                font_exps.append(para_exp)
        return font_exps
    except Exception as e:
        print(e)
    finally:
        document.Close(False)


# if __name__ == '__main__':
#     print(find_font_exp(r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\e14.docx", [r"宋体", r"Times New Roman"]))
#     print(find_q_str("ＯＫ，。来个全角字符啊"))
