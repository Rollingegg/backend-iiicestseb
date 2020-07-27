# -*- coding: utf-8 -*-

"""
@File  : test_ieee.py
@Author: SteveLai
@Time  : 7/21/2020 3:02 PM
@Software: PyCharm
"""

import json
import re
import random
import sys
import time
import threading
import inspect
import ctypes
import os

import requests

# ===========================CUSTOM SETTING==============================
# 设置连接超时
timeout = 10

# 下列是一些著名的国际会议，是手动在浏览器获取的
start_conference_ids = {
    'ICSE': '1000691',  # Software Engineering (ICSE), International Conference on
    'ASE': '1000064',  # Automated Software Engineering (ASE), IEEE International Conference
    'CVPR': '1000147',  # Computer Vision and Pattern Recognition (CVPR), Conference on
    'ICCV': '1000149',  # Computer Vision (ICCV), International Conference on
    'VTS': '1000804',  # VLSI Test Symposium
    'ICMLA': '1001544',  # Machine Learning and Applications (ICMLA), International Conference on
    'ISCA': '1000123',  # Computer Architecture, ISCA, Annual International Symposium on
    'ICIP': '1000349',  # Image Processing, IEEE International Conference
    'DSN': '1000192',  # Dependable Systems and Networks (DSN), International Conference on
}
# host
ieee_url = 'https://ieeexplore.ieee.org'
# 会议
conference_url = 'https://ieeexplore.ieee.org/rest/publication/conhome/metadata?parentId={parentId}'
# 具体到年份的会议
concrete_conference_url = 'https://ieeexplore.ieee.org/rest/search/pub/{publicationNumber}/issue/{issueNumber}/toc'
# 文章详情
paper_url = 'https://ieeexplore.ieee.org/document/{articleNumber}'
# 文章引用列表
paper_ref_url = 'https://ieeexplore.ieee.org/rest/document/{articleNumber}/references'

headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Accept-Encoding': 'gzip, deflate, sdch, br',
    'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2,mt;q=0.2',
    'Connection': 'keep-alive',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36'
}


# ===========================FUNC UTILS==============================
def log(info: str):
    print('[crawler]' + info, flush=True)


def get_referer_header(url: str):
    new_headers = headers
    new_headers.update({"Referer": url})
    return new_headers


def ieee_info(html: str):
    paper = dict(
        title='title' + html,
        authors=[] if int(html) < 15 else [
            {"name": "author1", "affiliation": "affiliation1", "firstName": "Au", "lastName": "thor1", "id": "1"},
            {"name": "author2", "affiliation": "affiliation2", "firstName": "Au", "lastName": "thor2", "id": "2"},
            {"name": "author3", "affiliation": "affiliation3", "firstName": "Au", "lastName": "thor3", "id": "3"}],
        abstract='abstract' + html,
        keywords=[],
        doi='doi' + html,
        articleId=html,
        metrics={"citationCountPaper": 0, "citationCountPatent": 0, "totalDownloads": 0},
        startPage=str(random.randint(1, 10)),
        endPage=str(random.randint(11, 20)),
        pdfLink='pdfLink' + html,
        pubLink='pubLink' + html,
        issueLink='issueLink' + html,
        publisher='publisher' + html,
        confLoc='confLoc' + html,
        chronDate="2019-2020"
    )
    return paper


