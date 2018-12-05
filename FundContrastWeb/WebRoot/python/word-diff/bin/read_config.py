#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import configparser


# 获取config配置文件
def getConfig():
    config = configparser.ConfigParser()
    path = os.path.split(os.path.realpath(__file__))[0] + r'\..\config\check.config'
    config.read(path, encoding="utf-8")
    return config


# if __name__ == '__main__':
#     config = getConfig()
#     allow_fonts = [config.get("font_limit", "ch_font").split(";"), config.get("font_limit", "en_font").split(";")]
#     print(allow_fonts)
