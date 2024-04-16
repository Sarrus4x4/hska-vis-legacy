CREATE DATABASE IF NOT EXISTS db_kategorien;

CREATE USER 'kategorienuser'@'%' IDENTIFIED BY 'ThePassword1';

GRANT ALL ON db_kategorien.* TO 'kategorienuser'@'%';