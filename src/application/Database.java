package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
	public static Connection connectDB() {
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
			return connect;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
