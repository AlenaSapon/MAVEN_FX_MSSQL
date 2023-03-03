

CREATE TABLE DEPARTMENTS (
DEP_INDEX	 [int]  IDENTITY(10,10) NOT NULL PRIMARY KEY,
DEP_NAME	[varchar](20)	NOT NULL,
SITY	[varchar](20)	NOT NULL,
REGION	[varchar](20)	NOT NULL,
CONSTRAINT DEP_NAME_UK UNIQUE NONCLUSTERED(DEP_NAME)
);

CREATE TABLE EMPLOYEE (
STAFF_CODE [int]  IDENTITY(1000,1) NOT NULL PRIMARY KEY,
FULL_NAME 	[varchar](30)	NOT NULL,
DEP_INDEX	[int]	NOT NULL,
CONSTRAINT DEP_INDEX_FK FOREIGN KEY (DEP_INDEX)
  REFERENCES DEPARTMENTS (DEP_INDEX)
);

CREATE TABLE SUPPLIES (
INNER_CODE [int]  IDENTITY(1000,1) NOT NULL PRIMARY KEY,
SUPPL_NAME	[varchar](20)	NOT NULL,
SUPPL_DESCR	[varchar](20)	NULL,
CONS_RATE	[float]	NULL);

CREATE TABLE ORDERS (
SYNT_CODE [int]  IDENTITY(1000,1) NOT NULL PRIMARY KEY,
ORDER_NUM [int]  NOT NULL,
SUPPLIER	[varchar](20)	NOT NULL,
ORDER_DATE	[Date]	NOT NULL,
STAFF_CODE [INT] NOT NULL,
AMOUNT	decimal(10,2)	NULL, 
CONSTRAINT STAFF_CODE_FK FOREIGN KEY (STAFF_CODE)
  REFERENCES EMPLOYEE (STAFF_CODE)
);
ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_UK UNIQUE  (ORDER_NUM,SUPPLIER,ORDER_DATE);


CREATE TABLE ORDERS_DESC (
SYNT_CODE [int]  NOT NULL,
INNER_CODE [int]  NOT NULL,
VEND_CODE [int]  NOT NULL,
SUPPL_DESCRIPTION	[varchar](20) NULL,
PRICE	DECIMAL(10,2)	NULL,
QTY [INT] NOT NULL,
AMOUNT	DECIMAL(10,2) NULL,
CONSTRAINT ORDERS_DESC_PK PRIMARY KEY (SYNT_CODE,INNER_CODE,VEND_CODE),
CONSTRAINT SYNT_CODE_FK FOREIGN KEY (SYNT_CODE)
  REFERENCES ORDERS (SYNT_CODE),
 CONSTRAINT INNER_CODE_FK FOREIGN KEY (INNER_CODE)
  REFERENCES SUPPLIES (INNER_CODE)
);

INSERT INTO DEPARTMENTS VALUES ('DIRECTORATE','NEW-YORK','EAST');
INSERT INTO DEPARTMENTS VALUES ('FINANCE','PHILADELPHIA','EAST');
INSERT INTO DEPARTMENTS VALUES ('RESEARCHING','HOUSTON', 'SOUTH');
INSERT INTO DEPARTMENTS VALUES ('EXECUTIVE','LOS-ANGELES','WEST');
INSERT INTO DEPARTMENTS VALUES ('PERSPECTIVE', 'SEATTLE','WEST');

INSERT INTO SUPPLIES VALUES ('PAPER A4', 'FOR PRINTERS',1.5);
INSERT INTO SUPPLIES VALUES ('PAPER A3', 'FOR PRINTERS',1.0);
INSERT INTO SUPPLIES VALUES ('PAPER A0', 'FOR PLOTTERS',0.1);
INSERT INTO SUPPLIES VALUES ('SCOTCH', 'FOR VISITORS',null);
INSERT INTO SUPPLIES VALUES ('CARTRIDGE', 'FOR PRINTERS', 0.1);
INSERT INTO SUPPLIES VALUES ('PEN', 'FOR WRITING',5.1);
INSERT INTO SUPPLIES VALUES ('OFFICE CHAIR', 'FOR WORK',0.03);
INSERT INTO SUPPLIES VALUES ('FOLDER', 'FOR PAPERS',1.02);

