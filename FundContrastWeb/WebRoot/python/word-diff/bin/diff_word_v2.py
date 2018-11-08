import os
import sys
sys.path.append("../reference_code/")
import docx
import time
from mydifflib import ndiff
from docx import Document
from read_word_v2 import read_word_with_txt as read_word
from docu_comparer_v2 import create_changes_docx, filter_page


def diff_word(source_doc, target_doc, result_doc):
    print("%s start read word file" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    source_ = read_word(source_doc)
    target_ = read_word(target_doc)
    print("%s start ndiff lines" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    diff = ndiff(source_, target_)
    print("%s start filter changes" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    changes = filter_page(diff)
    print("%s start format changes" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    doc = create_changes_docx(changes, source_doc, target_doc)
    print("%s start save result doc" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    doc.save(result_doc)
    print("0")


if __name__ == '__main__':
    # source_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\1-增资协议.doc"
    # target_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\2-增资协议.doc"
    # result_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\diff_result_v2.docx"
    if len(sys.argv) == 4:
        source_doc = sys.argv[1]
        target_doc = sys.argv[2]
        result_doc = sys.argv[3]
        diff_word(source_doc, target_doc, result_doc)
    else:
        print("-1:参数数量异常！")
