DROP DATABASE IF EXISTS oasisdb;
CREATE DATABASE oasisdb DEFAULT CHARACTER SET utf8;
USE oasisdb;

##创建机构表
CREATE TABLE affiliation (
                             id   INT AUTO_INCREMENT PRIMARY KEY comment '机构id',
                             name varchar(200) comment '机构名'
)ENGINE=InnoDB comment '机构表';
create index affiliation_name_hash using hash on affiliation(name);

##创建作者表
CREATE TABLE author (
                        id   INT AUTO_INCREMENT PRIMARY KEY comment '作者id',
                        name VARCHAR(40) comment '作者姓名',
                        affiliation_id int    comment '作者所属机构id',
                        foreign key (affiliation_id) references  affiliation(id)
)ENGINE=InnoDB comment '作者表';
create index author_name_hash using hash on author(name);

##创建发行机构表
CREATE TABLE publisher (
                           id   INT AUTO_INCREMENT PRIMARY KEY comment '出版社id',
                           name varchar(200) comment '出版社名称'
)ENGINE=InnoDB comment '出版社表';
create index publisher_name_hash using hash on publisher(name);

##创建会议表
CREATE TABLE conference (
                            id   INT AUTO_INCREMENT PRIMARY KEY comment '会议id',
                            name VARCHAR(100) comment '会议名称'
)ENGINE=InnoDB comment '会议表';
create index conference_name_hash using hash on conference(name);

##创建文献表
CREATE TABLE paper (
                       id   INT AUTO_INCREMENT PRIMARY KEY comment '文献id',
                       publication_title varchar(100) comment '文献所属出版刊物名称',
                       publisher_id int comment '刊物所属出版社id',
                       conference_id int  comment  '文献所属会议id',
                       pdf_link text comment '文献pdf连接',
                       DOI varchar(40) comment  'DOI',
                       paper_title varchar(200) comment '文献标题',
                       paper_abstract text comment  '摘要',
                       reference_count int comment  '参考资料数',
                       citation_count int comment '文献被引次数',
                       publication_year timestamp comment '出版年份',
                       start_page varchar(15) comment '起始页',
                       end_page varchar(15) comment '终止页',
#                          author_keywords varchar(100) comment '文章关键词',
                       document_identifier varchar(30) comment 'document_identifier',
                       foreign key (conference_id) references conference(id),
                       foreign key (publisher_id) references  publisher(id)
)ENGINE=InnoDB comment '文献表';
create index paper_title_hash using hash on paper(paper_title);

##创建发表文献关系表
CREATE TABLE publish (
                         id   INT AUTO_INCREMENT PRIMARY KEY comment '作者发表文献_关系id',
                         author_id int comment '作者id',
                         paper_id int comment '文献id',
                         foreign key (author_id) references author(id),
                         foreign key (paper_id) references paper(id)
)ENGINE=InnoDB comment '作者发表文献_关系表';

# ##创建术语分类标准表
# CREATE TABLE term_standard (
#                             id   INT AUTO_INCREMENT PRIMARY KEY comment '术语标准id',
#                             name varchar(30) comment '术语标准名'
# )ENGINE=InnoDB comment '术语标准表';
# create index term_standard_name_hash using hash on term_standard(name);
# use sec;
# insert into term_standard (id, name) VALUES (1, 'IEEE Terms'), (2, 'INSPEC Controlled Terms'), (3, 'INSPEC Non-Controlled Terms'), (4, 'Mesh Terms');

##创建术语表
CREATE TABLE term (
                      id   INT AUTO_INCREMENT PRIMARY KEY comment '术语id',
#                       standard_id int comment '术语标准来源id',
                      word varchar(80) comment '术语'
#                       foreign key (standard_id) references term_standard(id)
)ENGINE=InnoDB comment '术语表';
create index term_word_hash using hash on term(word);

##创建文献术语关系表
CREATE TABLE paper_term (
                            id   INT AUTO_INCREMENT PRIMARY KEY comment '文献术语_关系id',
                            paper_id int comment '文献id',
                            term_id int comment '术语id',
                            foreign key (term_id) references term(id),
                            foreign key (paper_id) references paper(id)
)ENGINE=InnoDB comment '文献术语_关系表';

##创建浏览记录表
CREATE TABLE record (
                        id   INT AUTO_INCREMENT PRIMARY KEY comment '历史记录id',
                        search_record text comment '搜索记录字段',
                        browse_record text comment '历史浏览记录'
)ENGINE=InnoDB comment '历史记录';


##创建用户表
CREATE TABLE user (
                      id   INT AUTO_INCREMENT PRIMARY KEY comment '用户id',
                      record_id int comment '用户浏览记录id',
                      username varchar(32) comment '用户名' unique ,
                      password varchar(32) comment '密码',
                      privilege_level varchar(20) comment '权限等级',
                      foreign key (record_id) references record(id)
)ENGINE=InnoDB comment '用户表';
create index username_hash using hash on user(username);