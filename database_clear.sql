SET SQL_SAFE_UPDATES = 0;

DELETE FROM bom_lines;
DELETE FROM bom;
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
DELETE FROM tokens;
DELETE FROM customers;
DELETE FROM roles;
DELETE FROM employees;
DELETE FROM floorings;
DELETE FROM claddings;

SET SQL_SAFE_UPDATES = 1;