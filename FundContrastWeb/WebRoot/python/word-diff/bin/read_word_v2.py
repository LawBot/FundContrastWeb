import os
import re
from word_to_txt import doc2txt
# from check_content import find_en_punctuation, find_q_str


# 将Word文档转化为TXT文件后读取所有内容
# 转化为TXT目的是防止通过docx直接读取文档造成编号、超链接等内容丢失的问题
def read_word_with_txt(filepath, sep=r"!@#$%^"):
    try:
        filename = os.path.split(filepath)[1]
        if not os.path.exists(filepath):
            return "-1:文件%s不存在！" % filename
        if os.path.getsize(filepath) > 50*1024*1024:
            return "-1:文件%s大小超过50M！" % filename
        extension = os.path.splitext(filename)[1]
        if extension.lower() == ".docx" or extension.lower() == ".doc":
            txt_path = filepath.replace(extension, ".txt")
            if doc2txt(filepath, txt_path):
                content = open(txt_path, "r").readlines()
                return add_chapter(content, sep)
            else:
                return "-1:文件读取失败！"
        else:
            return "-1:文件非Word文档！"
    except Exception as e:
        return "-1:%s" % e
    finally:
        if os.path.exists(txt_path):
            os.remove(txt_path)


# 通过正则表达式获取内容中编号信息
# 对文档中每个段落进行章节编号的标注
def add_chapter(content, sep):
    chapter_pattern1 = re.compile(r"^(第.{1,3}条) .*")
    chapter_pattern2 = re.compile(r"^(\d+.\d+) .*")
    exp_pattern = re.compile(r"^(第 \d{1,3} 页).*")
    result_content = []
    chapter_lv1 = "前言"
    chapter_lv2 = ""
    for line in content:
        line = line.strip()
        if line == "":
            continue
        if exp_pattern.findall(line):
            continue
        chapter1_obj = chapter_pattern1.findall(line)
        chapter2_obj = chapter_pattern2.findall(line)
        if chapter1_obj:
            chapter_lv1 = chapter1_obj[0]
            chapter_lv2 = ""
        if chapter2_obj:
            chapter_lv2 = chapter2_obj[0]
        result_content.append("%s%s%s%s" % (line, sep, chapter_lv1, chapter_lv2))
    return result_content


# source_word = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e14.docx"
# read_word_with_txt(source_word)
