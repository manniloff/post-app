INSERT INTO user (login, password, active, role) VALUES ('admin','admin', 1,'ADMIN'), ('user','user',1,'USER');

INSERT INTO category (name) VALUES ('Politics'), ('IT'),('Business');

INSERT INTO post (title, create_date, body, user_id) VALUES ('Politics Post', CURRENT_TIMESTAMP, 'Some text about politics', 2), ('IT Post', CURRENT_TIMESTAMP, 'Some text about IT', 1),('Business Post', CURRENT_TIMESTAMP, 'Some text about business', 2);

INSERT INTO comment (text, posted_date, post_id, user_id) VALUES ('Politics Comment', CURRENT_TIMESTAMP, 1, 2), ('IT Post', CURRENT_TIMESTAMP, 2, 1),('Business Post', CURRENT_TIMESTAMP, 3, 2);

INSERT INTO post_category_relation (category_id, post_id) VALUES (1, 1), (2, 2),(3, 3);