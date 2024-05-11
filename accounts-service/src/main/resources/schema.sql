CREATE TABLE IF NOT EXISTS `accounts` (
  `customer_id` int NOT NULL,
   `account_id` varchar(100)  PRIMARY KEY,
  `account_type` varchar(100) NOT NULL,
  `balance` BIGINT NOT NULL,
  `created_at` TIMESTAMP  NOT NULL,
   `created_by` varchar(20) NOT NULL,
   `updated_at` TIMESTAMP  DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    `enable` boolean DEFAULT TRUE
);