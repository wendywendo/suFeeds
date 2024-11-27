CREATE DATABASE IF NOT EXISTS sufeeds;

USE sufeeds;

CREATE TABLE tbl_students (
  username varchar(10) primary key,
  fName varchar(50),
  lName varchar(50),
  password varchar(30)
);

CREATE TABLE tbl_units (
    unitCode int primary key auto_increment,
    unitName varchar(60)
);

CREATE TABLE tbl_stdunits (
    stdUnitId int primary key auto_increment,
    unitCode int, foreign key (unitCode) references tbl_units (unitCode),
    username varchar(10), foreign key (username) references tbl_students (username),
    semester int
);


CREATE TABLE tbl_feeds (
    feedId int primary key auto_increment,
    topic varchar(50),
    comment varchar(255),
    username varchar(10), foreign key (username) references tbl_students (username),
    unitCode int, foreign key (unitCode) references tbl_units (unitCode)
);