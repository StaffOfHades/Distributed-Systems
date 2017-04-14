-- MySQL dump 10.16  Distrib 10.1.22-MariaDB, for osx10.12 (x86_64)
--
-- Host: localhost    Database: play_webservices
-- ------------------------------------------------------
-- Server version	10.1.22-MariaDB

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
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `name` varchar(128) CHARACTER SET utf8 NOT NULL,
  `capital` varchar(128) CHARACTER SET utf8 NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`name`),
  KEY `name` (`name`(32))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES ('Alemania','Berlín',52.5077843,13.3517587),('Austria','Vienna',48.1998585,16.3700744),('Bélgica','Bruselas',50.8083158,4.3849209),('Dinamarca','Copenhagen',55.6864627,12.5918263),('España','Madrid',40.4160947,-3.6973632),('Finlandia','Helsinki',60.1685662,24.9351516),('Francia','Paris',48.8648275,2.292344),('Grecia','Atenas',37.9773269,23.7406908),('Hungría','Budapest',47.516701,19.0278825),('Irlanda','Dublin',53.3303151,-6.2387368),('Italia','Roma',41.9133032,12.5097257),('Países Bajos','Amsterdam',52.0899761,4.3059916),('Polonia','Warsaw',52.22499,20.99128),('Portugal','Lisboa',38.7375761,-9.2019543),('Reino Unido','Londres',51.513845,-0.1422097),('República Checa','Prague',50.0801273,14.4168633),('Rumanía','Bucharest',50.0801273,14.4168633),('Russia','Moscow',55.74326,37.589527),('Suecia','Stockolmo',59.3320685,18.0830599);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-13 21:50:15
