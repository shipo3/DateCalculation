 DROP ALL OBJECTS;
 
 CREATE TABLE IF NOT EXISTS formula
 (
 id int PRIMARY KEY AUTO_INCREMENT,
 name varchar(30) NOT NULL,
 detail varchar(100),
 year int NOT NULL,
 month int NOT NULL,
 day int NOT NULL
 );
