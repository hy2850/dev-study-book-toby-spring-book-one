create table if not exists users
(
    id
    varchar
(
    255
) primary key, name varchar
(
    255
), password varchar
(
    255
), level int, loginCnt int, recommendCnt int, email varchar
(
    255
));