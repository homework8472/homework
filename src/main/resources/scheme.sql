CREATE TABLE categories (
    id BIGINT(20) NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE brands (
    id BIGINT(20) NOT NULL auto_increment PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE products (
    id BIGINT(20) NOT NULL auto_increment PRIMARY KEY,
    category VARCHAR(255),
    brand_id BIGINT,
    price INT,
);

create index idx_products_name on "brands" ("name");
create index idx_categories_name on "categories" ("name");
create index idx_products_categoryid_brandid on "products" ("category_id", "brand_id");