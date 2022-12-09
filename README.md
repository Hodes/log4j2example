# log4j2example

An example of Log4j2 java application that does custom loggings with JDBC appender.

It has Docker Compose to start the testing MySQL database.

## Configure database

* (Optional) Start mysql database using the given docker-compose.yml `docker compose up -d`
* Connect to the database and initialize log table
```

CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_at` datetime NOT NULL,
  `logger` varchar(100) NOT NULL,
  `loglevel` varchar(100) NOT NULL,
  `message` varchar(1000) NOT NULL,
  `exception` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;

```

* (Optional) Change the database info at `src/main/java/log4jexample/ConnectionFactory`

## Run App

`./gradlew run`


