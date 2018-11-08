import os
import re
from word_to_txt import doc2txt


def read_word_with_txt(filepath, sep=r"!@#$%^"):
    try:
        extension = os.path.splitext(os.path.split(filepath)[1])[1]
        if extension.lower() == ".docx" or extension.lower() == ".doc":
            txt_path = filepath.replace(extension, ".txt")
            if doc2txt(filepath, txt_path):
                content = open(txt_path, "r").readlines()
                return add_chapter(content, sep)
            else:
                print("文件转化失败！")
    except Exception as e:
        raise


def add_chapter(content, sep):
    chapter_pattern1 = re.compile(r"^(第.{1,3}条) .*")
    chapter_pattern2 = re.compile(r"^(\d+.\d+) .*")
    result_content = []
    chapter_lv1 = "前言"
    chapter_lv2 = ""
    for line in content:
        line = line.strip()
        if line == "":
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
# print(read_word_with_txt(source_word))