INSERT INTO EMPLOYEE VALUES ('TOM SMITH', 10);
INSERT INTO EMPLOYEE VALUES ('KEANU REEVES', 10);
INSERT INTO EMPLOYEE VALUES ('MORPHEUS', 10);
INSERT INTO EMPLOYEE VALUES ('TRINITY', 10);
INSERT INTO EMPLOYEE VALUES ('ARNOLD SCHWARZENEGGER', 20);
INSERT INTO EMPLOYEE VALUES ('ARTHUR CONAN DOYLE', 20);
INSERT INTO EMPLOYEE VALUES ('JOHN CAMPBELL', 20);
INSERT INTO EMPLOYEE VALUES ('FRANK HERBERT', 20);
INSERT INTO EMPLOYEE VALUES ('DAN SIMMONS', 20);
INSERT INTO EMPLOYEE VALUES ('H. G. Wells', 20);
INSERT INTO EMPLOYEE VALUES ('JULES VERNE', 30);
INSERT INTO EMPLOYEE VALUES ('GORDON FREEMAN', 30);
INSERT INTO EMPLOYEE VALUES ('ANAKIN SKYWALKER', 40);

INSERT INTO ORDERS VALUES(15,'CHARLIE SHOP','2022-01-05',1000, NULL);
INSERT INTO ORDERS VALUES(15,'WALMART','2022-01-05',1000, NULL);
INSERT INTO ORDERS VALUES(1152,'AMAZON','2022-01-05',1000, NULL);
INSERT INTO ORDERS VALUES(11,'TESLA','2022-01-05',1000, NULL);
INSERT INTO ORDERS VALUES(16,'CHARLIE SHOP','2022-01-05',1000, NULL);
INSERT INTO ORDERS VALUES(25,'CHARLIE SHOP','2022-10-10',1000, NULL);
INSERT INTO ORDERS VALUES(45,'WALMART','2022-12-12',1000, NULL);

INSERT INTO ORDERS VALUES(17,'CHARLIE SHOP','2022-01-05',1001, NULL);
INSERT INTO ORDERS VALUES(1115,'WALMART','2022-09-12',1002, NULL);
INSERT INTO ORDERS VALUES(1255,'AMAZON','2023-01-01',1005, NULL);
INSERT INTO ORDERS VALUES(1111,'TESLA','2022-01-05',1003, NULL);
INSERT INTO ORDERS VALUES(1321,'CHARLIE SHOP','2022-01-01',1010, NULL);
INSERT INTO ORDERS VALUES(5422,'AMAZON','2022-10-10',1011, NULL);
INSERT INTO ORDERS VALUES(6545,'CHARLIE SHOP','2022-12-12',1011, NULL);


INSERT INTO ORDERS_DESC VALUES(1000, 1000,134545, 'WHITE PAPER', 5.4,1,NULL);
INSERT INTO ORDERS_DESC VALUES(1000, 1000,1125, 'WHITE PAPER', 5.4,2,NULL);
INSERT INTO ORDERS_DESC VALUES(1001, 1000,134545, 'WHITE PAPER', 5.4,3,NULL);
INSERT INTO ORDERS_DESC VALUES(1001, 1000,1125, 'WHITE PAPER', 5.4,4,NULL);
INSERT INTO ORDERS_DESC VALUES(1003, 1001,134545, 'WHITE PAPER', 5.4,5,NULL);
INSERT INTO ORDERS_DESC VALUES(1004, 1005,112455, 'PEN BLUE', 5.4,6,NULL);
INSERT INTO ORDERS_DESC VALUES(1005, 1005,45646, 'PEN', 5.4,7,NULL);
INSERT INTO ORDERS_DESC VALUES(1006, 1004,5467, 'X SOP', 5.4,8,NULL);
INSERT INTO ORDERS_DESC VALUES(1003, 1005,9867, 'RED PEN', 5.4,9,NULL);
INSERT INTO ORDERS_DESC VALUES(1004, 1006,1125, 'CARTRIDGE', 5.4,10,NULL);
INSERT INTO ORDERS_DESC VALUES(1005, 1005,9987, 'BLACK PEN', 5.4,8,NULL);
INSERT INTO ORDERS_DESC VALUES(1006, 1004,4564, 'ANY', 5.4,8,NULL);
INSERT INTO ORDERS_DESC VALUES(1007, 1003,097676, 'SMART BUY', 5.4,8,NULL);
INSERT INTO ORDERS_DESC VALUES(1008, 1002,3354, 'SHOPAHOLIC', 5.4,10,NULL);
INSERT INTO ORDERS_DESC VALUES(1009, 1001,233, 'PRINT PAPER', 5.4,12,NULL);
INSERT INTO ORDERS_DESC VALUES(1010, 1001,433, 'PHOTO PAPER', 5.4,15,NULL);

UPDATE ORDERS_DESC SET AMOUNT=QTY*PRICE;
UPDATE ORDERS SET AMOUNT=(SELECT sum(AMOUNT) FROM ORDERS_DESC WHERE SYNT_CODE=ORDERS.SYNT_CODE);