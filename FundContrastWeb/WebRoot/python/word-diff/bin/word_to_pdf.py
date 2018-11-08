import os
from win32com import client


def doc2pdf(doc_name, pdf_name):
    try:
        word = client.DispatchEx("Word.Application")
        if os.path.exists(pdf_name):
            os.remove(pdf_name)
        worddoc = word.Documents.Open(doc_name)
        worddoc.SaveAs(pdf_name, FileFormat=17)
        worddoc.Close()
        return True
    except Exception as e:
        print(e)
        return False
    finally:
        word.Quit()


# doc2pdf(r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\e9.docx",
#         r"C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e9.pdf")
