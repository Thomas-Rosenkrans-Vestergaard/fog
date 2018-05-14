SET SQL_SAFE_UPDATES = 0;

DELETE FROM purchases;
DELETE FROM offers;
DELETE FROM orders;
DELETE FROM sheds;

DELETE FROM attribute_definitions;
DELETE FROM attribute_values;
DELETE FROM garage_component_values;
DELETE FROM garage_component_definitions;
DELETE FROM garage_models;
DELETE FROM roofing_component_values;
DELETE FROM roofing_component_definitions;
DELETE FROM component_values;
DELETE FROM component_definitions;
DELETE FROM categories;
DELETE FROM materials;
DELETE FROM roofings;
DELETE FROM customers;
DELETE FROM roles;
DELETE FROM employees;
DELETE FROM floorings;
DELETE FROM claddings;

INSERT INTO claddings (id, name, description, active) VALUES
  (1, 'Beklædning 1',
   'Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.',
   b'1');

INSERT INTO floorings (id, name, description, active) VALUES
  (1, 'Betongulv',
   'Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.',
   b'1');

INSERT INTO `categories` VALUES
  (1, 'Brædde'),
  (2, 'Spærtræ'),
  (3, 'Taglægte'),
  (4, 'Stolpe'),
  (5, 'Tegl'),
  (6, 'Rygningssten'),
  (7, 'Toplægteholder'),
  (8, 'Rygningsstensbeslag'),
  (9, 'Tagstens bindere & nakkekroge'),
  (10, 'Andre'),
  (11, 'Skrue');

INSERT INTO attribute_definitions (id, category, data_type, `name`) VALUES
  (1, 5, 'INT', 'HEIGHT_MM'),
  (2, 5, 'INT', 'WIDTH_MM'),
  (3, 5, 'INT', 'LATH_DISTANCE_MM'),
  (4, 6, 'INT', 'LENGTH_MM'),
  (5, 7, 'INT', 'USE_DISTANCE_MM'),
  (7, 1, 'INT', 'THICKNESS_MM'),
  (8, 2, 'INT', 'WIDTH_MM'),
  (9, 2, 'INT', 'THICKNESS_MM'),
  (10, 3, 'INT', 'WIDTH_MM'),
  (11, 3, 'INT', 'THICKNESS_MM'),
  (12, 4, 'INT', 'THICKNESS_MM');

INSERT INTO materials (id, `number`, `description`, `price`, `unit`, `category`) VALUES
  (1, '960919471840', 'B&C Dobbelt sort', 1000, 1, 5),
  (2, '306606942576', 'B&C Rygsten sort', 4500, 1, 6),
  (3, '342599675955', 'B&C Toplægteholder', 3900, 1, 7),
  (4, '682229537074', 'B&C Rygstensbeslag', 25000, 50, 8),
  (5, '459740486968', 'B&C Tagstensbindere & Nakkekroge', 12500, 50, 9),
  (6, '343615203226', '19x100 mm. Trykimprægneret bræt', '30000', 6, 1),
  (7, '093765091763', '25x50 mm. Trykimprægneret Bræt', '30000', 6, 1),
  (8, '229786742441', '38x73 mm. Taglægte T1', '30000', 6, 3),
  (9, '788947415996', 'Fædigskåret (byg-selv spær)', 100000, 1, 2),
  (10, '448582215123', '97x97 mm. Trykimprægneret stolpe', 4000, 1, 4),
  (11, '851468122894', '45x195 spærtræ ubh.', 40000, 1, 2),
  (12, '234368028753', '45x95 Reglar ubh.', 40000, 1, 2),
  (13, '514432595305', 'Universal 190 mm højre', 4500, 1, 10),
  (14, '231983532544', 'Universal 190 mm venstre', 4500, 1, 10),
  (15, '448646179776', 'Stalddørsgreb 50x75', 8000, 1, 10),
  (16, '685076293556', 'T-hængsel 390 mm', 12000, 1, 10),
  (17, '346844119739', 'Vinkelbeslag', 500, 1, 10),
  (18, '763421115200', '4,5x60 mm. skruer 200 stk.', 35000, 200, 11),
  (19, '013735613112', '5,0x40 mm. beslagskruer 250 stk.', 16000, 250, 11),
  (20, '702351056728', '5,0x100 mm. skruer 100 stk.', 8500, 100, 11),
  (21, '322772016293', '4,5x70 mm. Skruer 200 stk.', 15000, 200, 11),
  (22, '347369061736', '4,5x50 mm. Skruer 350 stk.', 35000, 350, 11),
  (23, '419643103011', 'Bræddebolt 10 x 120 mm.', 1500, 1, 10),
  (24, '950209945881', 'Firkantskiver 40x40x11 mm.', 1500, 1, 10),
  (25, '234789560123', '25x150 mm. trykimp. Bræt', 2000, 1, 1);

INSERT INTO attribute_values (attribute, material, `value`) VALUES
  (1, 1, '420'),
  (2, 1, '330'),
  (3, 1, '320'),
  (4, 2, '420'),
  (5, 3, '900'),
  (6, 6, '100'),
  (7, 6, '19'),
  (6, 7, '50'),
  (7, 7, '25'),
  (8, 8, '73'),
  (9, 8, '38'),
  (9, 10, '97'),
  (8, 11, '195'),
  (9, 11, '45');

