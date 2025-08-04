import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        // JDBC URL, username, and password for the database
        String url = "jdbc:mysql://localhost:3306/book_management"; // Replace "library" with your database name
        String user = "root"; // Replace with your MySQL username
        String password = "password"; // Replace with your MySQL password

        // Establish and return the connection
        return DriverManager.getConnection(url, user, password);
    }
    
    
}

