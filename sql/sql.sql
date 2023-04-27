CREATE TABLE `file` (
                        `idx` int NOT NULL,
                        `path` varchar(128) NOT NULL,
                        `name` varchar(128) NOT NULL,
                        PRIMARY KEY (`idx`)
) DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user` (
                        `idx` int NOT NULL AUTO_INCREMENT,
                        `image` int DEFAULT NULL,
                        `id` varchar(128) NOT NULL,
                        `password` varchar(256) NOT NULL,
                        `name` varchar(64) NOT NULL,
                        `create_time` datetime NOT NULL,
                        `grade` tinyint NOT NULL,
                        `class_name` tinyint NOT NULL,
                        `class_no` tinyint NOT NULL,
                        `user_type` char(1) NOT NULL DEFAULT 'N',
                        `meal_application` char(1) NOT NULL DEFAULT 'N',
                        `approved_yn` char(1) NOT NULL DEFAULT 'N',
                        `withdrawed_yn` char(1) NOT NULL DEFAULT 'N',
                        `withdrawed_time` datetime DEFAULT NULL,
                        `refresh_token` char(65) DEFAULT NULL,
                        PRIMARY KEY (`idx`),
                        UNIQUE KEY `id_UNIQUE` (`id`),
                        UNIQUE KEY `idx_UNIQUE` (`idx`),
                        KEY `fk_user_file_idx` (`image`),
                        CONSTRAINT `fk_user_file_idx` FOREIGN KEY (`image`) REFERENCES `file` (`idx`)
) DEFAULT CHARSET=utf8mb3;

CREATE TABLE `card` (
                        `idx` int NOT NULL AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `nfc_id` int NOT NULL,
                        `status` char(1) NOT NULL DEFAULT 'W',
                        `create_time` datetime NOT NULL,
                        `last_time` datetime NOT NULL,
                        PRIMARY KEY (`idx`),
                        UNIQUE KEY `nfc_id` (`nfc_id`),
                        KEY `fk_card_user_idx` (`user_id`),
                        CONSTRAINT `fk_card_user_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`idx`)
) DEFAULT CHARSET=utf8mb3;

CREATE TABLE `entry` (
                         `idx` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL,
                         `card_id` int NOT NULL,
                         `entry_time` datetime NOT NULL,
                         `status` char(1) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL DEFAULT 'N',
                         `type` varchar(32) CHARACTER SET ascii COLLATE ascii_general_ci NOT NULL,
                         PRIMARY KEY (`idx`),
                         KEY `fk_entry_user_idx` (`user_id`),
                         KEY `fk_entry_card_idx` (`card_id`),
                         CONSTRAINT `fk_entry_card_idx` FOREIGN KEY (`card_id`) REFERENCES `card` (`idx`),
                         CONSTRAINT `fk_entry_user_idx` FOREIGN KEY (`user_id`) REFERENCES `user` (`idx`)
) DEFAULT CHARSET=utf8mb3;

CREATE TABLE `access` (
                          `idx` int NOT NULL AUTO_INCREMENT,
                          `user_id` int NOT NULL,
                          `grant_id` int NOT NULL,
                          `type` varchar(32) NOT NULL,
                          `grant_time` datetime NOT NULL,
                          PRIMARY KEY (`idx`),
                          UNIQUE KEY `idx_UNIQUE` (`idx`),
                          KEY `fk_account_user_idx_1_idx` (`user_id`),
                          KEY `fk_account_user_idx_2_idx` (`grant_id`),
                          CONSTRAINT `fk_account_user_idx_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`idx`),
                          CONSTRAINT `fk_account_user_idx_2` FOREIGN KEY (`grant_id`) REFERENCES `user` (`idx`)
) DEFAULT CHARSET=utf8mb3;

CREATE TABLE `opening` (
                           `idx` int NOT NULL AUTO_INCREMENT,
                           `type` varchar(32) NOT NULL,
                           `info` varchar(128) DEFAULT NULL,
                           `date` date DEFAULT NULL,
                           `open_time` time DEFAULT NULL,
                           `close_time` time DEFAULT NULL,
                           PRIMARY KEY (`idx`)
) DEFAULT CHARSET=utf8mb3;