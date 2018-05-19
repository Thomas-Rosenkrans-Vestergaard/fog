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
INSERT INTO `attribute_values` VALUES (598,1,1,'420'),(599,2,1,'330'),(600,3,1,'320'),(601,4,2,'420'),(602,5,3,'900'),(603,6,6,'100'),(604,7,6,'19'),(605,6,7,'50'),(606,7,7,'25'),(607,8,8,'73'),(608,9,8,'38'),(609,9,10,'97'),(610,8,11,'195'),(611,9,11,'45'),(612,13,6,'100'),(613,13,7,'50'),(614,14,12,'95'),(615,15,12,'45'),(616,1,26,'420'),(617,2,26,'330'),(618,3,26,'320'),(619,4,27,'420'),(628,4,34,'420'),(629,2,35,'320'),(630,1,35,'330'),(631,3,35,'420'),(632,4,36,'420');
/*!40000 ALTER TABLE `attribute_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bom`
--

LOCK TABLES `bom` WRITE;
/*!40000 ALTER TABLE `bom` DISABLE KEYS */;
INSERT INTO `bom` VALUES (52);
/*!40000 ALTER TABLE `bom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bom_drawings`
--

LOCK TABLES `bom_drawings` WRITE;
/*!40000 ALTER TABLE `bom_drawings` DISABLE KEYS */;
INSERT INTO `bom_drawings` VALUES (1,52,'Skelet ovenfra','\n<svg contentScriptType=\"text/ecmascript\"\n     xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\"\n     contentStyleType=\"text/css\" viewBox=\"0 0 5400 4900\"\n     preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\"\n     version=\"1.0\">\n    <rect x=\"1000\" width=\"3400\" y=\"1250\" height=\"45\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3602\" height=\"45\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <line y2=\"4400\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"4400\"\n          y1=\"4400\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"4350\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"4350\"/>\n    <text x=\"2700\" y=\"4300\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        340 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1500\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1500\" x2=\"1500\"\n          y1=\"450\"/>\n    <text x=\"1250\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"4400\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"3900\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"450\"/>\n    <text x=\"4150\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"500\" x2=\"500\"\n          y1=\"1250\"/>\n    <line y2=\"1250\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"1250\"/>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"3650\"/>\n    <text x=\"500\" y=\"2450\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        240 cm\n    </text>\n    <rect x=\"3803\" width=\"97\" y=\"1250\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"3803\" width=\"97\" y=\"3553\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" y=\"1250\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" y=\"3553\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"3803\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"1597\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3803\" x2=\"3803\"\n          y1=\"450\"/>\n    <text x=\"2700\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        220 cm\n    </text>\n</svg>'),(2,52,'Skelet fra siden','\n<svg contentScriptType=\"text/ecmascript\"\n     xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\"\n     contentStyleType=\"text/css\" viewBox=\"0 0 5400 3995\"\n     preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\"\n     version=\"1.0\">\n    <rect x=\"1000\" width=\"3400\" y=\"1000\" height=\"195\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <line y2=\"4400\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"4400\"\n          y1=\"4400\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"4350\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"4350\"/>\n    <text x=\"2700\" y=\"4300\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        340 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1500\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1500\" x2=\"1500\"\n          y1=\"450\"/>\n    <text x=\"1250\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"4400\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"3900\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"450\"/>\n    <text x=\"4150\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <rect x=\"3803\" width=\"97\" y=\"1000\" height=\"1995\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" y=\"1000\" height=\"1995\"\n          style=\"stroke:black;fill:black\"/>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"3803\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"1597\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3803\" x2=\"3803\"\n          y1=\"450\"/>\n    <text x=\"2700\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        220 cm\n    </text>\n    <line y2=\"2995\" style=\"stroke-width:5;stroke:black\" x1=\"500\" x2=\"500\"\n          y1=\"1195\"/>\n    <line y2=\"1195\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"1195\"/>\n    <line y2=\"2995\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"2995\"/>\n    <text x=\"500\" y=\"2095\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        180 cm\n    </text>\n</svg>'),(3,52,'Tag skelet','\n<svg contentScriptType=\"text/ecmascript\"\n     xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\"\n     contentStyleType=\"text/css\" viewBox=\"0 0 5400 4900\"\n     preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\"\n     version=\"1.0\">\n    <rect x=\"1000\" width=\"3400\" height=\"45\" y=\"1250\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"45\" y=\"3602\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <line y2=\"4400\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"4400\"\n          y1=\"4400\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"4350\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"4350\"/>\n    <text x=\"2700\" y=\"4300\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        340 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1500\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1500\" x2=\"1500\"\n          y1=\"450\"/>\n    <text x=\"1250\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"4400\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"3900\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"450\"/>\n    <text x=\"4150\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"500\" x2=\"500\"\n          y1=\"1250\"/>\n    <line y2=\"1250\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"1250\"/>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"3650\"/>\n    <text x=\"500\" y=\"2450\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        240 cm\n    </text>\n    <rect x=\"3803\" width=\"97\" height=\"97\" y=\"1250\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"3803\" width=\"97\" height=\"97\" y=\"3553\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" height=\"97\" y=\"1250\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" height=\"97\" y=\"3553\"\n          style=\"stroke:black;fill:black\"/>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"3803\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"1597\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3803\" x2=\"3803\"\n          y1=\"450\"/>\n    <text x=\"2700\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        220 cm\n    </text>\n    <rect x=\"2178\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3356\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"2414\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"2094\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"1774\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"1454\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"1134\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"2734\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3054\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3374\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3694\" height=\"73\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"1000\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3900\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1000\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4355\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"45\" y=\"2450\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"4355\" width=\"45\" y=\"2450\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n</svg>'),(4,52,'Tag tegl','\n<svg contentScriptType=\"text/ecmascript\"\n     xmlns:xlink=\"http://www.w3.org/1999/xlink\" zoomAndPan=\"magnify\"\n     contentStyleType=\"text/css\" viewBox=\"0 0 5400 4900\"\n     preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\"\n     version=\"1.0\">\n    <rect x=\"1000\" width=\"3400\" y=\"1250\" height=\"45\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" y=\"3602\" height=\"45\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <line y2=\"4400\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"4400\"\n          y1=\"4400\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"4350\"/>\n    <line y2=\"4450\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"4350\"/>\n    <text x=\"2700\" y=\"4300\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        340 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1500\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1000\" x2=\"1000\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1500\" x2=\"1500\"\n          y1=\"450\"/>\n    <text x=\"1250\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"4400\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3900\" x2=\"3900\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"4400\" x2=\"4400\"\n          y1=\"450\"/>\n    <text x=\"4150\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        50 cm\n    </text>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"500\" x2=\"500\"\n          y1=\"1250\"/>\n    <line y2=\"1250\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"1250\"/>\n    <line y2=\"3650\" style=\"stroke-width:5;stroke:black\" x1=\"450\" x2=\"550\"\n          y1=\"3650\"/>\n    <text x=\"500\" y=\"2450\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        240 cm\n    </text>\n    <rect x=\"3803\" width=\"97\" y=\"1250\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"3803\" width=\"97\" y=\"3553\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" y=\"1250\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1500\" width=\"97\" y=\"3553\" height=\"97\"\n          style=\"stroke:black;fill:black\"/>\n    <line y2=\"500\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"3803\"\n          y1=\"500\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"1597\" x2=\"1597\"\n          y1=\"450\"/>\n    <line y2=\"550\" style=\"stroke-width:5;stroke:black\" x1=\"3803\" x2=\"3803\"\n          y1=\"450\"/>\n    <text x=\"2700\" y=\"400\"\n          style=\"font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;\"\n          text-anchor=\"middle\">\n        220 cm\n    </text>\n    <rect x=\"2178\" width=\"45\" height=\"2900\" y=\"1000\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3356\" width=\"45\" height=\"2900\" y=\"1000\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"2414\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"2094\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"1774\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"1454\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"1134\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"2734\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"3054\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"3374\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"73\" y=\"3694\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"20\" y=\"1000\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1000\" width=\"3400\" height=\"20\" y=\"3900\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"1000\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1330\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1330\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1330\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1330\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1660\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1660\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1660\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1660\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1990\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1990\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1990\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1990\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2320\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2320\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2320\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2320\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2650\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2650\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2650\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2650\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2980\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2980\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2980\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2980\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3310\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3310\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3310\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3310\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3640\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3640\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3640\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3640\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3970\" width=\"330\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3970\" width=\"330\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3970\" width=\"330\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3970\" width=\"330\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1330\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1660\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1990\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2320\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2650\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2980\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3310\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3640\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3970\" width=\"330\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4300\" width=\"100\" y=\"2414\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4300\" width=\"100\" y=\"2734\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4300\" width=\"100\" y=\"3054\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4300\" width=\"100\" y=\"3374\" height=\"420\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4300\" width=\"100\" y=\"3694\" height=\"206\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1420\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1840\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2260\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"2680\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3100\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3520\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"3940\" width=\"420\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1360\" width=\"40\" y=\"2288\" height=\"252\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"4355\" width=\"45\" y=\"1000\" height=\"2900\"\n          style=\"fill:white;stroke-width:5;stroke:black\"/>\n    <rect x=\"1000\" width=\"45\" y=\"2450\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n    <rect x=\"4355\" width=\"45\" y=\"2450\" height=\"20\"\n          style=\"stroke:black;fill:black\"/>\n</svg>');
/*!40000 ALTER TABLE `bom_drawings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bom_lines`
--

LOCK TABLES `bom_lines` WRITE;
/*!40000 ALTER TABLE `bom_lines` DISABLE KEYS */;
INSERT INTO `bom_lines` VALUES (259,52,11,2,'Remme i sider, sadles ned i stolper Carport del'),(260,52,10,4,'Stolper nedgraves 90 cm. i jord + skråstiver'),(261,52,1,64,'Monteres på taglægter'),(262,52,2,6,'Monteres på toplægte med medfølgende beslag se tagstens vejledning'),(263,52,4,6,'Til	montering af rygsten'),(264,52,3,3,'Monteres på toppen af spæret (til toplægte)'),(265,52,5,48,'Til	montering af tagsten, alle ydersten + hver anden fastgøres'),(266,52,6,0,'Beklædning af gavle 1 på 2'),(267,52,25,2,'Vindskeder på rejsning'),(268,52,25,2,'Vindskeder på rejsning');
/*!40000 ALTER TABLE `bom_lines` ENABLE KEYS */;
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
INSERT INTO `component_values` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5),(6,6,6),(7,7,10),(8,8,11),(9,9,11),(10,10,12),(11,11,12),(12,14,6),(13,15,8),(14,12,6),(15,16,8),(16,17,8),(17,18,25),(18,19,25),(30,1,26),(31,2,34),(32,3,3),(33,4,4),(34,5,5),(35,6,6),(36,12,6),(37,16,8),(38,17,8),(39,18,25),(40,19,25),(41,1,35),(42,2,36),(43,3,3),(44,4,4),(45,5,5),(46,6,6),(47,12,6),(48,16,8),(49,17,8),(50,18,25),(51,19,25);
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
INSERT INTO `materials` VALUES (1,'960919471840','B&C Dobbelt sort',1000,1,5,''),(2,'306606942576','B&C Rygsten sort',4500,1,6,''),(3,'342599675955','B&C Toplægteholder',3900,1,7,''),(4,'682229537074','B&C Rygstensbeslag',25000,50,8,''),(5,'459740486968','B&C Tagstensbindere & Nakkekroge',12500,50,9,''),(6,'343615203226','19x100 mm. Trykimprægneret bræt',30000,6,1,''),(7,'093765091763','25x50 mm. Trykimprægneret bræt',30000,6,1,''),(8,'229786742441','38x73 mm. Taglægte T1',120000,6,3,''),(9,'788947415996','Fædigskåret (byg-selv spær)',100000,1,2,''),(10,'448582215123','97x97 mm. Trykimprægneret stolpe',4000,1,4,''),(11,'851468122894','45x195 spærtræ ubh.',40000,1,2,''),(12,'234368028753','45x95 Reglar ubh.',40000,1,12,''),(13,'514432595305','Universal 190 mm højre',4500,1,10,''),(14,'231983532544','Universal 190 mm venstre',4500,1,10,''),(15,'448646179776','Stalddørsgreb 50x75',8000,1,10,''),(16,'685076293556','T-hængsel 390 mm',12000,1,10,''),(17,'346844119739','Vinkelbeslag',500,1,10,''),(18,'763421115200','4,5x60 mm. skruer 200 stk.',35000,200,11,''),(19,'013735613112','5,0x40 mm. beslagskruer 250 stk.',16000,250,11,''),(20,'702351056728','5,0x100 mm. skruer 100 stk.',8500,100,11,''),(21,'322772016293','4,5x70 mm. Skruer 200 stk.',15000,200,11,''),(22,'347369061736','4,5x50 mm. Skruer 350 stk.',35000,350,11,''),(23,'419643103011','Bræddebolt 10 x 120 mm.',1500,1,10,''),(24,'950209945881','Firkantskiver 40x40x11 mm.',1500,1,10,''),(25,'234789560123','25x150 mm. trykimp. Bræt',2000,1,1,''),(26,'768456342309','B&C Dobbelt rød',1000,1,5,''),(27,'094563498753','B&C Rygsten rød',4500,1,6,''),(34,'094563498753','B&C Rygsten rød _',4500,1,6,''),(35,'937582947467','B&C Dobbelt grå',3000,1,5,''),(36,'214435845234','B&C Rygsten grå',4500,1,6,'');
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (2,15,1,20000,'REJECTED','2018-05-16 23:08:46'),(3,15,1,2000000,'ACCEPTED','2018-05-16 23:12:18'),(4,16,1,1000000,'ACCEPTED','2018-05-17 07:57:45'),(5,17,1,10000,'ACCEPTED','2018-05-17 12:10:45'),(6,17,1,12000,'REJECTED','2018-05-17 12:11:32'),(7,18,1,10000000,'ACCEPTED','2018-05-17 14:41:52'),(8,19,1,1000000,'REJECTED','2018-05-18 07:13:32'),(9,19,1,500000,'REJECTED','2018-05-18 07:26:08'),(10,19,1,100000,'REJECTED','2018-05-18 07:28:05'),(11,19,1,1000000,'ACCEPTED','2018-05-18 07:29:06'),(12,20,1,10000,'ACCEPTED','2018-05-18 07:35:39'),(13,21,1,100000,'ACCEPTED','2018-05-19 12:44:23'),(14,22,1,1000000,'REJECTED','2018-05-19 12:54:14'),(15,22,1,1000,'ACCEPTED','2018-05-19 12:56:35');
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (15,1,420,780,300,1,25,1,'\0',1,'2018-05-16 14:02:17'),(16,1,420,780,300,2,25,0,'\0',3,'2018-05-17 07:45:20'),(17,1,240,240,180,1,25,0,'\0',4,'2018-05-17 12:08:04'),(18,1,240,240,180,1,25,0,'\0',NULL,'2018-05-17 14:17:32'),(19,1,240,240,180,1,25,0,'\0',NULL,'2018-05-17 15:02:53'),(20,1,240,240,180,1,25,0,'\0',5,'2018-05-18 07:34:58'),(21,1,240,240,180,1,25,0,'\0',NULL,'2018-05-19 12:42:47'),(22,1,240,240,180,1,25,0,'\0',NULL,'2018-05-19 12:50:54');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (28,15,52,'2018-05-19 13:00:40');
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
INSERT INTO `roofing_component_values` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,14),(8,1,15),(9,1,16),(10,1,17),(11,1,18),(21,2,30),(22,2,31),(23,2,32),(24,2,33),(25,2,34),(26,2,35),(27,2,36),(28,2,37),(29,2,38),(30,2,39),(31,2,40),(32,3,41),(33,3,42),(34,3,43),(35,3,44),(36,3,45),(37,3,46),(38,3,47),(39,3,48),(40,3,49),(41,3,50),(42,3,51);
/*!40000 ALTER TABLE `roofing_component_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roofings`
--

LOCK TABLES `roofings` WRITE;
/*!40000 ALTER TABLE `roofings` DISABLE KEYS */;
INSERT INTO `roofings` VALUES (1,'Sorte teglsten','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','','TILED'),(2,'Røde teglsten','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','','TILED'),(3,'Grå teglsten','Integer imperdiet lectus quis justo. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero.','','TILED');
/*!40000 ALTER TABLE `roofings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sheds`
--

LOCK TABLES `sheds` WRITE;
/*!40000 ALTER TABLE `sheds` DISABLE KEYS */;
INSERT INTO `sheds` VALUES (1,200,1,1),(3,200,1,1),(4,100,1,1),(5,100,1,1);
/*!40000 ALTER TABLE `sheds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (1,1,'$2a$10$txIrDL1wpCWYPLtRK8r9/Ofq5.SSVy12N.8mw7uqxN0vj3WUhFeJq','2018-05-16 23:12:19','OFFER_EMAIL'),(2,1,'$2a$10$rxnqFn.qABsJTF2ALPFEteOyRMwedwktCn8gfggnIBhxFVz58oE.a','2018-05-17 07:57:45','OFFER_EMAIL'),(3,1,'$2a$10$YQVzcKVHKOjFBvPkDF09A.Nawy8TJmCjlq8c0P8n1WahQH/jX4Yiy','2018-05-17 12:10:45','OFFER_EMAIL'),(4,1,'$2a$10$j7Z.N71Xi/vE6NcaEYfWRuUz3h9Pm54oP5J8mAqDdp8XgsYKzxmoO','2018-05-17 12:11:32','OFFER_EMAIL'),(5,1,'$2a$10$RauJmL5MB7/9PcPshYKHDevaTMhRikw7J1ulF8QYKs8yO/sUnqRVW','2018-05-17 14:41:52','OFFER_EMAIL'),(6,1,'$2a$10$.cbst/dIpmVyJ2kfQmr7aerVgNJJJM.cVHuZgRnMFTgoqyfNgd4Wa','2018-05-18 07:13:32','OFFER_EMAIL'),(7,1,'$2a$10$ZruMd8yvd/XEg5mflG2Z3eT789k8dDXeZ3iJHbk.cW.Phw43enBW.','2018-05-18 07:26:08','OFFER_EMAIL'),(8,1,'$2a$10$AXdUKPhwKM05o2ZeRMZKK.dKTrtJ/Pc/X0S7hFSZuGN6rLn1QUf7C','2018-05-18 07:28:05','OFFER_EMAIL'),(9,1,'$2a$10$T5jQlKISfj/OstWkaU03s.pRUKd/RFcKrHjnvsOjcmnVuccjyUAfu','2018-05-18 07:29:06','OFFER_EMAIL'),(10,1,'$2a$10$9cwQaHcHNgW29fnYvT4MzOvy6rdLDGTTJode81ZqS9RiHerIvflsK','2018-05-18 07:35:40','OFFER_EMAIL'),(11,1,'$2a$10$8ExDWGOXg8kU10ENf/wb6uNr29s2RQfiItflzx5BdY7MreaxflThC','2018-05-19 12:44:23','OFFER_EMAIL'),(12,1,'$2a$10$pYr2lxLykOWOVabYqdoIM.yjy617wArq461ObukQDjhSbmQLWO2Ei','2018-05-19 12:54:14','OFFER_EMAIL'),(13,1,'$2a$10$MqB5QCE0mYFjhAyPQO.1Tu45BBtCCI9Zx2sVSK4tWFy36aG.Dv0K2','2018-05-19 12:56:35','OFFER_EMAIL');
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

-- Dump completed on 2018-05-19 15:25:07
