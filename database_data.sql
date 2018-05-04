SET SQL_SAFE_UPDATES = 0;

DELETE FROM roofing_component_attribute_values;
DELETE FROM roofing_component_attribute_definitions;
DELETE FROM roofing_component_values;
DELETE FROM roofing_component_definitions;
DELETE FROM materials;
DELETE FROM roofings;

INSERT INTO materials (id, `number`, `description`, `price`, `unit`) VALUES
  (1, '960919471840', 'B&C Dobbelt sort', 600, 1),
  (2, '306606942576', 'B&C Rygsten sort', 4500, 1),
  (3, '342599675955', 'B&C Toplægteholder', 3900, 1),
  (4, '682229537074', 'B&C Rygstensbeslag', 500, 50),
  (5, '459740486968', 'B&C Tagstensbindere & Nakkekroge', 6, 50);

INSERT INTO roofings (id, name, description, active, type)
VALUES (1, 'Sorte teglsten',
        'Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.',
        b'1', 'TILED');

INSERT INTO roofing_component_definitions (id, `type`, identifier) VALUES
  (1, 'TILED', 'TILE'),
  (2, 'TILED', 'RIDGE_TILE'),
  (3, 'TILED', 'TOP_HOLDER'),
  (4, 'TILED', 'RIDGE_TILE_BRACKET'),
  (5, 'TILED', 'BINDERS_HOOKS');

INSERT INTO roofing_component_values (id, roofing, component, material) VALUES
  (1, 1, 1, 1),
  (2, 1, 2, 2),
  (3, 1, 3, 3),
  (4, 1, 4, 4),
  (5, 1, 5, 5);

INSERT INTO roofing_component_attribute_definitions (id, component, name, data_type) VALUES
  (1, 1, 'HEIGHT_MM', 'INT'),
  (2, 1, 'WIDTH_MM', 'INT'),
  (3, 1, 'LATH_DISTANCE_MM', 'INT'),
  (4, 2, 'LENGTH_MM', 'INT');

INSERT INTO roofing_component_attribute_values (attribute, `value`) VALUES
  (1, '420'),
  (2, '330'),
  (3, '320'),
  (4, '420');