import win32com.client as win32


# 英文标点符号识别警报
def find_en_punctuation(line):
    p_list = []
    for word in line:
        if word in ",!?:;'\"":
            p_list.append(word)
    if p_list:
        return [line, p_list]
    else:
        return None


# 全角字符报警
def find_q_str(line):
    q_list = []
    for word in line:
        inside_code = ord(word)
        if inside_code >= 65281 and inside_code <= 65374:
            q_list.append(word)
    if q_list:
        return [line, q_list]
    else:
        return None


# 判断字符是否为中文
def is_Chinese(word):
    for ch in word:
        if '\u4e00' <= ch <= '\u9fff':
            return True
    return False


# 判断是否为英文
def is_alphabet(uchar):
    if (uchar >= u'\u0041' and uchar <= u'\u005a') or (uchar >= u'\u0061' and uchar <= u'\u007a'):
        return True
    else:
        return False


# 字体检查
def find_font_exp(doc_path):
    try:
        word = win32.gencache.EnsureDispatch('Word.Application')
        document = word.Documents.Open(doc_path)
        allow_fonts = [r"宋体", r"Times New Roman"]
        for para in document.Paragraphs:
            exp_word = []
            for word in para.Range.Words:
                word_font = word.Font.Name
                word_str = str(word).strip()
                if word_str == "":
                    continue
                if is_Chinese(word_str) and word_font != allow_fonts[0] and word_font != "":
                    exp_word.append(word_str)
                elif is_alphabet(word_str) and word_font != allow_fonts[1] and word_font != "":
                    exp_word.append(word_str)
            if exp_word:
                print([para.Range.Text, ",".join(exp_word)])
    except Exception as e:
        print(e)
    finally:
        document.Close(False)


if __name__ == '__main__':
    find_font_exp(r"C:\Users\huangzhouzhou\Documents\GitHub\word-diff\tmp\e14.docx")
