import sys
sys.path.append("../reference_code/")
import time
from mydifflib import ndiff
from read_word_v2 import read_word_with_txt as read_word
from docu_comparer_v2 import create_changes_docx, filter_page


# 时间戳标记
def timestamp_print(str_):
    time_str = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
    print("%s %s" % (time_str, str_))


# 文档比对主方法
def diff_word(source_doc, target_doc, result_doc):
    timestamp_print("read word file")
    source_ = read_word(source_doc)
    if isinstance(source_, str):
        return source_
    target_ = read_word(target_doc)
    if isinstance(target_, str):
        return target_
    timestamp_print("ndiff lines")
    diff = ndiff(source_, target_)
    timestamp_print("filter changes")
    changes = filter_page(diff)
    timestamp_print("format changes")
    doc = create_changes_docx(changes, source_doc, target_doc)
    timestamp_print("save result doc")
    doc.save(result_doc)
    return "0"


if __name__ == '__main__':
    # source_doc = r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\1-增资协议.doc"
    # target_doc = r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\2-增资协议.doc"
    # result_doc = r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\diff_result_v2.docx"
    if len(sys.argv) == 4:
        source_doc = sys.argv[1]
        target_doc = sys.argv[2]
        result_doc = sys.argv[3]
        print(diff_word(source_doc, target_doc, result_doc))
    else:
        print("-1:参数数量异常！")
