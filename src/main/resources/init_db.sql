CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`products` (
                                           `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(150) NOT NULL,
                                           `price` DOUBLE NULL DEFAULT 0,
                                           PRIMARY KEY (`id`),
                                           UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)