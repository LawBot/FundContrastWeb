import os
import win32com.client as win32


# 将word文档接受修订后转化为txt文档（接受doc,docx文件）
# 主要用于读取word纯文本内容，防止docx,win32com直接读取时序号、超链接等内容丢失的情况发生
def doc2txt(doc_name, txt_name):
    try:
        word = win32.gencache.EnsureDispatch('Word.Application')
        if os.path.exists(txt_name):
            os.remove(txt_name)
        worddoc = word.Documents.Open(doc_name)
        worddoc.Activate()
        word.ActiveDocument.TrackRevisions = False
        try:
            word.WordBasic.AcceptAllChangesInDoc()
        except TypeError:
            pass
        word.ActiveDocument.SaveAs(txt_name, FileFormat=2)
        return True
    except Exception as e:
        print(e)
        return False
    finally:
        worddoc.Close(False)
        word.Quit()


# doc_path = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\1-增资协议.doc"
# txt_path = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\1-增资协议.txt"
# print(doc2txt(doc_path, txt_path))
