import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MemberManagementSystem extends JFrame {
    private JTextField txtMemberName, txtEmail, txtPhone, txtMembershipDate;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection conn;

    public MemberManagementSystem(Connection connection) {
        this.conn = connection;

        setTitle("Member Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Top Panel for Input
        JPanel panelTop = createInputPanel();

        // Center Table Panel
        JScrollPane scrollPane = createTablePanel();

        // Bottom Panel for Buttons
        JPanel panelBottom = createButtonPanel();

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // Load data into the table
        loadMembers();

        // Center the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Member Information"));

        panel.add(new JLabel("Name:"));
        txtMemberName = new JTextField();
        panel.add(txtMemberName);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Membership Date (YYYY-MM-DD):"));
        txtMembershipDate = new JTextField();
        panel.add(txtMembershipDate);

        return panel;
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Phone", "Membership Date"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                populateFormFromTable();
            }
        });

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnExit = new JButton("Exit");

        btnAdd.addActionListener(e -> addMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> dispose());

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnExit);

        return panel;
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            txtMemberName.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtPhone.setText((String) tableModel.getValueAt(selectedRow, 3));
            txtMembershipDate.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        }
    }

    private void loadMembers() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT * FROM members";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getDate("membership_date")
                });
            }
        } catch (SQLException e) {
            showError("Error loading members: " + e.getMessage());
        }
    }

    private void addMember() {
        String name = txtMemberName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String membershipDate = txtMembershipDate.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || membershipDate.isEmpty()) {
            showError("All fields are required!");
            return;
        }

        try {
            String query = "INSERT INTO members (name, email, phone, membership_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setDate(4, Date.valueOf(membershipDate));
            stmt.executeUpdate();

            showMessage("Member added successfully!");
            loadMembers();
            clearForm();
        } catch (SQLException | IllegalArgumentException e) {
            showError("Error adding member: " + e.getMessage());
        }
    }

    private void updateMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Select a member to update!");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = txtMemberName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String membershipDate = txtMembershipDate.getText();

        try {
            String query = "UPDATE members SET name = ?, email = ?, phone = ?, membership_date = ? WHERE member_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setDate(4, Date.valueOf(membershipDate));
            stmt.setInt(5, id);
            stmt.executeUpdate();

            showMessage("Member updated successfully!");
            loadMembers();
            clearForm();
        } catch (SQLException | IllegalArgumentException e) {
            showError("Error updating member: " + e.getMessage());
        }
    }

    private void deleteMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Select a member to delete!");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            String query = "DELETE FROM members WHERE member_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            showMessage("Member deleted successfully!");
            loadMembers();
            clearForm();
        } catch (SQLException e) {
            showError("Error deleting member: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMemberName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtMembershipDate.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_management", "root", "");
            SwingUtilities.invokeLater(() -> new MemberManagementSystem(conn));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
