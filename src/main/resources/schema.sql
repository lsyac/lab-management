create table if not exists `user`
(
    id char(19) primary key,
    name varchar(6) not null ,
    account char(10) not null ,
    password char(60) not null,
    role varchar(5) not null ,
    avatar longtext null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    unique (account)
);

create table if not exists `course`
(
    id char(19) primary key ,
    name varchar(6) not null ,
    quantity tinyint unsigned not null ,
    lesson tinyint unsigned not null ,
    class_info varchar(6) not null ,
    major varchar(20) not null ,
    teacher_id char(19) not null ,
    index (teacher_id)
);
create table if not exists `lab`
(
    id char(19) primary key ,
    name varchar(10) not null ,
    manage varchar(6) null ,
    quantity tinyint unsigned null ,
    description varchar(200) null ,
    state tinyint unsigned not null default 1,
    index (state)
);

create table if not exists `lab_reservation`
(
    id char(19) primary key ,
    course_id char(19) not null ,
    course_name varchar(6) not null ,
    lab_id char(19) not null ,
    lab_name varchar(10) not null ,
    week tinyint unsigned not null ,
    day_of_week tinyint unsigned not null ,
    section tinyint unsigned not null ,
    teacher_id char(19) not null ,
    teacher_name varchar(6) not null ,
    reservation_time datetime not null default current_timestamp,
    unique (lab_id,week,day_of_week,section),
    index (course_id)
);

create table if not exists `announcement` (
      id char(19) primary key,
      title varchar(100) not null,
      content text not null,
      create_time datetime not null default current_timestamp,
      update_time datetime not null default current_timestamp on update current_timestamp
);
