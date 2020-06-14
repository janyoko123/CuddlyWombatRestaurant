use mydb;

CREATE TABLE staff (
    name varchar(35) NOT NULL,
    id varchar(35) NOT NULL,
    password varchar(35) NOT NULL,
    role varchar(35) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE items (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    price float NOT NULL,
    takeout boolean,
    dinein boolean,
    PRIMARY KEY(id)
);

CREATE TABLE reservations (
	id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
    num int NOT NULL,
    table_type varchar(50) NOT NULL,
    date_time timestamp NOT NULL,
    active boolean NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE orders (
	id int NOT NULL AUTO_INCREMENT,
    table_id varchar(25) NOT NULL,
    price float DEFAULT 0.0,
    status varchar(50) NOT NULL,
    date_time timestamp NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE order_detail (
	id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    item_id int NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE tables (
	id varchar(20) NOT NULL,
    order_id int NOT NULL,
    seats int NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE customers (
	id int NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE invoices (
	id int NOT NULL AUTO_INCREMENT,
    table_id varchar(20) NOT NULL,
    order_id int NOT NULL,
    staff_id varchar(35) not NULL,
    date_time timestamp not null,
    bill float not null,
    amount_paid float not null,
    payment_method varchar(20) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE profit (
	date_time timestamp not null unique,
    amount float not null
);