class IeeeSpider:
    def __init__(self, search_conference: str, start_year: str, end_year: str, output_file: str):
        """
        传入命令行参数
        :param search_conference:
        :param start_year:
        :param end_year:
        :param output_file:
        """
        self.search_conference = search_conference
        self.start_year = int(start_year)
        self.end_year = int(end_year)
        self.out_file = open(output_file, 'w+')
        self.session = requests.Session()
        self.session.headers = headers
        self.con_params = list()
        self.total_article_param_list = list()
        self.total_article_num = 0

    def run(self):
        self.start_request()
        self.scratch_articles()
        self.out_file.close()
        log('end')

    def start_request(self):
        """
        从获取会议下的子会议开始，并最终获取待爬取的文献参数，存入 total_article_param_list 中
        :return:
        """
        time.sleep(2)
        self.con_params = [{"punumber": str(random.randint(100000, 200000)),
                            "issueNumber": random.randint(100000, 200000),
                            "publicationTitle": 'publicationTitle'}]
        log('subConferenceNum : ' + str(len(self.con_params)))
        # 遍历子会议，第一次查询先找出待爬取的总文献数
        for param in self.con_params:
            time.sleep(0.5)
            tmp = random.randint(1, 10)
            con_data = {"totalPages": 2, "totalRecords": 3, "records": [
                {'articleNumber': 'articleNumber' + str(tmp)}
            ]}
            # print(con_data)
            param.update({"totalPages": int(con_data['totalPages'])})
            self.total_article_num += int(con_data['totalRecords'])
            for record in con_data['records']:
                self.total_article_param_list.append({
                    'articleNumber': record['articleNumber'],
                    'publicationTitle': param['publicationTitle']
                })

        # 继续遍历子会议，并通过post请求获得所有的文献id
        index = 0
        for param in self.con_params:
            index += 1
            log('current : ' + str(index))
            for page_num in range(2, param['totalPages'] + 1):
                # log('getSubConference : {0} / {1} [{2}]'.format(page_num, param['totalPages'],
                #                                                 param['publicationTitle']))
                param.update({'pageNumber': page_num})
                time.sleep(0.5)
                tmp = random.randint(1, 20)
                if tmp > 2:
                    con_data = {"totalPages": 2, "totalRecords": 11, "records": [
                        {'articleNumber': 'articleNumber' + str(i)} for i in range(tmp, tmp + 10)
                    ]}
                    for record in con_data['records']:
                        self.total_article_param_list.append({
                            'articleNumber': record['articleNumber'],
                            'publicationTitle': param['publicationTitle']
                        })
                else:
                    continue

        log('paperNum : ' + str(self.total_article_num))

    def scratch_articles(self):
        """
        利用上一步获取的文章id，进一步获取文章详情和引用列表
        :return:
        """
        for i in range(len(self.total_article_param_list)):
            log('current : ' + str(i + 1))
            cur_pub_title = self.total_article_param_list[i]['publicationTitle']
            cur_article_number = self.total_article_param_list[i]['articleNumber']
            try:
                time.sleep(1)
                tmp = random.randint(1, 20)
                if tmp > 2:
                    cur_paper = ieee_info(str(tmp))
                    if len(cur_paper.get('authors')) == 0:
                        log('exception : Invalid paper')
                        continue
                    cur_paper.update({'conferenceName': self.search_conference})
                    cur_paper.update({'publication': cur_pub_title})
                    cur_paper.update(
                        {'ref': self.get_reference(paper_ref_url.format(articleNumber=cur_article_number))})
                    self.out_file.write(json.dumps(cur_paper) + '\n')
                    self.out_file.flush()
                    time.sleep(0.1)
                else:
                    raise Exception("Test Exception")
            except Exception:
                log('exception : Go on next paper')

    def get_reference(self, url: str):
        time.sleep(0.5)
        reference = list()
        return reference


def _async_raise(tid, exctype):
    """raises the exception, performs cleanup if needed"""
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctype):
        exctype = type(exctype)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("invalid thread id")
    elif res != 1:
        # """if it returns a number greater than one, you're in trouble,
        # and you should call it again with exc=NULL to revert the effect"""
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError("PyThreadState_SetAsyncExc failed")


def stop_thread(thread):
    _async_raise(thread.ident, SystemExit)


class SpiderThread(threading.Thread):
    def __init__(self, spider: IeeeSpider):
        self.spider = spider

    def run(self):
        self.func()


if __name__ == '__main__':
    log('start')
    con = sys.argv[1].split('=')[1]
    sy = sys.argv[2].split('=')[1]
    ey = sys.argv[3].split('=')[1]
    out = sys.argv[4].split('=')[1]
    spider = IeeeSpider(con, sy, ey, out)
    spider_thread = threading.Thread(target=spider.run)
    spider_thread.start()

    cmd = input()
    if cmd == 'cancel':
        stop_thread(spider_thread)
        spider.out_file.close()
        if os.path.exists(out):
            os.remove(out)
        log('cancel')

