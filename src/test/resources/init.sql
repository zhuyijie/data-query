CREATE TABLE `adposition` (
  `userid` BIGINT(20) NOT NULL DEFAULT '0',
  `date` INT(11) NOT NULL DEFAULT '0',
  `adpositionid` BIGINT(20) NOT NULL DEFAULT '0',
  `channelid` BIGINT(20) NOT NULL DEFAULT '0',
  `regionid` BIGINT(20) NOT NULL DEFAULT '0',
  `showtimes` BIGINT(20) NULL DEFAULT NULL,
  `deliveryshow` BIGINT(20) NULL DEFAULT NULL,
  `clicktimes` BIGINT(20) NULL DEFAULT NULL,
  `income` BIGINT(20) NULL DEFAULT NULL,
  `uniqipcount` BIGINT(20) NULL DEFAULT NULL,
  `uniqvisitcount` BIGINT(20) NULL DEFAULT NULL
) AS SELECT * FROM CSVREAD('classpath:adposition.csv');

UPDATE `adposition` set `date` = UNIX_TIMESTAMP(`date`);