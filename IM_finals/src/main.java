import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class main extends JFrame {

    private Connection conn;

    public main() {
        // Set up the frame
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Initialize the database connection
        try {
            conn = connectToDatabase();  // Establish the database connection
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(),
                                          "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Header label
        JLabel lblHeader = new JLabel("Library Management System", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblHeader, BorderLayout.NORTH);

        // Panel for buttons
        JPanel panelButtons = new JPanel(new GridLayout(4, 1, 10, 10));  // Changed to 4 rows
        panelButtons.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Button for Book Management System
        JButton btnBooks = new JButton("Book Management System");
        btnBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookManagementSystem(); // Open the Book Management System
            }
        });

        // Button for Member Management System
        JButton btnManageMembers = new JButton("Members");
        btnManageMembers.addActionListener(e -> openMemberManagementSystem());

        // Button for Transaction Management System
        JButton btnManageTransaction = new JButton("Transactions");
        btnManageTransaction.addActionListener(e -> openTransactionManagementSystem());

        // Button for Library Reports
        JButton btnReports = new JButton("Library Reports");
        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryReports(); 
            }
        });

        // Add buttons to panel
        panelButtons.add(btnBooks);
        panelButtons.add(btnManageMembers);
        panelButtons.add(btnManageTransaction);
        panelButtons.add(btnReports); // Added the Reports button

        // Add panel to frame
        add(panelButtons, BorderLayout.CENTER);

        // Set visible
        setLocationRelativeTo(null); // Center the frame on screen
        setVisible(true);
    }

    private Connection connectToDatabase() throws SQLException {
        // Replace these with your actual database credentials
        String url = "jdbc:mysql://localhost:3306/book_management";
        String user = "root";
        String password = ""; // Add your MySQL password here

        return DriverManager.getConnection(url, user, password);
    }

    private void openMemberManagementSystem() {
        // Open the Member Management System, passing the connection
        new MemberManagementSystem(conn);
    }

    private void openTransactionManagementSystem() {
        // Open the Transaction Management System, passing the connection
        new TransactionManagementSystem(conn);
    }

    public static void main(String[] args) {
        new main();
    }
}
