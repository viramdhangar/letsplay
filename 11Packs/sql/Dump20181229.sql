CREATE DATABASE  IF NOT EXISTS `ifl` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `ifl`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ifl
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('javainuse','ROLE_ADMIN'),('javainuse','ROLE_USER'),('javainuse','ROLE_USER'),('employee','ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batting`
--

DROP TABLE IF EXISTS `batting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `batting` (
  `p_id` int(9) NOT NULL,
  `m_id` int(9) NOT NULL,
  `run` int(3) DEFAULT NULL,
  `ball` int(3) DEFAULT NULL,
  `four` int(3) DEFAULT NULL,
  `six` int(3) DEFAULT NULL,
  `sRate` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batting`
--

LOCK TABLES `batting` WRITE;
/*!40000 ALTER TABLE `batting` DISABLE KEYS */;
/*!40000 ALTER TABLE `batting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bowling`
--

DROP TABLE IF EXISTS `bowling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bowling` (
  `p_id` int(9) NOT NULL,
  `m_id` int(9) NOT NULL,
  `over` int(3) DEFAULT NULL,
  `maiden` int(3) DEFAULT NULL,
  `run` int(3) DEFAULT NULL,
  `wicket` int(3) DEFAULT NULL,
  `economy` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bowling`
--

LOCK TABLES `bowling` WRITE;
/*!40000 ALTER TABLE `bowling` DISABLE KEYS */;
/*!40000 ALTER TABLE `bowling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `empId` varchar(10) NOT NULL,
  `empName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fielding`
--

DROP TABLE IF EXISTS `fielding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fielding` (
  `p_id` int(9) NOT NULL,
  `m_id` int(9) NOT NULL,
  `catch` int(3) DEFAULT NULL,
  `bowled` int(3) DEFAULT NULL,
  `lbw` int(3) DEFAULT NULL,
  `stumped` int(3) DEFAULT NULL,
  `run_out` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fielding`
--

LOCK TABLES `fielding` WRITE;
/*!40000 ALTER TABLE `fielding` DISABLE KEYS */;
/*!40000 ALTER TABLE `fielding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `league`
--

DROP TABLE IF EXISTS `league`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `league` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `league` varchar(50) NOT NULL,
  `entry_fee` int(10) NOT NULL,
  `size` int(9) NOT NULL,
  `winning_amount` int(9) NOT NULL,
  `winners` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `league`
--

LOCK TABLES `league` WRITE;
/*!40000 ALTER TABLE `league` DISABLE KEYS */;
INSERT INTO `league` VALUES (100,'chanpaiansLeague',60,3,150,1),(101,'Normal League',40,3,100,1),(102,'Special',10,20,170,5);
/*!40000 ALTER TABLE `league` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `league_teams`
--

DROP TABLE IF EXISTS `league_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `league_teams` (
  `league_id` varchar(20) NOT NULL,
  `team_id` varchar(20) NOT NULL,
  `match_id` varchar(20) NOT NULL,
  `created_id` varchar(20) NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `league_teams`
--

LOCK TABLES `league_teams` WRITE;
/*!40000 ALTER TABLE `league_teams` DISABLE KEYS */;
/*!40000 ALTER TABLE `league_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_leagues`
--

DROP TABLE IF EXISTS `match_leagues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `match_leagues` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `league_id` varchar(20) NOT NULL,
  `match_id` varchar(20) NOT NULL,
  `size` int(10) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `joined_team` int(10) DEFAULT '0',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_leagues`
--

LOCK TABLES `match_leagues` WRITE;
/*!40000 ALTER TABLE `match_leagues` DISABLE KEYS */;
/*!40000 ALTER TABLE `match_leagues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match_squad`
--

DROP TABLE IF EXISTS `match_squad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `match_squad` (
  `match_id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  `credit` int(5) DEFAULT '8',
  `team` varchar(20) NOT NULL,
  UNIQUE KEY `UC_MATCH_SQUAD` (`match_id`,`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match_squad`
--

LOCK TABLES `match_squad` WRITE;
/*!40000 ALTER TABLE `match_squad` DISABLE KEYS */;
/*!40000 ALTER TABLE `match_squad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `matches` (
  `unique_id` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `datetime` datetime NOT NULL,
  `team1` varchar(50) NOT NULL,
  `team2` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `squad` varchar(20) DEFAULT NULL,
  `toss_winner_team` varchar(50) DEFAULT NULL,
  `winner_team` varchar(50) DEFAULT NULL,
  `matchStarted` varchar(20) DEFAULT NULL,
  `is_active` varchar(5) DEFAULT 'N',
  PRIMARY KEY (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `player` (
  `pid` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `imageURL` varchar(200) DEFAULT NULL,
  `country` varchar(50) NOT NULL,
  `playingRole` varchar(60) DEFAULT 'BAT',
  `major_teams` varchar(500) DEFAULT NULL,
  `current_age` varchar(50) DEFAULT NULL,
  `born` varchar(100) DEFAULT NULL,
  `battingStyle` varchar(50) DEFAULT NULL,
  `bowlingStyle` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `point`
--

DROP TABLE IF EXISTS `point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `point` (
  `id` varchar(20) NOT NULL,
  `caption` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `t20` int(9) DEFAULT NULL,
  `odi` int(9) DEFAULT NULL,
  `test` int(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point`
--

LOCK TABLES `point` WRITE;
/*!40000 ALTER TABLE `point` DISABLE KEYS */;
INSERT INTO `point` VALUES ('1','For every run scored','Batting Points',1,1,1),('2','For every six hit','Batting Points',2,2,3);
/*!40000 ALTER TABLE `point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `profile` (
  `id` int(9) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (1000,'Viram','Dhangar','viram.dhangar@gmail.com','8097547286'),(1001,'Sunil','Patidar','patidarsunil110@gmail.com','8989944001'),(1002,'Vijesh','Dhakad','vjsh.dhakad@gmail.com','9993041202');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `match_id` varchar(20) NOT NULL,
  `created_id` varchar(20) NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'Team 1','1152519','8097547286','2018-12-24 22:10:41');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_player`
--

DROP TABLE IF EXISTS `team_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `team_player` (
  `team_id` bigint(20) NOT NULL,
  `player_id` int(10) NOT NULL,
  `captain` varchar(1) DEFAULT 'N',
  `vice_captain` varchar(1) DEFAULT 'N',
  `team` varchar(20) NOT NULL,
  UNIQUE KEY `UC_TEAM_PLAYER` (`team_id`,`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_player`
--

LOCK TABLES `team_player` WRITE;
/*!40000 ALTER TABLE `team_player` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(120) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('employee','employee',1),('javainuse','javainuse',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `winning_breakup`
--

DROP TABLE IF EXISTS `winning_breakup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `winning_breakup` (
  `id` int(10) NOT NULL,
  `prizeMoney` int(10) NOT NULL,
  `prizeRank` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `winning_breakup`
--

LOCK TABLES `winning_breakup` WRITE;
/*!40000 ALTER TABLE `winning_breakup` DISABLE KEYS */;
INSERT INTO `winning_breakup` VALUES (100,157,'1'),(101,100,'1'),(102,85,'1'),(102,50,'2'),(102,15,'3'),(102,10,'4-5');
/*!40000 ALTER TABLE `winning_breakup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ifl'
--

--
-- Dumping routines for database 'ifl'
--
/*!50003 DROP PROCEDURE IF EXISTS `join_league` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `join_league`(IN teamId CHAR(20),
IN leagueId CHAR(20),
IN matchId CHAR(20),
IN createdId CHAR(20),
OUT result CHAR(20))
BEGIN
DECLARE leagueSize INT DEFAULT 0;   
DECLARE teamCount INT DEFAULT 0; 
   
   select size into leagueSize from match_leagues where id=leagueId;
   select count(team_id) into teamCount from league_teams where league_id=leagueId;
   
   IF teamCount < leagueSize THEN
     insert into league_teams (league_id, team_id, match_id, created_id) values (leagueId, teamId, matchId, createdId);
     -- check again
	 -- select count(team_id) into teamCount from league_teams where league_id = leagueId;
      IF (teamCount+1) = leagueSize THEN
		update match_leagues set status='Full', joined_team = (teamCount+1) where id= leagueId;
        insert into match_leagues (league_id, match_id, size) select league_id, match_id, size from match_leagues where id = leagueId;
	  ELSE
        update match_leagues set joined_team = (teamCount+1) where id= leagueId;
      END IF;
     SET result ='SUCCESS';
   ELSE
     SET result ='Full';
   END IF; 
   
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-29 14:25:24
