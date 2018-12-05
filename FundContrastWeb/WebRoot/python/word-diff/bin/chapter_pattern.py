import re
import numpy

# 几种主要编号模式正则表达式 (需根据实际情况添加)
# 编号模式 1 (第N章，第N条...)
sn_p1 = re.compile(r"^(第[ ⅠⅤⅩ0-9一二三四五六七八九零十百]{1,7}[章节条款话篇部]{1,2})[\.、 :：]+.*")
# 编号模式 2 (一、二、三...)
sn_p2 = re.compile(r"^([一二三四五六七八九零十百]{1,5})[\.、 :：]+.*")
# 编号模式 3 (1,2,3...)
sn_p3 = re.compile(r"^(\d+)(?!\.\d+)[\.、 :：]+.*")
# 编号模式 4 (1.1,1.2,1.3...)
sn_p4 = re.compile(r"^(\d+\.+\d+)(?!\.\d+)[\.、 :：]+.*")
# 编号模式 5 (A,B,C...)
sn_p5 = re.compile(r"^([A-Z])[\.、 :：]+.*")
sn_p6 = re.compile(r"^([a-z])[\.、 :：]+.*")
# 编号模式 7 (Ⅰ,Ⅱ,Ⅲ...)
sn_p7 = re.compile(r"^([ⅠⅤⅩ]+)[\.、 :：]+.*")
# 编号模式 8 ((一),(二),(三)...)
sn_p8 = re.compile(r"^([\(（][ ]*[一二三四五六七八九零十百]{1,5}[ ]*[\)）]).*")
# 编号模式 9 ((1),(2),(3)...)
sn_p9 = re.compile(r"^([\(（][ ]*\d+[ ]*[\)）]).*")
# 编号模式 10 ((a),(b),(c)...)
sn_p10 = re.compile(r"^([\(（][ ]*[A-Z][ ]*[\)）]).*")
sn_p11 = re.compile(r"^([\(（][ ]*[a-z][ ]*[\)）]).*")
# 编号模式 12 ((Ⅰ),(Ⅱ),(Ⅲ)...)
sn_p12 = re.compile(r"^([\(（][ ]*[ⅠⅤⅩ]+[ ]*[\)）]).*")
# 其他编号
sn_other = re.compile(r"^(附[件录][ⅠⅤⅩ0-9一二三四五六七八九零十百]{1,7})[\.、 :：]+.*")
# 所有编号
# sn_all = re.compile(r"^([第（\( ]*[a-zA-Z0-9一二三四五六七八九零十百ⅠⅤⅩ]{1,7}[ 章节条款话篇部）\)]*)[\.、 :：]+.*")
sn_ps = [sn_p1, sn_p2, sn_p3, sn_p4, sn_p5, sn_p6, sn_p7, sn_p8, sn_p9, sn_p10, sn_p11, sn_p12]


# 为内容添加编号
def add_chapter(content):
    # 1.遍历文本内容，通过正则表达式获取编号
    pattern_indexs, content_add_chapter = match_sn(content)
    # 2.根据编号模式索引，推算一二级标题
    # for i, val in enumerate(pattern_indexs, 1):
    #     print("%d===>%s" % (i, val))
    [x, y] = get_top2_level(pattern_indexs)
    # print("level_1,level_2===>%d,%d" % (x, y))
    # 3.按一二级标题标记段落编号
    chapter_lv1 = "前言"
    chapter_lv2 = ""
    result = []
    for l in content_add_chapter:
        if l[-1]:
            result.append([l[-1], l[0]])
            continue
        if l[x]:
            chapter_lv1 = l[x]
            chapter_lv2 = ""
        elif l[-2]:
            chapter_lv1 = l[-2]
            chapter_lv2 = ""
        if l[y]:
            chapter_lv2 = l[y]
        chapter = "%s%s" % (chapter_lv1, "-%s" % chapter_lv2 if chapter_lv2 else "")
        result.append([chapter, l[0]])
    return result


# 通过正则表达式匹配文档内容，返回匹配结果(正则表达式匹配索引集合,文档内容匹配结果)
def match_sn(content, patterns=sn_ps):
    # 返回内容
    content_add_chapter = []
    pt_count = len(patterns)
    # 多种编号模式出现在文档中的索引集合
    ps_indexs = [[] for i in range(pt_count)]
    index = 1
    # 标记目录特殊处理(0.未开始,1.开始,-1.结束)
    catalog_status = 0
    for line in content:
        line = line.strip()
        line_obj = []
        if line == "":
            continue
        # 特殊目录处理：
        line_obj.append(line)
        if catalog_status == 0 and re.match(r"目[ ]*录", line):
            catalog_status = 1
            line_obj = line_obj + ["" for i in range(pt_count+1)] + ["目录"]
        elif catalog_status == 1 and re.match(r".*[\. \t]+\d+$", line):
            line_obj = line_obj + ["" for i in range(pt_count+1)] + ["目录"]
        else:
            if catalog_status == 1:
                catalog_status = -1
            # 1.匹配
            for i, pattern in enumerate(patterns):
                sn_obj = pattern.findall(line)
                # 保存匹配内容，供后续提取编号结构
                line_obj.append(sn_obj[0] if sn_obj else "")
                # 保存各编号模式匹配成功的索引及编号内容
                if sn_obj:
                    ps_indexs[i].append([index, sn_obj[0]])
            other_obj = sn_other.findall(line)
            line_obj = line_obj+[other_obj[0] if other_obj else "", ""]
        content_add_chapter.append(line_obj)
        index += len(line)
    return ps_indexs, content_add_chapter


# 根据编号模式参数，推算一二级标题
def get_top2_level(args):
    # 当编号模式未匹配或者只匹配一次时 忽略该模式编号
    nargs = [a for a in args if len(a) > 1]
    arg_count = len(nargs)
    if arg_count == 0:
        return [1, 2]
    elif arg_count == 1:
        return [args.index(nargs[0])+1, -1]
    else:
        # 计算各种编号模式下 编号间的平均距离
        args_avg = []
        for arg in nargs:
            # 对编号进行一次清洗，防止因符号或空格差异导致判断编号重复性时出现错误
            c_arg = [[a[0], clear_sn(a[1])] for a in arg]
            cpt = [i[1] for i in c_arg]
            # 计算编号间的平均间距(重新编号时,上一次编号结束到此次编号开始的距离忽略)
            arg_avg = [c_arg[i][0]-c_arg[i-1][0] for i in range(1, len(arg)) if cpt[:i+1].count(c_arg[i][1]) == cpt[:i+1].count(c_arg[i-1][1])]
            avg_val = numpy.average(arg_avg)
            args_avg.append([arg, avg_val])
        # 冒泡排序 根据平均跨度获取TOP2编号模式
        for i in range(0, arg_count):
            for j in range(i+1, arg_count):
                if args_avg[i][1] < args_avg[j][1]:
                    args_avg[i], args_avg[j] = args_avg[j], args_avg[i]
        return [args.index(args_avg[0][0])+1, args.index(args_avg[1][0])+1]


# 清洗编号
def clear_sn(sn_str):
    return re.sub(r" |\(|\)|（|）", "", sn_str)
