import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookManagementSystem extends JFrame {
    private JTextField txtBookName, txtAuthor, txtPrice, txtQuantity;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection conn;

    public BookManagementSystem() {
        // Initialize GUI components
        setTitle("Book Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent immediate exit
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(5, 2, 5, 5));
        panelTop.setBorder(BorderFactory.createTitledBorder("Book Information"));

        panelTop.add(new JLabel("Book Name:"));
        txtBookName = new JTextField();
        panelTop.add(txtBookName);

        panelTop.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        panelTop.add(txtAuthor);

        panelTop.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        panelTop.add(txtPrice);

        panelTop.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        panelTop.add(txtQuantity);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnNew = new JButton("New");

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnNew);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Book Name", "Author", "Price", "Quantity"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Handle the exit operation with confirmation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        BookManagementSystem.this, 
                        "Are you sure you want to exit?", 
                        "Exit Confirmation", 
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                    new main(); // Redirect to the main file when closing
                }
            }
        });

        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnNew.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtBookName.setText((String) tableModel.getValueAt(selectedRow, 1));
                    txtAuthor.setText((String) tableModel.getValueAt(selectedRow, 2));
                    txtPrice.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                    txtQuantity.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
                }
            }
        });

        try {
            connectToDatabase();
            loadBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void connectToDatabase() throws SQLException {
        // Replace these with your database credentials
        String url = "jdbc:mysql://localhost:3306/book_management";
        String user = "root";
        String password = ""; // Enter your MySQL password

        conn = DriverManager.getConnection(url, user, password);
        JOptionPane.showMessageDialog(this, "Connected to Database!");
    }

    private void loadBooks() {
        try {
            tableModel.setRowCount(0); // Clear the table
            String query = "SELECT * FROM books";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBook() {
        String name = txtBookName.getText();
        String author = txtAuthor.getText();
        String priceText = txtPrice.getText();
        String quantityText = txtQuantity.getText();

        if (name.isEmpty() || author.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            String query = "INSERT INTO books (name, author, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            loadBooks();
            clearForm();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = txtBookName.getText();
        String author = txtAuthor.getText();
        String priceText = txtPrice.getText();
        String quantityText = txtQuantity.getText();

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            String query = "UPDATE books SET name = ?, author = ?, price = ?, quantity = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setInt(5, id);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            loadBooks();
            clearForm();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            loadBooks();
            clearForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtBookName.setText("");
        txtAuthor.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    public static void main(String[] args) {
        new BookManagementSystem();
    }
}
