DROP DATABASE IF EXISTS oasisdb;
CREATE DATABASE oasisdb DEFAULT CHARACTER SET utf8;
USE oasisdb;

##创建机构表
CREATE TABLE t_affiliation (
    affiliation_id   INT AUTO_INCREMENT PRIMARY KEY,
    affiliation VARCHAR(30)
)ENGINE=InnoDB;

##创建作者表
CREATE TABLE t_author (
    author_id   INT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(30),
    author_organization_id int,
    foreign key (author_organization_id) references  t_affiliation(affiliation_id)
)ENGINE=InnoDB;

##创建发行机构表
CREATE TABLE t_publisher (
    publisher_id   INT AUTO_INCREMENT PRIMARY KEY,
    publisher_name VARCHAR(30)
)ENGINE=InnoDB;

##创建会议表
CREATE TABLE t_conference (
    conference_id   INT AUTO_INCREMENT PRIMARY KEY,
    conference_name VARCHAR(30)
)ENGINE=InnoDB;

##创建文献表
CREATE TABLE t_paper (
                         paper_id   INT AUTO_INCREMENT PRIMARY KEY,
                         paper_publisher_id int,
                         paper_conference_id int,
                         pdf_url varchar(200),
                         DOI varchar(30),
                         paper_title varchar(200),
                         abstract varchar(2000),
                         reference_count int,
                         article_citation_count int,
                         publication_title varchar(100),
                         publication_year int,
                         start_page varchar(12),
                         end_page varchar(12),
                         author_word varchar(100),
                         foreign key (paper_conference_id) references t_conference(conference_id),
                         foreign key (paper_publisher_id) references  t_publisher(publisher_id)
)ENGINE=InnoDB;

##创建发表文献关系表
CREATE TABLE t_publish (
            publish_id   INT AUTO_INCREMENT PRIMARY KEY,
            publish_author_id int,
            publish_paper_id int,
            foreign key (publish_author_id) references t_author(author_id),
            foreign key (publish_paper_id) references t_paper(paper_id)
)ENGINE=InnoDB;

##创建术语机构种类表
CREATE TABLE t_organization (
                      organization_id   INT AUTO_INCREMENT PRIMARY KEY,
                      organization_name varchar(23)
)ENGINE=InnoDB;

##创建术语表
CREATE TABLE t_term (
                      term_id   INT AUTO_INCREMENT PRIMARY KEY,
                      term_organization int,
                      term_word varchar(20),
                    foreign key (term_organization) references t_organization(organization_id)
)ENGINE=InnoDB;

##创建文献术语联系表
CREATE TABLE t_paper_term (
                        paper_term_id   INT AUTO_INCREMENT PRIMARY KEY,
                        paper_term_paper_id int,
                        paper_term_term_id int,
                        foreign key (paper_term_term_id) references t_term(term_id)
)ENGINE=InnoDB;

##创建浏览记录表
CREATE TABLE t_browse_record (
                                 browse_record_id   INT AUTO_INCREMENT PRIMARY KEY,
                                search_word varchar(100),
                                browse_context varchar(200)

)ENGINE=InnoDB;


##创建用户表
CREATE TABLE t_user (
                        user_id   INT AUTO_INCREMENT PRIMARY KEY,
                        user_browse_record int,
                        username varchar(32),
                        password varchar(32),
                        privilege_level int,
                        foreign key (user_browse_record) references t_browse_record(browse_record_id)
)ENGINE=InnoDB;