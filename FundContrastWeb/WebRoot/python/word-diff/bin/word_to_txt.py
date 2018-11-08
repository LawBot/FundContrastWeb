import os
import win32com.client as win32


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
        print("word转化txt失败!")
        return False
    finally:
        worddoc.Close(False)
        word.Quit()


# doc_path = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\1-增资协议.doc"
# txt_path = "C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\1-增资协议.txt"
# print(doc2txt(doc_path, txt_path))
