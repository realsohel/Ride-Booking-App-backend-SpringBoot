INSERT INTO app_user (name,email, password) VALUES
('Ayesha', 'ayesha@mail.com', 'ayeshaKLJGyhEAAAdgfFAjkl'),
('Sami', 'sami@mail.com', 'samidfkgjlsgxLKHvbnMVLmno'),
('Nida', 'nida@mail.com', 'nidaPOIRtyDFGTvbnJHMGJii'),
('Zain', 'zain@mail.com', 'zainlmJHKDLksjgjkuiPMLmko'),
('Rizwan', 'rizwan@mail.com', 'rizwanMLKJHYTQXOPsdfghjk'),
('Fatima', 'fatima@mail.com', 'fatimaxyzOPIUYTREWQLmsd'),
('Irfan', 'irfan@mail.com', 'irfanLKJHYTRsdgjvnZXCVbnm'),
('Sara', 'sara@mail.com', 'saraLKJHGVDFtgyhbnXZmnpoi'),
('Aman', 'aman@mail.com', 'amanMLKHYTGRewsdfXZVCBbji'),
('Salman', 'salman@mail.com', 'salmanJKLHGvbnmsdfgrtWQs'),
('Farah', 'farah@mail.com', 'farahQWERTYUIDFvbnmsdfgt'),
('Kabir', 'kabir@mail.com', 'kabirLKJHGTFUPOIlmnvbnwq'),
('Nasreen', 'nasreen@mail.com', 'nasreenLMNBVCDXZQsdfggh'),
('Imran', 'imran@mail.com', 'imranLKJHFDFGHxcvnzxcvbn'),
('Zoya', 'zoya@mail.com', 'zoyaLKJHGFDDFGvcvbnmZXCJ'),
('Armaan', 'armaan@mail.com', 'armaanPOIUYTREWQXmnbdfgi'),
('Meera', 'meera@mail.com', 'meeraJKLHGDcvbnmXCZRTYU'),
('Azhar', 'azhar@mail.com', 'azharLOIUYTREWSDFXcvbnml'),
('Parveen', 'parveen@mail.com', 'parveenMNBVCSDFGHJTYvbnm'),
('Rahul', 'rahul@mail.com', 'rahulJHGFDSASDFGHcvbnmZX');


INSERT INTO user_entity_roles(user_entity_id, roles) VALUES
(1, 'RIDER'),
(2, 'RIDER'),
(2, 'DRIVER'),
(3, 'RIDER'),
(3, 'DRIVER'),
(4, 'RIDER'),
(4, 'DRIVER'),
(5, 'RIDER'),
(5, 'DRIVER'),
(6, 'RIDER'),
(6, 'DRIVER'),
(7, 'RIDER'),
(7, 'DRIVER'),
(8, 'RIDER'),
(8, 'DRIVER'),
(9, 'RIDER'),
(9, 'DRIVER'),
(10, 'RIDER'),
(10, 'DRIVER'),
(11, 'RIDER'),
(11, 'DRIVER'),
(12, 'RIDER'),
(12, 'DRIVER'),
(13, 'RIDER'),
(13, 'DRIVER'),
(14, 'RIDER'),
(14, 'DRIVER'),
(15, 'RIDER'),
(15, 'DRIVER'),
(16, 'RIDER'),
(16, 'DRIVER'),
(17, 'RIDER'),
(17, 'DRIVER'),
(18, 'RIDER'),
(18, 'DRIVER'),
(19, 'RIDER'),
(19, 'DRIVER'),
(20, 'RIDER'),
(20, 'DRIVER');

INSERT INTO rider(user_id,rating) VALUES
(1,4.7);

INSERT INTO drivers (user_id,rating, available, current_location) VALUES
(2,4.7, true, ST_GeomFromText('POINT(77.1025 28.7041)', 4326)),
(3, 4.8, true, ST_GeomFromText('POINT(72.8611 19.0177)', 4326)),
(4, 4.6, true, ST_GeomFromText('POINT(72.9480 18.9323)', 4326)),
(5, 4.9, true, ST_GeomFromText('POINT(72.7988 19.1620)', 4326)),
(6, 4.7, true, ST_GeomFromText('POINT(72.8491 19.0108)', 4326)),
(7, 4.5, true, ST_GeomFromText('POINT(72.9695 19.2377)', 4326)),
(8, 4.8, true, ST_GeomFromText('POINT(72.8147 19.0272)', 4326)),
(9, 4.6, true, ST_GeomFromText('POINT(72.7907 19.1850)', 4326)),
(10, 4.7, true, ST_GeomFromText('POINT(72.9767 19.0397)', 4326)),
(11, 4.9, true, ST_GeomFromText('POINT(72.8668 18.9009)', 4326)),
(12, 4.8, true, ST_GeomFromText('POINT(72.9293 18.9237)', 4326)),
(13, 4.7, true, ST_GeomFromText('POINT(72.9075 19.2039)', 4326)),
(14, 4.6, true, ST_GeomFromText('POINT(72.8035 19.0652)', 4326)),
(15, 4.9, true, ST_GeomFromText('POINT(72.9618 19.2603)', 4326)),
(16, 4.8, true, ST_GeomFromText('POINT(72.7768 18.9830)', 4326)),
(17, 4.7, true, ST_GeomFromText('POINT(72.9123 18.9698)', 4326)),
(18, 4.5, true, ST_GeomFromText('POINT(72.9141 19.1952)', 4326)),
(19, 4.8, true, ST_GeomFromText('POINT(72.9242 19.1829)', 4326)),
(20, 4.7, true, ST_GeomFromText('POINT(72.9090 19.0123)', 4326));

INSERT INTO wallet(user_id,balance) VALUES
(1,5000),
(2,500);