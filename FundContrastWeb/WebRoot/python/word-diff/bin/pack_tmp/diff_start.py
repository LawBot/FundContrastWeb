import sys
import docx
import numpy
import mydifflib
import word_to_txt
import read_word_v2
import chapter_pattern
import docu_comparer_v2
import win32com.client as win32
from diff_word_v2 import diff_word


if __name__ == '__main__':
    if len(sys.argv) == 4:
        source_doc = sys.argv[1]
        target_doc = sys.argv[2]
        result_doc = sys.argv[3]
        print(diff_word(source_doc, target_doc, result_doc))
    else:
        print("-1:参数数量异常！")
