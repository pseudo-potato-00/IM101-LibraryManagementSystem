import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionManagementSystem extends JFrame {
    private JTextField txtTransactionID, txtMemberID, txtBookID, txtBorrowDate, txtReturnDate, txtStatus;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection conn;

    public TransactionManagementSystem(Connection connection) {
        this.conn = connection;

        // Initialize GUI components
        setTitle("Transaction Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // So it doesn't close the entire app
        setSize(800, 500);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(6, 2, 5, 5));
        panelTop.setBorder(BorderFactory.createTitledBorder("Transaction Information"));

        panelTop.add(new JLabel("Transaction ID:"));
        txtTransactionID = new JTextField();
        txtTransactionID.setEditable(false); // Auto-generated
        panelTop.add(txtTransactionID);

        panelTop.add(new JLabel("Member ID:"));
        txtMemberID = new JTextField();
        panelTop.add(txtMemberID);

        panelTop.add(new JLabel("Book ID:"));
        txtBookID = new JTextField();
        panelTop.add(txtBookID);

        panelTop.add(new JLabel("Borrow Date (YYYY-MM-DD):"));
        txtBorrowDate = new JTextField();
        panelTop.add(txtBorrowDate);

        panelTop.add(new JLabel("Return Date (YYYY-MM-DD):"));
        txtReturnDate = new JTextField();
        panelTop.add(txtReturnDate);

        panelTop.add(new JLabel("Status (Borrowed/Returned):"));
        txtStatus = new JTextField();
        panelTop.add(txtStatus);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnNew = new JButton("New");

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnNew);

        tableModel = new DefaultTableModel(new Object[]{"Transaction ID", "Member ID", "Book ID", "Borrow Date", "Return Date", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addTransaction());
        btnUpdate.addActionListener(e -> updateTransaction());
        btnDelete.addActionListener(e -> deleteTransaction());
        btnNew.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtTransactionID.setText(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
                    txtMemberID.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
                    txtBookID.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
                    txtBorrowDate.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                    txtReturnDate.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
                    txtStatus.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
                }
            }
        });

        loadTransactions();

        // Center the window on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    // Load transactions from the database into the table
    private void loadTransactions() {
        try {
            tableModel.setRowCount(0); // Clear the table
            String query = "SELECT * FROM transactions";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("transaction_id"),
                    rs.getInt("member_id"),
                    rs.getInt("id"),
                    rs.getDate("borrow_date"),
                    rs.getDate("return_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Utility method to validate date format
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Don't allow invalid dates (e.g., 2024-02-30)
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Add a new transaction to the database
    private void addTransaction() {
        String memberID = txtMemberID.getText();
        String bookID = txtBookID.getText();
        String borrowDate = txtBorrowDate.getText();
        String returnDate = txtReturnDate.getText();
        String status = txtStatus.getText();

        if (memberID.isEmpty() || bookID.isEmpty() || borrowDate.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID, Book ID, Borrow Date, and Status are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(borrowDate)) {
            JOptionPane.showMessageDialog(this, "Invalid Borrow Date format! Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO transactions (member_id, id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(memberID));
            stmt.setInt(2, Integer.parseInt(bookID));
            stmt.setDate(3, Date.valueOf(borrowDate));

            if (returnDate.isEmpty()) {
                stmt.setNull(4, Types.DATE); // Set return_date to NULL if empty
            } else if (!isValidDate(returnDate)) {
                JOptionPane.showMessageDialog(this, "Invalid Return Date format! Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                stmt.setDate(4, Date.valueOf(returnDate));
            }

            stmt.setString(5, status);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            loadTransactions();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update an existing transaction
    private void updateTransaction() {
        String transactionID = txtTransactionID.getText();
        String memberID = txtMemberID.getText();
        String bookID = txtBookID.getText();
        String borrowDate = txtBorrowDate.getText();
        String returnDate = txtReturnDate.getText();
        String status = txtStatus.getText();

        if (transactionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a transaction to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(borrowDate)) {
            JOptionPane.showMessageDialog(this, "Invalid Borrow Date format! Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "UPDATE transactions SET member_id = ?, id = ?, borrow_date = ?, return_date = ?, status = ? WHERE transaction_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(memberID));
            stmt.setInt(2, Integer.parseInt(bookID));
            stmt.setDate(3, Date.valueOf(borrowDate));

            if (returnDate.isEmpty()) {
                stmt.setNull(4, Types.DATE); // Set return_date to NULL if empty
            } else if (!isValidDate(returnDate)) {
                JOptionPane.showMessageDialog(this, "Invalid Return Date format! Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                stmt.setDate(4, Date.valueOf(returnDate));
            }

            stmt.setString(5, status);
            stmt.setInt(6, Integer.parseInt(transactionID));
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Transaction updated successfully!");
            loadTransactions();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete a selected transaction
    private void deleteTransaction() {
        String transactionID = txtTransactionID.getText();

        if (transactionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a transaction to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "DELETE FROM transactions WHERE transaction_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(transactionID));
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
            loadTransactions();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear the input fields
    private void clearForm() {
        txtTransactionID.setText("");
        txtMemberID.setText("");
        txtBookID.setText("");
        txtBorrowDate.setText("");
        txtReturnDate.setText("");
        txtStatus.setText("");
    }

    // Main method to run the application
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_management", "root", "");
            new TransactionManagementSystem(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
