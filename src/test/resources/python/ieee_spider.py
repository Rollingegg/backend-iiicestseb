# -*- coding: utf-8 -*-

"""
@File  : ieee.py
@Author: SteveLai
@Time  : 7/13/2020 11:41 PM
@Software: PyCharm
"""

import json
import re
import sys
import time

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
    print('[crawler]' + info)


def get_referer_header(url: str):
    new_headers = headers
    new_headers.update({"Referer": url})
    return new_headers


def ieee_info(html: str):
    pattern = re.compile(r'metadata={.*};')
    content = json.loads(pattern.search(html).group()[9:-1])
    if 'title' in content:
        title = content['title']
    else:
        title = ''

    if 'authors' in content:
        authors = content['authors']
    else:
        authors = list()

    if 'abstract' in content:
        abstract = content['abstract']
    else:
        abstract = ''

    if 'keywords' in content:
        keywords = content['keywords']
    else:
        keywords = list()

    if 'doi' in content:
        doi = content['doi']
    else:
        doi = ''

    if 'metrics' in content:
        metrics = content['metrics']
    else:
        metrics = dict()

    if 'startPage' in content:
        startPage = content['startPage']
    else:
        startPage = ''

    if 'endPage' in content:
        endPage = content['endPage']
    else:
        endPage = ''

    if 'pubLink' in content:
        pubLink = content['pubLink']
    else:
        pubLink = ''

    if 'issueLink' in content:
        issueLink = ieee_url + content['issueLink']
    else:
        issueLink = ''

    if 'pdfPath' in content:
        pdfLink = ieee_url + content['pdfPath']
    else:
        pdfLink = ''

    if 'publisher' in content:
        publisher = content['publisher']
    else:
        publisher = ''

    if 'confLoc' in content:
        confLoc = content['confLoc']
    else:
        confLoc = ''

    if 'chronDate' in content:
        chronDate = content['chronDate']
    else:
        chronDate = ''

    if 'articleId' in content:
        articleId = content['articleId']
    else:
        articleId = ''

    paper = dict(
        title=title,
        authors=authors,
        abstract=abstract,
        keywords=keywords,
        doi=doi,
        articleId=articleId,
        metrics=metrics,
        startPage=startPage,
        endPage=endPage,
        pdfLink=pdfLink,
        pubLink=pubLink,
        issueLink=issueLink,
        publisher=publisher,
        confLoc=confLoc,
        chronDate=chronDate
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
        self.output = output_file
        self.session = requests.Session()
        self.session.headers = headers
        self.con_params = list()
        self.total_article_param_list = list()
        self.total_article_num = 0

    def run(self):
        self.start_request()
        self.scratch_articles()

    def start_request(self):
        """
        从获取会议下的子会议开始，并最终获取待爬取的文献参数，存入 total_article_param_list 中
        :return:
        """
        search_conference_url = conference_url.format(parentId=start_conference_ids[self.search_conference])
        r = self.session.get(url=search_conference_url, headers=get_referer_header(search_conference_url))
        if r.status_code == requests.codes.ok:
            con_data = r.json()
            # print(con_data)
            if con_data.get('records'):
                conference_list = con_data['records']
                for sub_con in conference_list:
                    con_year = int(sub_con['issues'][0]['year'])
                    if self.end_year >= con_year >= self.start_year:
                        self.con_params.append({
                            "punumber": sub_con['publicationNumber'],
                            "issueNumber": sub_con['issues'][0]['issueNumber'],
                            "publicationTitle": sub_con['displayTitle']
                        })

        log('subConferenceNum : ' + str(len(self.con_params)))
        # 遍历子会议，第一次查询先找出待爬取的总文献数
        for param in self.con_params:
            sub_conference_url = concrete_conference_url.format(publicationNumber=param['punumber'],
                                                                issueNumber=str(param['issueNumber']))
            r = self.session.post(url=sub_conference_url, json=param,
                                  headers=get_referer_header(sub_conference_url))
            if r.status_code == requests.codes.ok:
                con_data = r.json()
                # print(con_data)
                param.update({"totalPages": int(con_data['totalPages'])})
                self.total_article_num += int(con_data['totalRecords'])
                for record in con_data['records']:
                    self.total_article_param_list.append({
                        'articleNumber': record['articleNumber'],
                        'publicationTitle': param['publicationTitle']
                    })
            else:
                continue

        # 继续遍历子会议，并通过post请求获得所有的文献id
        index = 0
        for param in self.con_params:
            sub_conference_url = concrete_conference_url.format(publicationNumber=param['punumber'],
                                                                issueNumber=str(param['issueNumber']))
            index += 1
            log('current : ' + str(index))
            for page_num in range(2, param['totalPages'] + 1):
                # log('getSubConference : {0} / {1} [{2}]'.format(page_num, param['totalPages'],
                #                                                 param['publicationTitle']))
                param.update({'pageNumber': page_num})
                r = self.session.post(url=sub_conference_url, json=param,
                                      headers=get_referer_header(sub_conference_url))
                if r.status_code == requests.codes.ok:
                    con_data = r.json()
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
        with open(self.output, 'a') as f:
            for i in range(len(self.total_article_param_list)):
                log('current : ' + str(i + 1))
                cur_pub_title = self.total_article_param_list[i]['publicationTitle']
                cur_article_number = self.total_article_param_list[i]['articleNumber']
                cur_article_url = paper_url.format(articleNumber=cur_article_number)
                try:
                    r = self.session.get(url=cur_article_url,
                                         headers=get_referer_header(cur_article_url),
                                         timeout=timeout)
                    if r.status_code == requests.codes.ok:
                        cur_paper = ieee_info(r.text)
                        if len(cur_paper.get('authors')) == 0:
                            log('exception : Invalid paper')
                            continue
                        cur_paper.update({'conferenceName': self.search_conference})
                        cur_paper.update({'publication': cur_pub_title})
                        cur_paper.update(
                            {'ref': self.get_reference(paper_ref_url.format(articleNumber=cur_article_number))})
                        f.write(json.dumps(cur_paper) + '\n')
                        f.flush()
                        time.sleep(0.1)
                except Exception:
                    log('exception : Go on next paper')

    def get_reference(self, url: str):
        reference = list()
        r = self.session.get(url=url, headers=get_referer_header(url), timeout=timeout)
        if r.status_code == requests.codes.ok:
            content = r.json()
            for ref in content['references']:
                reference.append({
                    "order": ref['order'],
                    "title": ref['title'],
                    "text": ref['text'],
                    "googleScholarLink": ref['googleScholarLink'],
                    "refType": ref['refType']
                })
        return reference


if __name__ == '__main__':
    log('start')
    con = sys.argv[1].split('=')[1]
    sy = sys.argv[2].split('=')[1]
    ey = sys.argv[3].split('=')[1]
    out = sys.argv[4].split('=')[1]
    spider = IeeeSpider(con, sy, ey, out)
    spider.run()
    log('end')
