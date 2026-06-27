-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: inventorymanagmentsystem
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status_sid` int NOT NULL,
  `role_rid` int NOT NULL,
  PRIMARY KEY (`aid`),
  KEY `fk_admin_status_idx` (`status_sid`),
  KEY `fk_admin_role1_idx` (`role_rid`),
  CONSTRAINT `fk_admin_role1` FOREIGN KEY (`role_rid`) REFERENCES `role` (`rid`),
  CONSTRAINT `fk_admin_status` FOREIGN KEY (`status_sid`) REFERENCES `status` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (213,'mihimitha','mihimitha@gmail.com','0000000',1,2),(214,'sandil','sandil@gmail.com','1111111',2,1),(215,'jayawardana','jayawardana@gmail.com','1212121',1,2),(216,'Geeganage','geeganage@gmail.com','1234123',1,1),(217,'Thenujaya','thenu@gmail.com','1234567',1,1),(218,'Amal','amal@gmail.com','9876543',2,2),(220,'vithum','vithum@gmail.com','1234567',1,2),(221,'shane','shane@gmail.com','0987654',2,1),(222,'test User','test@gmail.com','1234567',1,1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'cocacola'),(2,'munche'),(3,'Maliban'),(4,'elephent house'),(5,'Risbary');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catagory`
--

DROP TABLE IF EXISTS `catagory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catagory` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catagory`
--

LOCK TABLES `catagory` WRITE;
/*!40000 ALTER TABLE `catagory` DISABLE KEYS */;
INSERT INTO `catagory` VALUES (1,'biscuts'),(2,'drinks'),(3,'Ice-cream'),(4,'chocalate');
/*!40000 ALTER TABLE `catagory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `discription` varchar(100) NOT NULL,
  `product_code` varchar(50) NOT NULL,
  `category_cid` int NOT NULL,
  `brand_bid` int NOT NULL,
  `status_sid` int NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `fk_product_status1_idx` (`status_sid`),
  KEY `fk_product_category1_idx` (`category_cid`),
  KEY `fk_product_brand1_idx` (`brand_bid`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_bid`) REFERENCES `brand` (`bid`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_cid`) REFERENCES `catagory` (`cid`),
  CONSTRAINT `fk_product_status1` FOREIGN KEY (`status_sid`) REFERENCES `status` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (10,'ole','rata biscute','IMS_1751214737395',1,3,1),(16,'EGB','EGB nathuwa kamma epa','IMS_1752330445773',2,4,1),(37,'Strabury Ice cream','ice cream','IMS_1782044469029',3,4,1),(38,'chunky choc','chocolate chnuky','IMS_1782129036723',4,5,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `product_image_id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(500) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`product_image_id`),
  KEY `fk_product_image_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_image_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,'C:\\Users\\MB\\Documents\\NetBeansProjects\\Inventiry Managment System\\Pimg\\1751214735790_download.jpeg',10),(29,'C:\\Users\\MB\\Documents\\NetBeansProjects\\Inventiry Managment System\\Pimg\\1782129035192_1752338252470_c.jpeg',38),(31,'C:\\Users\\MB\\Documents\\NetBeansProjects\\Inventiry Managment System\\Pimg\\1782130132638_1752156589909_strawberry.png',37),(32,'C:\\Users\\MB\\Documents\\NetBeansProjects\\Inventiry Managment System\\Pimg\\1782130158174_1752330444805_e.jpg',16);
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_item`
--

DROP TABLE IF EXISTS `purchase_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `price` double NOT NULL,
  `purchase_order_id` int NOT NULL,
  `stock_stid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_item_purchase_order1_idx` (`purchase_order_id`),
  KEY `fk_purchase_item_stock1_idx` (`stock_stid`),
  CONSTRAINT `fk_purchase_item_purchase_order1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`),
  CONSTRAINT `fk_purchase_item_stock1` FOREIGN KEY (`stock_stid`) REFERENCES `stock` (`stid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_item`
--

LOCK TABLES `purchase_item` WRITE;
/*!40000 ALTER TABLE `purchase_item` DISABLE KEYS */;
INSERT INTO `purchase_item` VALUES (15,1,150,19,29);
/*!40000 ALTER TABLE `purchase_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_price` double NOT NULL,
  `purchased_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
INSERT INTO `purchase_order` VALUES (19,150,'2026-06-21 17:44:47'),(20,100,'2026-06-22 15:00:32'),(21,100,'2026-06-22 16:37:15');
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'stockmanager');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Active'),(2,'Inactive');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `stid` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `price` double NOT NULL,
  `update_date` datetime NOT NULL,
  `product_pid` int NOT NULL,
  `suppliers_supid` int NOT NULL,
  PRIMARY KEY (`stid`),
  KEY `fk_Stock_product1_idx` (`product_pid`),
  KEY `fk_Stock_suppliers1_idx` (`suppliers_supid`),
  CONSTRAINT `fk_Stock_product1` FOREIGN KEY (`product_pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `fk_Stock_suppliers1` FOREIGN KEY (`suppliers_supid`) REFERENCES `suppliers` (`supid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (29,99,150,'2026-06-22 17:39:19',16,1),(31,50,500,'2026-06-22 13:36:06',10,6),(32,25,750,'2026-06-22 17:38:55',37,2),(33,25,500,'2026-06-22 17:36:41',38,1);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `status_sid` int NOT NULL,
  PRIMARY KEY (`supid`),
  KEY `fk_suppliers_status1_idx` (`status_sid`),
  CONSTRAINT `fk_suppliers_status1` FOREIGN KEY (`status_sid`) REFERENCES `status` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'ranil','ranil@gmail.com','+94 712443076',1),(2,'mahinda','mahinda @gmail.com','+94 718652468',1),(3,'test','test@gmail.com','+9475844613',1),(4,'methula','methula@gmail.com','+94718556324',2),(5,'lithum','lithum@gmail.com','+94785669234',2),(6,'deepthi','deepthi@gmail.com','+94355684679',1),(7,'manoshika','manoshika@gmail.com','+9425869543',2),(8,'Nimal','nimal@gmail.com','+94753698546',1),(9,'charith','charit@gmail.com','0712586549',1),(10,'test supplier','testSupplier@gmail.com','0728546547',1);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-22 17:42:19
