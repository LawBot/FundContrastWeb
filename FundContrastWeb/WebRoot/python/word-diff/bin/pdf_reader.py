import sys
import importlib
import pprint
importlib.reload(sys)
from pdfminer.pdfparser import PDFParser, PDFDocument
from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import PDFPageAggregator
from pdfminer.layout import LTTextBoxHorizontal, LAParams
from pdfminer.pdfinterp import PDFTextExtractionNotAllowed


def pdf_reader(path):
    fp = open(path, 'rb')  # 以二进制读模式打开
    praser = PDFParser(fp)  # 用文件对象来创建一个pdf文档分析器
    doc = PDFDocument()  # 创建一个PDF文档
    praser.set_document(doc)  # 连接分析器 与文档对象
    doc.set_parser(praser)
    doc.initialize()  # 提供初始化密码 如果没有密码 就创建一个空的字符串
    # 检测文档是否提供txt转换，不提供就忽略
    if not doc.is_extractable:
        raise PDFTextExtractionNotAllowed
        return None
    else:
        rsrcmgr = PDFResourceManager()  # 创建PDf 资源管理器 来管理共享资源
        laparams = LAParams()  # 创建一个PDF设备对象
        device = PDFPageAggregator(rsrcmgr, laparams=laparams)
        interpreter = PDFPageInterpreter(rsrcmgr, device)  # 创建一个PDF解释器对象
        pdf_content = []
        page_num = 1
        # 循环遍历列表，每次处理一个page的内容
        for page in doc.get_pages():
            interpreter.process_page(page)
            layout = device.get_result()  # 接受该页面的LTPage对象
            # 这里layout是一个LTPage对象 里面存放着 这个page解析出的各种对象 一般包括LTTextBox, LTFigure, LTImage, LTTextBoxHorizontal等等
            # 想要获取文本就获得对象的text属性，
            for x in layout:
                if (isinstance(x, LTTextBoxHorizontal)):
                    results = x.get_text()
                    # pprint.pprint(results)
                    if "\n" in results:
                        for line in results.split("\n"):
                            if line.strip() != "":
                                pdf_content.append((page_num, line))
                    else:
                        pdf_content.append((page_num, results))
            page_num += 1
        return pdf_content


# pdf_reader("C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\2-4.pdf")
