import os
import sys
sys.path.append("../reference_code/")
import docx
import time
from mydifflib import ndiff
from docx import Document
from read_word import read_word_with_page as read_word
from docu_comparer import create_changes_docx, filter_page


def diff_word(source_doc, target_doc, result_doc):
    print("%s start read word file" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    source_ = read_word(source_doc)
    target_ = read_word(target_doc)
    print("%s start ndiff lines" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    diff = ndiff(source_, target_)
    print("%s start filter changes" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    changes = filter_page(diff)
    print("%s start format changes" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    doc = create_changes_docx(changes)
    print("%s start save result doc" % time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())))
    doc.save(result_doc)


if __name__ == '__main__':
    source_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e11_接受.docx"
    target_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e12_拒绝.docx"
    result_doc = r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\diff_result.docx"
    diff_word(source_doc, target_doc, result_doc)
