DROP TABLE IF EXISTS formula;

CREATE TABLE formula
 (
 id int PRIMARY KEY AUTO_INCREMENT,
 name varchar(30) NOT NULL,
 detail varchar(100),
 year int NOT NULL,
 month int NOT NULL,
 day int NOT NULL
 );
 
 INSERT INTO
    formula (id,name,detail,year,month,day)
VALUES
    (1,'20日後','ゆず酒',0,0,20),
    (2,'8ヵ月後','味噌',0,8,0),
    (3,'10年後','居間LED',10,0,0),
    (4,'3年7ヵ月前','車購入日',3,7,0);