create database db_autentic; -- Create the new database
create user 'springUser'@'localhost' identified by 'ThePassword'; -- Creates the user
grant all on db_autentic.* to 'springuser'@'localhost'; -- Gives all the privileges to the new user on the newly created databaseuser

CREATE TABLE products(
productId INTEGER UNSIGNED PRIMARY KEY,
productName CHAR(50),
productAmount INT(8),
productLocation CHAR(20)
);

CREATE TABLE sales(
saleId INTEGER UNSIGNED PRIMARY KEY,
productId INTEGER,
saleValue INT(8)
);