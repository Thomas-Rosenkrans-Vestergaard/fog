CREATE DATABASE  IF NOT EXISTS `fog` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `fog`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fog
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `attribute_definitions`
--

LOCK TABLES `attribute_definitions` WRITE;
/*!40000 ALTER TABLE `attribute_definitions` DISABLE KEYS */;
INSERT INTO `attribute_definitions` VALUES (1,5,'INT','HEIGHT_MM'),(2,5,'INT','WIDTH_MM'),(3,5,'INT','LATH_DISTANCE_MM'),(4,6,'INT','LENGTH_MM'),(5,7,'INT','USE_DISTANCE_MM'),(7,1,'INT','THICKNESS_MM'),(8,2,'INT','WIDTH_MM'),(9,2,'INT','THICKNESS_MM'),(10,3,'INT','WIDTH_MM'),(11,3,'INT','THICKNESS_MM'),(12,4,'INT','THICKNESS_MM'),(13,1,'INT','WIDTH_MM'),(14,12,'INT','WIDTH_MM'),(15,12,'INT','THICKNESS_MM');
/*!40000 ALTER TABLE `attribute_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attribute_values`
--

LOCK TABLES `attribute_values` WRITE;
/*!40000 ALTER TABLE `attribute_values` DISABLE KEYS */;
INSERT INTO `attribute_values` VALUES (598,1,1,'420'),(599,2,1,'330'),(600,3,1,'320'),(601,4,2,'420'),(602,5,3,'900'),(603,6,6,'100'),(604,7,6,'19'),(605,6,7,'50'),(606,7,7,'25'),(607,8,8,'73'),(608,9,8,'38'),(609,9,10,'97'),(610,8,11,'195'),(611,9,11,'45'),(612,13,6,'100'),(613,13,7,'50'),(614,14,12,'95'),(615,15,12,'45'),(616,1,26,'420'),(617,2,26,'330'),(618,3,26,'320'),(619,4,27,'420');
/*!40000 ALTER TABLE `attribute_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bom`
--

LOCK TABLES `bom` WRITE;
/*!40000 ALTER TABLE `bom` DISABLE KEYS */;
/*!40000 ALTER TABLE `bom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bom_materials`
--

LOCK TABLES `bom_materials` WRITE;
/*!40000 ALTER TABLE `bom_materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `bom_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Brædde'),(2,'Spærtræ'),(3,'Taglægte'),(4,'Stolpe'),(5,'Tegl'),(6,'Rygningssten'),(7,'Toplægteholder'),(8,'Rygningsstensbeslag'),(9,'Tagstens bindere & nakkekroge'),(10,'Andre'),(11,'Skrue'),(12,'Reglar');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `claddings`
--

LOCK TABLES `claddings` WRITE;
/*!40000 ALTER TABLE `claddings` DISABLE KEYS */;
INSERT INTO `claddings` VALUES (1,'Beklædning 1','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','');
/*!40000 ALTER TABLE `claddings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `component_definitions`
--

LOCK TABLES `component_definitions` WRITE;
/*!40000 ALTER TABLE `component_definitions` DISABLE KEYS */;
INSERT INTO `component_definitions` VALUES (1,'ROOF_TILE',5,'Monteres på taglægter'),(2,'ROOF_RIDGE_TILE',6,'Monteres på toplægte med medfølgende beslag se tagstens vejledning'),(3,'ROOF_RIDGE_LATH_HOLDER',7,'Monteres på toppen af spæret (til toplægte)'),(4,'ROOF_RIDGE_TILE_BRACKET',8,'Til	montering af rygsten'),(5,'ROOF_TILE_BINDER_AND_HOOKS',9,'Til	montering af tagsten, alle ydersten + hver anden fastgøres'),(6,'ROOF_GABLE_CLADDING',1,'Beklædning af gavle 1 på 2'),(7,'POST',4,'Stolper nedgraves 90 cm. i jord + skråstiver'),(8,'STRAPS_GARAGE',2,'Remme i sider, sadles ned i stolper Carport del'),(9,'STRAPS_SHED',2,'Remme i sider, sadles ned i stolper Skur del'),(10,'SHED_SIDE_CLADDING_NOGGING',2,'Løsholter i siderne af skur'),(11,'SHED_GABLE_CLADDING_NOGGING',2,'Løsholter i gavle af skur'),(12,'WATER_BOARD',1,'Vandbræt på vindskeder'),(14,'SHED_CLADDING',1,'Beklædning af skur 1 på 2'),(15,'SHED_DOOR_NOGGING',3,'Til z på bagside af dør'),(16,'ROOFING_LATHS',3,'Til montering på spær, 7 rækker lægter på hver skiftevis 1 hel & 1 halv lægte'),(17,'ROOF_RIDGE_LATH',3,'Toplægte til montering af rygsten lægges i toplægte holder'),(18,'VINDSKEDER',1,'Vindskeder på rejsning'),(19,'STERN_BOARD',1,'Sternbrædder til siderne Carport del');
/*!40000 ALTER TABLE `component_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `component_values`
--

LOCK TABLES `component_values` WRITE;
/*!40000 ALTER TABLE `component_values` DISABLE KEYS */;
INSERT INTO `component_values` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5),(6,6,6),(7,7,10),(8,8,11),(9,9,11),(10,10,12),(11,11,12),(12,14,6),(13,15,8),(14,12,6),(15,16,8),(16,17,8),(17,18,25),(18,19,25),(30,1,26),(31,2,27),(32,3,3),(33,4,4),(34,5,5),(35,6,6),(36,12,6),(37,16,8),(38,17,8),(39,18,25),(40,19,25);
/*!40000 ALTER TABLE `component_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Thomas Rosenkrans Vestergaard','Møllevangen 23','tvestergaard@hotmail.com','26508830','$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm','','','2018-05-16 14:02:14');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Administrator','admin','$2a$10$9bIJX1ISI1flRxxkl.yb.u9u7M2ujahmwGwOMb8eD/sP3IvKJvtkm','','2018-05-16 14:02:15');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `floorings`
--

LOCK TABLES `floorings` WRITE;
/*!40000 ALTER TABLE `floorings` DISABLE KEYS */;
INSERT INTO `floorings` VALUES (1,'Betongulv','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','');
/*!40000 ALTER TABLE `floorings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `garage_component_definitions`
--

LOCK TABLES `garage_component_definitions` WRITE;
/*!40000 ALTER TABLE `garage_component_definitions` DISABLE KEYS */;
INSERT INTO `garage_component_definitions` VALUES (1,7,1),(2,8,1),(3,9,1),(4,10,1),(5,11,1),(6,14,1),(7,15,1);
/*!40000 ALTER TABLE `garage_component_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `garage_component_values`
--

LOCK TABLES `garage_component_values` WRITE;
/*!40000 ALTER TABLE `garage_component_values` DISABLE KEYS */;
INSERT INTO `garage_component_values` VALUES (1,7,1),(2,8,2),(3,9,3),(4,10,4),(5,11,5),(6,12,6),(7,13,7);
/*!40000 ALTER TABLE `garage_component_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `garage_models`
--

LOCK TABLES `garage_models` WRITE;
/*!40000 ALTER TABLE `garage_models` DISABLE KEYS */;
INSERT INTO `garage_models` VALUES (1,'Carport');
/*!40000 ALTER TABLE `garage_models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
INSERT INTO `materials` VALUES (1,'960919471840','B&C Dobbelt sort',1000,1,5),(2,'306606942576','B&C Rygsten sort',4500,1,6),(3,'342599675955','B&C Toplægteholder',3900,1,7),(4,'682229537074','B&C Rygstensbeslag',25000,50,8),(5,'459740486968','B&C Tagstensbindere & Nakkekroge',12500,50,9),(6,'343615203226','19x100 mm. Trykimprægneret bræt',30000,6,1),(7,'093765091763','25x50 mm. Trykimprægneret bræt',30000,6,1),(8,'229786742441','38x73 mm. Taglægte T1',30000,6,3),(9,'788947415996','Fædigskåret (byg-selv spær)',100000,1,2),(10,'448582215123','97x97 mm. Trykimprægneret stolpe',4000,1,4),(11,'851468122894','45x195 spærtræ ubh.',40000,1,2),(12,'234368028753','45x95 Reglar ubh.',40000,1,12),(13,'514432595305','Universal 190 mm højre',4500,1,10),(14,'231983532544','Universal 190 mm venstre',4500,1,10),(15,'448646179776','Stalddørsgreb 50x75',8000,1,10),(16,'685076293556','T-hængsel 390 mm',12000,1,10),(17,'346844119739','Vinkelbeslag',500,1,10),(18,'763421115200','4,5x60 mm. skruer 200 stk.',35000,200,11),(19,'013735613112','5,0x40 mm. beslagskruer 250 stk.',16000,250,11),(20,'702351056728','5,0x100 mm. skruer 100 stk.',8500,100,11),(21,'322772016293','4,5x70 mm. Skruer 200 stk.',15000,200,11),(22,'347369061736','4,5x50 mm. Skruer 350 stk.',35000,350,11),(23,'419643103011','Bræddebolt 10 x 120 mm.',1500,1,10),(24,'950209945881','Firkantskiver 40x40x11 mm.',1500,1,10),(25,'234789560123','25x150 mm. trykimp. Bræt',2000,1,1),(26,'768456342309','B&C Dobbelt rød',1000,1,5),(27,'094563498753','B&C Rygsten rød',4500,1,6);
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (15,1,420,780,300,1,25,1,'',1,'2018-05-16 14:02:17');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (107,1,'HEAD_OF_CENTER'),(108,1,'HEAD_OF_MATERIALS'),(109,1,'SALESMAN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roofing_component_definitions`
--

LOCK TABLES `roofing_component_definitions` WRITE;
/*!40000 ALTER TABLE `roofing_component_definitions` DISABLE KEYS */;
INSERT INTO `roofing_component_definitions` VALUES (1,'TILED',1),(2,'TILED',2),(3,'TILED',3),(4,'TILED',4),(5,'TILED',5),(6,'TILED',6),(7,'TILED',12),(9,'TILED',16),(10,'TILED',17),(11,'TILED',18),(12,'TILED',19);
/*!40000 ALTER TABLE `roofing_component_definitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roofing_component_values`
--

LOCK TABLES `roofing_component_values` WRITE;
/*!40000 ALTER TABLE `roofing_component_values` DISABLE KEYS */;
INSERT INTO `roofing_component_values` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,14),(8,1,15),(9,1,16),(10,1,17),(11,1,18),(21,3,30),(22,3,31),(23,3,32),(24,3,33),(25,3,34),(26,3,35),(27,3,36),(28,3,37),(29,3,38),(30,3,39),(31,3,40);
/*!40000 ALTER TABLE `roofing_component_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roofings`
--

LOCK TABLES `roofings` WRITE;
/*!40000 ALTER TABLE `roofings` DISABLE KEYS */;
INSERT INTO `roofings` VALUES (1,'Sorte teglsten','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','','TILED'),(2,'Røde teglsten','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','','TILED');
/*!40000 ALTER TABLE `roofings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sheds`
--

LOCK TABLES `sheds` WRITE;
/*!40000 ALTER TABLE `sheds` DISABLE KEYS */;
INSERT INTO `sheds` VALUES (1,200,1,1);
/*!40000 ALTER TABLE `sheds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-16 16:04:55
