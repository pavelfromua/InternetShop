#CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;
USE `internetshop`;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
                            `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                            `name` varchar(256) NOT NULL,
                            `price` double NOT NULL DEFAULT '0',
                            PRIMARY KEY (`product_id`),
                            UNIQUE KEY `name_UNIQUE` (`name`)
);

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(256) NOT NULL,
                         `login` varchar(256) NOT NULL,
                         `password` varchar(256) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `login_UNIQUE` (`login`)
);

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
                         `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(256) NOT NULL,
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `name_UNIQUE` (`name`)
);

INSERT INTO `roles` (name) VALUES ('USER');
INSERT INTO `roles` (name) VALUES ('ADMIN');

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
                               `user_id` BIGINT(11) NOT NULL,
                               `role_id` BIGINT(11) NOT NULL,
                               CONSTRAINT `FK_roles_users` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
                               CONSTRAINT `FK_users_roles` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `shopping_carts`;

CREATE TABLE `shopping_carts` (
                                  `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                  `user_id` BIGINT(11) NOT NULL,
                                  PRIMARY KEY (`cart_id`),
                                  CONSTRAINT `FK_shopping_carts_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `shopping_carts_products`;

CREATE TABLE `shopping_carts_products` (
                               `cart_id` BIGINT(11) NOT NULL,
                               `product_id` BIGINT(11) NOT NULL,
                               CONSTRAINT `FK_shopping_carts_products` FOREIGN KEY (`cart_id`) REFERENCES `shopping_carts` (`cart_id`),
                               CONSTRAINT `FK_products_shopping_carts` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
);

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
                        `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                        `user_id` BIGINT(11) NOT NULL,
                        PRIMARY KEY (`order_id`),
                        CONSTRAINT `FK_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `orders_products`;

CREATE TABLE `orders_products` (
                                `order_id` BIGINT(11) NOT NULL,
                                `product_id` BIGINT(11) NOT NULL,
                                CONSTRAINT `FK_orders_products` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
                                CONSTRAINT `FK_products_orders` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
);

SET FOREIGN_KEY_CHECKS = 1;