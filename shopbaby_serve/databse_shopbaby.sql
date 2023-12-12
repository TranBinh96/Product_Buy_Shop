create database  shopbabay;
use  shopbaby;

create table product_images(
    id int primary key auto_increment,
    product_id int,
    name varchar(50),
        foreign key (product_id)  references products(id),
        CONSTRAINT fk_product_images_product_id
        foreign key (product_id) references products(id) on delete cascade,
    image_url varchar(300)
);

create table order_details
(
    id                 int primary key auto_increment,
    order_id           int,
    foreign key (order_id) references orders (id),
    product_id         int,
    foreign key (product_id) references products (id),
    price              float check (price >= 0 ),
    number_of_products int check (number_of_products > 0),
    color              varchar(20) default ''
);



alter table  orders modify status enum('pending','processing','shipped','delivered','cancelled') comment 'status order';

create  table  orders(
    id int primary key  auto_increment,
    user_id int,
    foreign key (user_id) references users(id),
    fullname varchar(100) default '',
    email varchar(100) default '',
    phone_number varchar(20) not null,
    address varchar(200) not null ,
    note varchar(100) default '',
    order_date datetime default CURRENT_TIMESTAMP,
    status varchar(20),
    total_money float check ( total_money >= 0 ),
    shipping_method varchar(100),
    shipping_address varchar(100),
    shipping_date date,
    tracking_number varchar(100),
    payment_method varchar(100),
    active tinyint(1)

);


alter table users add column role_id int;
alter table  users add foreign key (role_id)  REFERENCES roles(id);

create  table roles(
    id int auto_increment primary key ,
    name varchar(20) not null
);

create  table products(
    id int primary key auto_increment,
    name varchar(350) comment 'name product',
    price float not null  check ( price >=0  ),
    thumbnail varchar(300) default  '',
    description varchar(350) default '',
    create_at timestamp,
    update_at timestamp,
    category_id int,
    foreign key (category_id) references categories(id)
);

create table categories(
    id int primary key auto_increment,
    name varchar(100) not null default '' comment 'ten danh muc,vd do dien tu'

);

create table social_accounts(
    id int primary key auto_increment,
    provider varchar(20) not null comment 'name home social network',
    provider_id varchar(50) not null ,
    email varchar(150) not null comment 'Email account',
    name varchar(100) not null  comment 'name username',
    user_id int,
    foreign key (user_id) references users(id)
);

create table tokens(
    id int primary key auto_increment,
    token varchar(255) unique not null ,
    token_type varchar(50) not null ,
    expiration_date datetime,
    revoked tinyint(1) not null ,
    expired tinyint(1) not null ,
    user_id int,
    foreign key (user_id) references users(id)
);


create  table users(
    id int AUTO_INCREMENT PRIMARY KEY ,
    fullname varchar(50) default '',
    phone_number varchar(10) default  '',
    address varchar(255) default  '',
    password varchar(255) not null  default '',
    create_at datetime,
    update_at datetime,
    is_active tinyint(1) default 1,
    date_of_birth date,
    facebook_account_id int default 0,
    google_account_id int default  0
);
