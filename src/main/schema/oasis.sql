DROP DATABASE IF EXISTS IIICEStseB;
CREATE DATABASE IIICEStseB DEFAULT CHARACTER SET utf8;
USE IIICEStseB;


# CREATE USER if not exists 'iii'@'3.dwxh.xyz' IDENTIFIED BY 'iii';
# grant all on IIICEStseB.* to 'iii'@'3.dwxh.xyz';
# flush privileges;

##创建机构表
CREATE TABLE affiliation
(
    id   INT PRIMARY KEY auto_increment comment '机构id',
    name varchar(300) comment '机构名'
) ENGINE = InnoDB comment '机构表'
  ROW_FORMAT = DYNAMIC;
create index affiliation_name_hash using hash on affiliation (name);

##创建作者表
CREATE TABLE author
(
    id             INT auto_increment PRIMARY KEY comment '作者id',
    name           VARCHAR(80) comment '作者姓名',
    first_name     varchar(40) comment '姓',
    last_name      varchar(40) comment '名',
    affiliation_id int comment '作者所属机构id',
    foreign key (affiliation_id) references affiliation (id)
) ENGINE = InnoDB comment '作者表';
create index author_name_hash using hash on author (name);

##创建会议表
CREATE TABLE conference
(
    id   INT PRIMARY KEY auto_increment comment '会议id',
    name VARCHAR(100) comment '会议名称'
) ENGINE = InnoDB comment '会议表';
create index conference_name_hash using hash on conference (name);

##创建文献表
CREATE TABLE paper
(
    id                    INT PRIMARY KEY auto_increment comment '文献id',
    pdf_url               text comment '文献pdf连接',
    author_keywords       text comment '作者设定的文章关键词',
    title                 varchar(300) comment '文献标题',
    paper_abstract        text comment '摘要',
    doi                   varchar(40) comment 'DOI',
    publication_title     varchar(200) comment '文献所属出版刊物名称',
    citation_count_paper  int comment '文献被引次数',
    citation_count_patent int comment '专利被引次数',
    total_downloads       int comment '文献下载量',
    start_page            varchar(15) comment '起始页',
    end_page              varchar(15) comment '终止页',
    pub_link              text comment '所属会议的总页面',
    issue_link            text comment '这篇文章的某一次会议的链接',
    publisher             varchar(10) comment '刊物所属出版社',
    conf_loc              varchar(50) comment '会议地点',
    chron_date            date comment '发表日期',
    article_id            int comment 'ieee设立的文献id',
#外键
    conference_id         int comment '文献所属会议id',
    foreign key (conference_id) references conference (id)
) ENGINE = InnoDB comment '文献表'
  ROW_FORMAT = DYNAMIC;
create index paper_title_hash using hash on paper (title);
create index idx_article_id using hash on paper (article_id);
create index idx_chron_date on paper (chron_date);


##创建发表文献关系表
CREATE TABLE paper_authors
(
    author_id    int comment '作者id',
    paper_id     int comment '文献id',
    author_order int comment '发表作者次序',
    foreign key (author_id) references author (id),
    foreign key (paper_id) references paper (id),
    primary key (author_id, paper_id)
) ENGINE = InnoDB comment '作者发表文献_关系表';
create index idx_paper_id on paper_authors (paper_id);

##创建受控标引标准表
CREATE TABLE term
(
    id   INT PRIMARY KEY auto_increment comment '受控标引id',
    name varchar(80) comment '受控标引名'
) ENGINE = InnoDB comment '受控标引表';
create index term_standard_name_hash using hash on term (name);

##创建文献术语关系表
CREATE TABLE paper_term
(
    paper_id int comment '文献id',
    term_id  int comment '受控id',
    foreign key (term_id) references term (id),
    foreign key (paper_id) references paper (id),
    primary key (paper_id, term_id)
) ENGINE = InnoDB comment '文献术语关系表';
create index idx_term_id on paper_term (term_id);

##创建参考文献表
create table reference
(
    id                  int primary key auto_increment comment '参考文献表',
    reference_order     int comment '被引次序',
    text                text comment '被引内容',
    title               varchar(300) comment '被引文章标题',
    google_scholar_link text comment '被引文献url',
    ref_type            varchar(20) comment '引用类型',
    article_id          int comment '引用文章的ieee id',
    foreign key (article_id) references paper (article_id)
);

##创建用户表
CREATE TABLE user
(
    id              INT PRIMARY KEY auto_increment comment '用户id',
    username        varchar(32) comment '用户名' unique,
    password        varchar(32) comment '密码',
    privilege_level varchar(16) comment '权限等级'
) ENGINE = InnoDB comment '用户表';
create index username_hash using hash on user (username);
insert into user value (1, 'root', '123456', '管理员');
insert into user value (3, '1234', '123qwe', '管理员');

##创建浏览记录表
CREATE TABLE record
(
    id            INT PRIMARY KEY auto_increment comment '历史记录id',
    search_record text comment '搜索记录字段',
    user_id       int,
    foreign key (user_id) references user (id) on delete Cascade
) ENGINE = InnoDB comment '历史记录';

##创建作者统计信息表
create table author_statistics
(
    author_id INT not null unique,
    h_index   double,
    g_index   double,
    foreign key (author_id) references author (id) on delete Cascade
) ENGINE = InnoDB comment '作者统计信息';

##创建论文统计信息表
create table paper_statistics
(
    paper_id INT not null unique,
    score    double,
    foreign key (paper_id) references paper (id) on delete Cascade
) ENGINE = InnoDB comment '论文统计信息';

