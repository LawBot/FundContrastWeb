import sys
import docx
import numpy
import check_sn
import win32com
import word_to_txt
import read_config
import check_content
import chapter_pattern
from check_doc import write_exp_doc

if __name__ == '__main__':
    if len(sys.argv) == 3:
        doc_path = sys.argv[1]
        result_doc = sys.argv[2]
        print(write_exp_doc(doc_path, result_doc))
    else:
        print("-1:参数数量异常！")
