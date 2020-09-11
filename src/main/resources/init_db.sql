CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;
#USE `internetshop`;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
                            `product_id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(256) NOT NULL,
                            `price` double NOT NULL DEFAULT '0',
                            PRIMARY KEY (`product_id`),
                            UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(256) NOT NULL,
                         `login` varchar(256) NOT NULL,
                         `password` varchar(256) NOT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
                         `role_id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(256) NOT NULL,
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `roles` (name) VALUES ('USER');
INSERT INTO `roles` (name) VALUES ('ADMIN');

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
                               `user_id` bigint NOT NULL,
                               `role_id` bigint NOT NULL,
                               KEY `FK_users_idx` (`user_id`),
                               KEY `FK_roles_idx` (`role_id`),
                               CONSTRAINT `FK_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
                               CONSTRAINT `FK_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;