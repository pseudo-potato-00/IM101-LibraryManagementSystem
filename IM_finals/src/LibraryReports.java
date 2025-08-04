import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LibraryReports extends JFrame {

    private JTextArea textArea;

    public LibraryReports() {
        setTitle("Library Reports");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create the main layout
        setLayout(new BorderLayout(20, 20));

        // Create a header label with a bold font
        JLabel headerLabel = new JLabel("Library Reports", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 102, 204));
        add(headerLabel, BorderLayout.NORTH);

        // Create the text area to display results
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(255, 255, 255));

        // Button for "View Borrowed Books"
        JButton btnBorrowedBooks = new JButton("View Borrowed Books with Member Details");
        styleButton(btnBorrowedBooks);
        btnBorrowedBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBorrowedBooksReport();
            }
        });

        // Button for "Total Borrowed Books"
        JButton btnTotalBooks = new JButton("Total Borrowed Books");
        styleButton(btnTotalBooks);
        btnTotalBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalBooksReport();
            }
        });

        // Button for "Book Borrowing Statistics"
        JButton btnBookStats = new JButton("Book Borrowing Statistics");
        styleButton(btnBookStats);
        btnBookStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookStatsReport();
            }
        });

        // Add buttons to panel
        panel.add(btnBorrowedBooks);
        panel.add(btnTotalBooks);
        panel.add(btnBookStats);

        // Add panel to frame
        add(panel, BorderLayout.WEST);

        setVisible(true); // Ensure the window is shown
    }

    // Method to style buttons (reduce size and default colors)
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Smaller font size
        button.setPreferredSize(new Dimension(200, 40)); // Smaller button size
        button.setFocusPainted(true);
    }

    // Method to display the "Borrowed Books" report
    private void showBorrowedBooksReport() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = """
                    SELECT m.name, b.name, t.borrow_date
                    FROM transactions t
                    JOIN members m ON t.member_id = m.member_id
                    JOIN books b ON t.id = b.id
                    WHERE t.status = 'Borrowed';
                    """;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                StringBuilder result = new StringBuilder("Borrowed Books with Member Details:\n\n");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String title = rs.getString("name");
                    Date borrowDate = rs.getDate("borrow_date");
                    result.append("Member: ").append(name)
                          .append(", Book: ").append(title)
                          .append(", Borrow Date: ").append(borrowDate)
                          .append("\n");
                }
                textArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report: " + e.getMessage());
        }
    }

    // Method to display the "Total Borrowed Books" report
    private void showTotalBooksReport() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS total_borrowed FROM Transactions WHERE status = 'Borrowed';";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    int totalBorrowed = rs.getInt("total_borrowed");
                    textArea.setText("Total Borrowed Books: " + totalBorrowed);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report: " + e.getMessage());
        }
    }

    // Method to display the "Book Borrowing Statistics" report
    private void showBookStatsReport() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = """
                    SELECT b.name AS 'Book Title', COUNT(t.id) AS 'Times Borrowed'
                    FROM Books b
                    LEFT JOIN transactions t ON b.id = t.id
                    GROUP BY b.id;
                    """;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                StringBuilder result = new StringBuilder("Book Borrowing Statistics:\n\n");
                while (rs.next()) {
                    String bookTitle = rs.getString("Book Title");
                    int timesBorrowed = rs.getInt("Times Borrowed");
                    result.append("Book: ").append(bookTitle)
                          .append(", Times Borrowed: ").append(timesBorrowed)
                          .append("\n");
                }
                textArea.setText(result.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report: " + e.getMessage());
        }
    }

    // Database Connection class
    private static class DatabaseConnection {
        private static Connection getConnection() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/book_management"; // Replace with your DB URL
            String user = "root";  // Replace with your DB username
            String password = "password";  // Replace with your DB password
            return DriverManager.getConnection(url, user, password);
        }
    }

    public static void main(String[] args) {
        new LibraryReports();
    }
}
