SET SQL_SAFE_UPDATES = 0;

DELETE FROM attribute_definitions;
DELETE FROM attribute_values;
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

INSERT INTO `categories` VALUES
  (1, 'PLANK'),
  (2, 'RAFTER'),
  (3, 'LATH'),
  (4, 'POLE'),
  (5, 'ROOF_TILE'),
  (6, 'ROOF_RIDGE_TILE'),
  (7, 'ROOF_RIDGE_LATH_HOLDER'),
  (8, 'ROOF_RIDGE_TILE_BRACKET'),
  (9, 'ROOF_TILE_BINDER_AND_HOOKS'),
  (10, 'OTHER');

INSERT INTO materials (id, `number`, `description`, `price`, `unit`, `category`) VALUES
  (1, '960919471840', 'B&C Dobbelt sort', 1000, 1, 5),
  (2, '306606942576', 'B&C Rygsten sort', 4500, 1, 6),
  (3, '342599675955', 'B&C Toplægteholder', 3900, 1, 7),
  (4, '682229537074', 'B&C Rygstensbeslag', 25000, 50, 8),
  (5, '459740486968', 'B&C Tagstensbindere & Nakkekroge', 12500, 50, 9),
  (6, '343615203226', '19x100 mm. Trykimprægneret bræt', '30000', 6, 1),
  (7, '229786742441', '38x73 mm. Taglægte T1', '30000', 6, 3),
  (8, '788947415996', 'Fædigskåret (byg-selv spær)', 100000, 1, 2),
  (9, '448582215123', '97x97 mm. Trykimprægneret stolpe', 4000, 1, 4),
  (10, '851468122894', '45x195 spærtræ ubh.', 40000, 1, 2);

INSERT INTO attribute_definitions (id, category, data_type, `name`) VALUES
  (6, 1, 'INT', 'LENGTH_MM'),
  (6, 1, 'INT', 'WIDTH_MM'),
  (6, 1, 'INT', 'THICKNESS_MM'),
  (1, 5, 'INT', 'HEIGHT_MM'),
  (2, 5, 'INT', 'WIDTH_MM'),
  (3, 5, 'INT', 'LATH_DISTANCE_MM'),
  (4, 6, 'INT', 'LENGTH_MM'),
  (5, 7, 'INT', 'USE_DISTANCE_MM');

INSERT INTO attribute_values (attribute, material, `value`) VALUES
  (1, 1, '420'),
  (2, 1, '330'),
  (3, 1, '320'),
  (4, 2, '420'),
  (5, 3, '900');

INSERT INTO roofings (id, name, description, active, type)
VALUES (1, 'Sorte teglsten',
        'Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.',
        b'1', 'TILED');

INSERT INTO component_definitions (id, identifier, category, notes) VALUES
  (1, 'ROOF_TILE', 5, 'Monteres på taglægter 6 rækker af 24 sten på hver side af taget'),
  (2, 'ROOF_RIDGE_TILE', 6, 'Monteres på toplægte med medfølgende beslag se tagstens vejledning'),
  (3, 'ROOF_RIDGE_LATH_HOLDER', 7, 'Monteres på toppen af spæret (til toplægte)'),
  (4, 'ROOF_RIDGE_TILE_BRACKET', 8, 'Til	montering af rygsten'),
  (5, 'ROOF_TILE_BINDER_AND_HOOKS', 9, 'Til	montering af tagsten, alle ydersten + hver anden fastgøres');

INSERT INTO roofing_component_definitions (id, roofing_type, definition) VALUES
  (1, 'TILED', 1),
  (2, 'TILED', 2),
  (3, 'TILED', 3),
  (4, 'TILED', 4),
  (5, 'TILED', 5);

INSERT INTO component_values (id, definition, material) VALUES
  (1, 1, 1),
  (2, 2, 2),
  (3, 3, 3),
  (4, 4, 4),
  (5, 5, 5);

INSERT INTO roofing_component_values (id, roofing, component) VALUES
  (1, 1, 1),
  (2, 1, 2),
  (3, 1, 3),
  (4, 1, 4),
  (5, 1, 5);

INSERT INTO `customers` (id, `name`, `address`, email, phone, password, active, confirmed) VALUES
  (1, 'Thomas Rosenkrans Vestergaard', 'Møllevangen 23', 'tvestergaard@hotmail.com', '26508830',
   '$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm', b'1', b'1');
INSERT INTO `employees` (id, name, username, password, active) VALUES
  (1, 'Administrator', 'admin', '$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm', b'1');

INSERT INTO roles (employee, role) VALUES
  (1, 'HEAD_OF_CENTER'),
  (1, 'HEAD_OF_MATERIALS'),
  (1, 'SALESMAN');

SET SQL_SAFE_UPDATES = 1;