package otus.learning.aksorokin.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Homework1Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Homework1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Homework1Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        logger.debug("Creating database tables");
        jdbcTemplate.execute("CREATE SEQUENCE education.users_seq");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.users (\n" +
                "                  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('education.users_seq'),\n" +
                "                  username varchar(100) NOT NULL,\n" +
                "                  password varchar(100) NOT NULL,\n" +
                "                  enabled smallint NOT NULL,\n" +
                "                  lastname varchar(100) NOT NULL,\n" +
                "                  age int NOT NULL,\n" +
                "                  interests varchar(1000) DEFAULT NULL,\n" +
                "                  city varchar(50) NOT NULL,\n" +
                "                  gender smallint NOT NULL,\n" +
                "                  PRIMARY KEY (id),\n" +
                "                  CONSTRAINT username_unique UNIQUE (username)\n" +
                "                )   \n" +
                "\n" +
                "alter sequence education.users_seq restart with 7;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.user_friends (\n" +
                "                   user_id bigint NOT NULL,\n" +
                "                   friend_id bigint NOT NULL,\n" +
                "                   PRIMARY KEY (friend_id,user_id)\n" +
                "                )");
  /*      jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.users (\n" +
                "  id bigint unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  username varchar(100) NOT NULL,\n" +
                "  password varchar(100) NOT NULL,\n" +
                "  enabled tinyint(1) NOT NULL,\n" +
                "  lastname varchar(100) NOT NULL,\n" +
                "  age int NOT NULL,\n" +
                "  interests varchar(1000) DEFAULT NULL,\n" +
                "  city varchar(50) NOT NULL,\n" +
                "  gender tinyint(1) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `username_unique` (username)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" +
                "");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.user_friends (\n" +
                "   user_id bigint NOT NULL,\n" +
                "   friend_id bigint NOT NULL,\n" +
                "   PRIMARY KEY (friend_id,user_id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" +
                "");

    */
    }
}
