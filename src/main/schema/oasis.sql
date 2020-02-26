DROP DATABASE IF EXISTS oasisdb;
CREATE DATABASE oasisdb DEFAULT CHARACTER SET utf8;
USE oasisdb;

##创建机构表
CREATE TABLE t_affiliation (
                               affiliation_id   INT AUTO_INCREMENT PRIMARY KEY comment '机构id',
                               affiliation VARCHAR(30) comment '机构名'
)ENGINE=InnoDB comment '机构表';

##创建作者表
CREATE TABLE t_author (
                          author_id   INT AUTO_INCREMENT PRIMARY KEY comment '作者id',
                          author_name VARCHAR(30) comment '作者姓名',
                          author_organization_id int    comment '作者所属机构id',
                          foreign key (author_organization_id) references  t_affiliation(affiliation_id)
)ENGINE=InnoDB comment '作者表';

##创建发行机构表
CREATE TABLE t_publisher (
                             publisher_id   INT AUTO_INCREMENT PRIMARY KEY comment '出版社id',
                             publisher_name VARCHAR(30) comment '出版社名称'
)ENGINE=InnoDB comment '出版社表';

##创建会议表
CREATE TABLE t_conference (
                              conference_id   INT AUTO_INCREMENT PRIMARY KEY comment '会议id',
                              conference_name VARCHAR(30) comment '会议名称'
)ENGINE=InnoDB comment '会议表';

##创建文献表
CREATE TABLE t_paper (
                         paper_id   INT AUTO_INCREMENT PRIMARY KEY comment '文献id',
                         publication_title varchar(100) comment '文献所属出版刊物名称',
                         paper_publisher_id int comment '刊物所属出版社id',
                         paper_conference_id int  comment  '文献所属会议id',
                         pdf_url varchar(200) comment '文献pdf连接',
                         DOI varchar(30) comment  'DOI',
                         paper_title varchar(200) comment '文献标题',
                         abstract varchar(2000) comment  '摘要',
                         reference_count int comment  '参考资料数',
                         article_citation_count int comment '文献被引次数',
                         publication_year int comment '出版年份',
                         start_page int comment '起始页',
                         end_page int comment '终止页',
                         author_word varchar(100) comment '文章关键词',
                         document_identifer varchar(20) comment 'document_identifer',
                         foreign key (paper_conference_id) references t_conference(conference_id),
                         foreign key (paper_publisher_id) references  t_publisher(publisher_id)
)ENGINE=InnoDB comment '文献表';

##创建发表文献关系表
CREATE TABLE t_publish (
                           publish_id   INT AUTO_INCREMENT PRIMARY KEY comment '作者发表文献_关系id',
                           publish_author_id int comment '作者id',
                           publish_paper_id int comment '文献id',
                           foreign key (publish_author_id) references t_author(author_id),
                           foreign key (publish_paper_id) references t_paper(paper_id)
)ENGINE=InnoDB comment '作者发表文献_关系表';

##创建术语标准表
CREATE TABLE t_standard (
                            standard_id   INT AUTO_INCREMENT PRIMARY KEY comment '术语标准id',
                            standard_name varchar(23) comment '术语标准名'
)ENGINE=InnoDB comment '术语标准表';

##创建术语表
CREATE TABLE t_term (
                        term_id   INT AUTO_INCREMENT PRIMARY KEY comment '术语id',
                        term_standard int comment '术语标准来源id',
                        term_word varchar(20) comment '术语',
                        foreign key (term_standard) references t_standard(standard_id)
)ENGINE=InnoDB comment '术语表';

##创建文献术语关系表
CREATE TABLE t_paper_term (
                              paper_term_id   INT AUTO_INCREMENT PRIMARY KEY comment '文献术语_关系id',
                              paper_term_paper_id int comment '文献id',
                              paper_term_term_id int comment '术语id',
                              foreign key (paper_term_term_id) references t_term(term_id)
)ENGINE=InnoDB comment '文献术语_关系表';

##创建浏览记录表
CREATE TABLE t_record (
                                record_id   INT AUTO_INCREMENT PRIMARY KEY comment '历史记录id',
                                 search_word_record varchar(200) comment '搜索记录字段',
                                 browse_record varchar(200) comment '历史浏览记录'
)ENGINE=InnoDB comment '历史记录';


##创建用户表
CREATE TABLE t_user (
                        user_id   INT AUTO_INCREMENT PRIMARY KEY comment '用户id',
                        user_record int comment '用户浏览记录id',
                        username varchar(32) comment '用户名',
                        password varchar(32) comment '密码',
                        privilege_level int comment '权限等级',
                        foreign key (user_record) references t_record(record_id)
)ENGINE=InnoDB comment '用户表';