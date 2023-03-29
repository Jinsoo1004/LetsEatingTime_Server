use let;
CREATE TABLE file (
                      idx int(11) NOT NULL,
                      path varchar(128) NOT NULL,
                      name varchar(128) NOT NULL,
                      PRIMARY KEY (idx)
) DEFAULT CHARSET=utf8;
CREATE TABLE user (
                      idx int(11) NOT NULL AUTO_INCREMENT,
                      image int(11),
                      school_number char(5) NOT NULL UNIQUE,
                      password varchar(256) NOT NULL,
                      name varchar(64) NOT NULL,
                      create_time datetime NOT NULL,
                      meal_application char(1) NOT NULL DEFAULT('N'),
                      user_type char(1) NOT NULL DEFAULT('S'),
                      PRIMARY KEY (idx),
                      CONSTRAINT fk_user_file_idx FOREIGN KEY (image) REFERENCES file (idx)
) DEFAULT CHARSET=utf8;
CREATE TABLE card (
                      idx int(11) NOT NULL AUTO_INCREMENT,
                      user_id int(11) NOT NULL,
                      nfc_id int(11) UNIQUE NOT NULL,
                      status char(1) DEFAULT('W') NOT NULL,
                      create_time datetime NOT NULL,
                      last_time datetime NOT NULL,
                      PRIMARY KEY (`idx`),
                      CONSTRAINT fk_card_user_idx FOREIGN KEY (user_id) REFERENCES user (idx)
) DEFAULT CHARSET=utf8;
CREATE TABLE entry (
                       idx int(11) NOT NULL AUTO_INCREMENT,
                       user_id int(11) NOT NULL,
                       card_id int(11) NOT NULL,
                       entry_time datetime NOT NULL,
                       status char(1) DEFAULT('N') NOT NULL,
                       meal_type char(1) DEFAULT('N') NOT NULL,
                       PRIMARY KEY (idx),
                       CONSTRAINT fk_entry_user_idx FOREIGN KEY (user_id) REFERENCES user (idx),
                       CONSTRAINT fk_entry_card_idx FOREIGN KEY (card_id) REFERENCES card (idx)
) DEFAULT CHARSET=utf8;