import os
import re
import pprint
from docx import Document
from word_to_pdf import doc2pdf
from pdf_reader import pdf_reader
from readword_in_order import read_word


# 获取word文档的内容及页数
# 参数：filepath为文档路径
#      sep为diff匹配页码记号
def read_word_with_page(filepath, sep=r"!@#$%^"):
    try:
        extension = os.path.splitext(os.path.split(filepath)[1])[1]
        if extension.lower() == ".docx":
            pdf_path = filepath.replace(extension, ".pdf")
            if doc2pdf(filepath, pdf_path):
                pars = read_word(filepath)  # word内容
                content = pdf_reader(pdf_path)  # pdf内容
                pars_list = []
                last_page = 0
                last_content = []
                for (pa, mark) in pars:
                    if re.sub(r" |\t|\a|\n|\u3000", "", pa) == "":
                        continue
                    if not content:
                        break
                    (page_number, content) = find_pagraph_pagination(pa, content)
                    if page_number:
                        pars_list.append("%s%s第%d页%s" % (pa, sep, page_number, mark))
                        last_page = page_number
                        last_content = content
                    else:
                        pars_list.append("%s%s第%d页%s" % (pa, sep, last_page, mark))
                        content = last_content
                return pars_list
            else:
                print("文件转化失败！")
    except Exception as e:
        raise


# 查找word中的段落所在页数
# 参数：pa为段落文本
#      content为当前参与页数匹配的pdf内容(此内容根据匹配进度不算减少)
# 返回：(当前段落所在页数,刷新后的pdf内容)
def find_pagraph_pagination(pa, content):
    i = 1
    pa = re.sub(r" |\t|\a|\n|\u3000", "", pa)
    # pprint.pprint("-->%s" % pa)
    for line in content:
        line_content = re.sub(r" |\t|\a|\n|", "", line[1])
        # pprint.pprint("----->%s" % line_content)
        if pa.startswith(line_content):
            if content[i:]:
                content = content[i:]
            return (line[0], content)
        i += 1
    return (None, None)


# source_word = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e11_接受.docx"
# print(read_word_with_page(source_word))