INSERT INTO roofings (id, name, description, active, type)
VALUES (1, 'Sorte teglsten',
        'Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.',
        b'1', 'TILED');

INSERT INTO component_definitions (id, identifier, category, notes) VALUES
  (1, 'ROOF_TILE', 5, 'Monteres på taglægter 6 rækker af 24 sten på hver side af taget'),
  (2, 'ROOF_RIDGE_TILE', 6, 'Monteres på toplægte med medfølgende beslag se tagstens vejledning'),
  (3, 'ROOF_RIDGE_LATH_HOLDER', 7, 'Monteres på toppen af spæret (til toplægte)'),
  (4, 'ROOF_RIDGE_TILE_BRACKET', 8, 'Til	montering af rygsten'),
  (5, 'ROOF_TILE_BINDER_AND_HOOKS', 9, 'Til	montering af tagsten, alle ydersten + hver anden fastgøres'),
  (6, 'ROOF_GABLE_CLADDING', 1, 'Beklædning af gavle 1 på 2'),
  (7, 'POST', 4, 'Stolper nedgraves 90 cm. i jord + skråstiver'),
  (8, 'STRAPS_GARAGE', 2, 'Remme i sider, sadles ned i stolper Carport del'),
  (9, 'STRAPS_SHED', 2, 'Remme i sider, sadles ned i stolper Skur del'),
  (10, 'SHED_SIDE_NOGGING', 2, 'Løsholter i siderne af skur'),
  (11, 'SHED_GABLE_NOGGING', 2, 'Løsholter i gavle af skur'),
  (12, 'WATER_BOARD', 1, 'Vand bræt på vindskeder'),
  (13, 'GABLE_CLADDING', 1, 'Beklædning af gavle 1 på 2'),
  (14, 'SHED_CLADDING', 1, 'Beklædning af skur 1 på 2'),
  (15, 'SHED_DOOR_NOGGING', 3, 'Til z på bagside af dør'),
  (16, 'ROOFING_LATHS', 3, 'Til montering på spær, 7 rækker lægter på hver skiftevis 1 hel & 1 halv lægte'),
  (17, 'ROOF_RIDGE_LATH', 3, 'Toplægte til montering af rygsten lægges i toplægte holder'),
  (18, 'VINDSKEDER', 1, 'Vindskeder på rejsning'),
  (19, 'STERN_BOARD', 1, 'Sternbrædder til siderne Carport del');

INSERT INTO roofing_component_definitions (id, roofing_type, definition) VALUES
  (1, 'TILED', 1),
  (2, 'TILED', 2),
  (3, 'TILED', 3),
  (4, 'TILED', 4),
  (5, 'TILED', 5),
  (6, 'TILED', 6),
  (7, 'TILED', 12),
  (8, 'TILED', 13),
  (9, 'TILED', 16),
  (10, 'TILED', 17),
  (11, 'TILED', 18),
  (12, 'TILED', 19);

INSERT INTO component_values (id, definition, material) VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 3),
  (4, 4, 4),
  (5, 5, 5),
  (6, 6, 6),
  (7, 7, 10),
  (8, 8, 11),
  (9, 9, 11),
  (10, 10, 12),
  (11, 11, 12),
  (12, 14, 6),
  (13, 15, 8),
  (14, 16, 8),
  (15, 17, 8),
  (16, 18, 25),
  (17, 19, 25);

INSERT INTO roofing_component_values (id, roofing, component) VALUES
  (1, 1, 1),
  (2, 1, 2),
  (3, 1, 3),
  (4, 1, 4),
  (5, 1, 5),
  (6, 1, 6),
  (7, 1, 16),
  (8, 1, 17);

INSERT INTO garage_models (id, name) VALUES
  (1, 'CAR01');

INSERT INTO garage_component_definitions (id, definition, model) VALUES
  (1, 7, 1),
  (2, 8, 1),
  (3, 9, 1),
  (4, 10, 1),
  (5, 11, 1),
  (6, 14, 1),
  (7, 15, 1);

INSERT INTO garage_component_values (id, component, definition) VALUES
  (1, 7, 1),
  (2, 8, 2),
  (3, 9, 3),
  (4, 10, 4),
  (5, 11, 5),
  (6, 12, 6),
  (7, 13, 7);

INSERT INTO `customers` (id, `name`, `address`, email, phone, PASSWORD, active, confirmed) VALUES
  (1, 'Thomas Rosenkrans Vestergaard', 'Møllevangen 23', 'tvestergaard@hotmail.com', '26508830',
   '$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm', b'1', b'1');
INSERT INTO `employees` (id, name, username, password, active) VALUES
  (1, 'Administrator', 'admin', '$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm', b'1');

INSERT INTO roles (employee, role) VALUES
  (1, 'HEAD_OF_CENTER'),
  (1, 'HEAD_OF_MATERIALS'),
  (1, 'SALESMAN');

SET SQL_SAFE_UPDATES = 1;