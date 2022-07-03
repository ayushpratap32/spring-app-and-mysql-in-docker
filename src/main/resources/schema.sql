CREATE TABLE product(
product_id varchar(20)  NOT NULL,
product_title varchar(40) NOT NULL,
quantity integer NOT NULL,
price varchar(10) NOT NULL,
CONSTRAINT pk_product_product_id PRIMARY KEY (product_id)
);