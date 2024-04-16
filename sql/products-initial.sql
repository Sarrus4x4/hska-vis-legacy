CREATE DATABASE IF NOT EXISTS db_products;

CREATE USER 'productsuser'@'%' IDENTIFIED BY 'ThePassword2';

GRANT ALL ON db_products.* TO 'productsuser'@'%';