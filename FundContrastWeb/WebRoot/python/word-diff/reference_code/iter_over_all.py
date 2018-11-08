import docx



from docx.document import Document
from docx.oxml.table import CT_Tbl
from docx.oxml.text.paragraph import CT_P
from docx.table import _Cell, Table
from docx.text.paragraph import Paragraph


def iter_block_items(parent):
    """
    Yield each paragraph and table child within *parent*, in document order.
    Each returned value is an instance of either Table or Paragraph. *parent*
    would most commonly be a reference to a main Document object, but
    also works for a _Cell object, which itself can contain paragraphs and tables.
    """
    if isinstance(parent, Document):
        parent_elm = parent.element.body
    elif isinstance(parent, _Cell):
        parent_elm = parent._tc
    else:
        raise ValueError("something's not right")

    for child in parent_elm.iterchildren():
        if isinstance(child, CT_P):
            yield Paragraph(child, parent)
        elif isinstance(child, CT_Tbl):
            yield Table(child, parent)


def table_print(block):
    table=block
    for row in table.rows:
        print(row.height)
        for cell in row.cells:
            for paragraph in cell.paragraphs:
                print(paragraph.text,'  ',end='')
                #y.write(paragraph.text)
                #y.write('  ')
        print("\n")
        #y.write("\n")


data=docx.Document("2-1.docx")
parent_elm = data.element.body
#for child in parent_elm.iterchildren():
#    print(type(child))
    #if isinstance(child,CT_P):
    #    print(child.text)
for i in iter_block_items(data):
    print(type(i))
    if isinstance(i,docx.text.paragraph.Paragraph) :
        #print(i.height)
        print(i.paragraph_format.first_line_indent)
        print(i.paragraph_format.page_break_before)
        print(i.text)
    elif isinstance(i, docx.table.Table ):
        table_print(i)
