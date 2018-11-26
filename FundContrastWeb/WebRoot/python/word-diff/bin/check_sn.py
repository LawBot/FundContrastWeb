import re


# 查找文本中的编号[正则匹配，需完善]
def find_sn(txt_file):
    sn_type1 = re.compile(r"^(\d+). .*")
    sn_type2 = re.compile(r"^(\d+.\d+) .*")
    sn_type3 = re.compile(r"^(\d+.\d+.\d+) .*")
    # sn_type4 = re.compile(r"^([a-zA-Z]). .*")
    # sn_type5 = re.compile(r"^([a-z]\)) .*")
    patterns = [sn_type1, sn_type2, sn_type3]
    match_result = []
    for pattern in patterns:
        exp_lines = []
        for line in open(txt_file).readlines():
            obj = pattern.findall(line)
            if obj:
                exp_lines.append([obj[0], line])
        match_result.append(exp_lines)
    return find_exp(match_result)


# 检查编号顺序
def find_exp(match_result):
    level = 0
    exp_result = []
    for pat_result in match_result:
        last_sn = level*[1] + [0]
        for sn_line in pat_result:
            sn = [int(i) for i in sn_line[0].split(".")]
            if level == 0 and not (sn[0] == last_sn[0] + 1):
                exp_result.append(sn_line)
            elif level == 1 and not ((sn[0] == last_sn[0] and sn[1] == last_sn[1] + 1) or (sn[0] > last_sn[0] and sn[1] == 1)):
                exp_result.append(sn_line)
            elif level == 2 and not((sn[0] == last_sn[0] and sn[1] == last_sn[1] and sn[2] == last_sn[2] + 1) or ((sn[0] > last_sn[0] or sn[1] > last_sn[1]) and sn[2] == 1)):
                exp_result.append(sn_line)
            last_sn = sn
        level += 1
    return exp_result


if __name__ == '__main__':
    print(find_sn("C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\tmp\\e14.txt"))
