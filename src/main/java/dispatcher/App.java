package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.DatabaseReader;

import java.sql.SQLException;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("hello world");
        logger.error("test logs");
        logger.warn("test");
        logger.debug("test logs");

        DatabaseReader databaseReader = new DatabaseReader("ID",1,"C:\\test_java\\test");
        databaseReader.connectToDatabase();


    }

}

/*
https://www.oracle.com/database/technologies/develop-java-apps-using-jdbc.html

<dependency>
		<groupId>com.oracle</groupId>
		<artifactId>ojdbc</artifactId>
		<version>8</version>
		<scope>system</scope>
		<systemPath>d:/projects/ojdbc8.jar</systemPath>
	</dependency>
 */