INSERT INTO ROLE(ID, ADD_FEATURES,	CD_PLACE,	CRUD_PLACE,	CRUD_REQUESTS,
	CRUD_RESERVATION,	CRUD_USERS,	NAME,	READ_MY_RESERVATIONS, has_Reservation_Mine_Only_Privilege,
                 has_Reservation_Read_Privilege, has_Reservation_Create_Privilege, has_Reservation_Delete_Privilege,
                 has_Reservation_Update_Privilege, has_Reservation_My_Offers_Privilege, has_Reservation_Cancel_Privilege)
VALUES(1, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, 'USER', TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE),
        (2, FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, 'PLACE_OWNER', TRUE, FALSE, TRUE, FALSE, FALSE, FALSE, TRUE, TRUE),
        (3, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, 'SYS_ADMIN', TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE);

INSERT INTO USER(ID, NAME, ROLE_ID, BLOCKED) VALUES(1001, 'PC1', 2, FALSE);
INSERT INTO USER(ID, NAME, ROLE_ID, BLOCKED) VALUES(1004, 'PC2', 2, FALSE);
INSERT INTO USER(ID, NAME, ROLE_ID, BLOCKED) VALUES(1002, 'SA1', 3, FALSE);
INSERT INTO USER(ID, NAME, ROLE_ID, BLOCKED) VALUES(1003, 'USR1', 1, FALSE);

INSERT INTO OFFER(ID, DESCRIPTION, NAME, OWNER_ID)VALUES (1001, 'OFERERERE', 'HOTEL', 1001);
INSERT INTO OFFER(ID, DESCRIPTION, NAME, OWNER_ID)VALUES (1002, '7D67SDF', 'RESTURACJA', 1002);

INSERT INTO FEATURE(ID,NAME,DESCRIPTION)
VALUES
    (1,'Testowy 1','Opis 1'),
    (2,'Testowy 2','Opis 2'),
    (3,'Testowy 3','Opis 3');
-- SELECT * FROM USER U
-- JOIN ROLE R ON U.ROLE_ID = R.ID


-- INSERT INTO RESERVATION(ID, CREATEDAT, DATEFROM, DATETO, STATUS, USERID, PLACEID) VALUES(1001, 1646218997, 1646132597, 1647255797, 'PENDING', 1001, 1001);
-- INSERT INTO RESERVATION(ID, CREATEDAT, DATEFROM, DATETO, STATUS, USERID, PLACEID) VALUES(1002, 1646218997, 1646132597, 1647255797, 'PENDING', 1001, 1001);
-- INSERT INTO RESERVATION(ID, CREATEDAT, DATEFROM, DATETO, STATUS, USERID, PLACEID) VALUES(1003, 1646218997, 1646132597, 1647255797, 'PENDING', 1001, 1001);