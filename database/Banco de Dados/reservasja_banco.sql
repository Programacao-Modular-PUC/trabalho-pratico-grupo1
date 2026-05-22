CREATE DATABASE  IF NOT EXISTS `projeto1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `projeto1`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: projeto1
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aluguel`
--

DROP TABLE IF EXISTS `aluguel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aluguel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cliente_id` bigint NOT NULL,
  `quarto_id` bigint NOT NULL,
  `data_prevista_entrada` datetime NOT NULL,
  `data_prevista_saida` datetime NOT NULL,
  `data_real_entrada` datetime DEFAULT NULL,
  `data_real_saida` datetime DEFAULT NULL,
  `quantidade_dias` int DEFAULT NULL,
  `valor_final` decimal(10,2) DEFAULT NULL,
  `status` enum('RESERVADO','EM_ANDAMENTO','CONCLUIDO','CANCELADO') NOT NULL,
  `numero_hospedes` int NOT NULL,
  `berco_solicitado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `cliente_id` (`cliente_id`),
  KEY `quarto_id` (`quarto_id`),
  CONSTRAINT `aluguel_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `aluguel_ibfk_2` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aluguel`
--

LOCK TABLES `aluguel` WRITE;
/*!40000 ALTER TABLE `aluguel` DISABLE KEYS */;
/*!40000 ALTER TABLE `aluguel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anfitriao`
--

DROP TABLE IF EXISTS `anfitriao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anfitriao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(30) DEFAULT NULL,
  `endereco_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `usuario_id` (`usuario_id`),
  KEY `endereco_id` (`endereco_id`),
  CONSTRAINT `anfitriao_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `anfitriao_ibfk_2` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anfitriao`
--

LOCK TABLES `anfitriao` WRITE;
/*!40000 ALTER TABLE `anfitriao` DISABLE KEYS */;
/*!40000 ALTER TABLE `anfitriao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(30) DEFAULT NULL,
  `endereco_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `usuario_id` (`usuario_id`),
  KEY `endereco_id` (`endereco_id`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `cliente_ibfk_2` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,1,'Maria Silva','12345678900','cliente@email.com','31999999999',1);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracao_camas`
--

DROP TABLE IF EXISTS `configuracao_camas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracao_camas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `camas_solteiro` int DEFAULT '0',
  `camas_casal` int DEFAULT '0',
  `camas_queen` int DEFAULT '0',
  `camas_king` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracao_camas`
--

LOCK TABLES `configuracao_camas` WRITE;
/*!40000 ALTER TABLE `configuracao_camas` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuracao_camas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rua` varchar(100) NOT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `bairro` varchar(80) DEFAULT NULL,
  `cep` varchar(20) DEFAULT NULL,
  `cidade` varchar(80) DEFAULT NULL,
  `estado` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,'Rua A','100','Centro','30000-000','Belo Horizonte','MG');
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aluguel_id` bigint NOT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `status` enum('PENDENTE','PAGO','CANCELADO','ESTORNADO') NOT NULL,
  `valor_pagamento` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `aluguel_id` (`aluguel_id`),
  CONSTRAINT `pagamento_ibfk_1` FOREIGN KEY (`aluguel_id`) REFERENCES `aluguel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarto`
--

DROP TABLE IF EXISTS `quarto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `residencia_id` bigint NOT NULL,
  `numero` int NOT NULL,
  `valor_base` decimal(10,2) NOT NULL,
  `capacidade_maxima` int NOT NULL,
  `possui_ar_condicionado` tinyint(1) DEFAULT '0',
  `possui_hidromassagem` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `residencia_id` (`residencia_id`),
  CONSTRAINT `quarto_ibfk_1` FOREIGN KEY (`residencia_id`) REFERENCES `residencia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarto`
--

LOCK TABLES `quarto` WRITE;
/*!40000 ALTER TABLE `quarto` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarto_casal`
--

DROP TABLE IF EXISTS `quarto_casal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarto_casal` (
  `quarto_id` bigint NOT NULL,
  `tipo_cama_casal` enum('COMUM','QUEEN','KING') NOT NULL,
  `berco_instalado` tinyint(1) DEFAULT '0',
  `valor_adicional_berco` decimal(10,2) DEFAULT '0.00',
  `valor_adicional_conforto` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`quarto_id`),
  CONSTRAINT `quarto_casal_ibfk_1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarto_casal`
--

LOCK TABLES `quarto_casal` WRITE;
/*!40000 ALTER TABLE `quarto_casal` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarto_casal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarto_familia`
--

DROP TABLE IF EXISTS `quarto_familia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarto_familia` (
  `quarto_id` bigint NOT NULL,
  `configuracao_camas_id` bigint NOT NULL,
  `quantidade_ambientes` int DEFAULT NULL,
  `percentual_adicional_por_hospede` decimal(5,2) DEFAULT NULL,
  `limite_desconto_grupo` int DEFAULT NULL,
  `percentual_desconto_grupo` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`quarto_id`),
  KEY `configuracao_camas_id` (`configuracao_camas_id`),
  CONSTRAINT `quarto_familia_ibfk_1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`),
  CONSTRAINT `quarto_familia_ibfk_2` FOREIGN KEY (`configuracao_camas_id`) REFERENCES `configuracao_camas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarto_familia`
--

LOCK TABLES `quarto_familia` WRITE;
/*!40000 ALTER TABLE `quarto_familia` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarto_familia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarto_individual`
--

DROP TABLE IF EXISTS `quarto_individual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarto_individual` (
  `quarto_id` bigint NOT NULL,
  `numero_camas_solteiro` int NOT NULL,
  `valor_adicional_por_cama` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`quarto_id`),
  CONSTRAINT `quarto_individual_ibfk_1` FOREIGN KEY (`quarto_id`) REFERENCES `quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarto_individual`
--

LOCK TABLES `quarto_individual` WRITE;
/*!40000 ALTER TABLE `quarto_individual` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarto_individual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `residencia`
--

DROP TABLE IF EXISTS `residencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `residencia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `anfitriao_id` bigint NOT NULL,
  `endereco_id` bigint NOT NULL,
  `telefone` varchar(30) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `anfitriao_id` (`anfitriao_id`),
  KEY `endereco_id` (`endereco_id`),
  CONSTRAINT `residencia_ibfk_1` FOREIGN KEY (`anfitriao_id`) REFERENCES `anfitriao` (`id`),
  CONSTRAINT `residencia_ibfk_2` FOREIGN KEY (`endereco_id`) REFERENCES `endereco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `residencia`
--

LOCK TABLES `residencia` WRITE;
/*!40000 ALTER TABLE `residencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `residencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `papel` enum('CLIENTE','ANFITRIAO') NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'CLIENTE','cliente@email.com','123456');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-14 23:11:54
