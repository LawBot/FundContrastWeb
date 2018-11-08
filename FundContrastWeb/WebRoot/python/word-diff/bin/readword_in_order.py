import docx
from docx.document import Document
from docx.oxml.table import CT_Tbl
from docx.oxml.text.paragraph import CT_P
from docx.table import _Cell, Table
from docx.text.paragraph import Paragraph
from docx.opc.constants import RELATIONSHIP_TYPE as RT


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


# 获取表格内容，格式为[(内容,标注)..]
def table_print(table, table_index):
    table_paragraphs = []
    max_cell_count = 0
    for row in table.rows:
        if len(row.cells) > max_cell_count:
            max_cell_count = len(row.cells)
    row_count = len(table.rows)
    for i in range(0, max_cell_count):
        for j in range(0, row_count):
            cells = table.rows[j].cells
            if len(cells) > i:
                cell_p = [p.text for p in cells[i].paragraphs]
                table_paragraphs.append((" ".join(cell_p), "表%d第%d行第%d列" % (table_index, j+1, i+1)))
    return table_paragraphs


# 读取文档内容，格式为[(内容,标注)..]
def read_word(path):
    data = docx.Document(path)
    paragraphs = []
    table_index = 1
    for i in iter_block_items(data):
        if isinstance(i, docx.text.paragraph.Paragraph):
            paragraphs.append((i.text, ""))
        elif isinstance(i, docx.table.Table):
            paragraphs += table_print(i, table_index)
            table_index += 1
    return paragraphs


# print(read_word("C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e10.docx"))
