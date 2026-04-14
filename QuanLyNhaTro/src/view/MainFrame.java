package view;

import dao.CustomerDAO;
import dao.RentalDAO;
import dao.RoomDAO;
import model.Customer;
import model.Rental;
import model.Room;
import utils.PDFExporter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;

public class MainFrame extends JFrame {

    // DAO
    private RoomDAO roomDAO = new RoomDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private RentalDAO rentalDAO = new RentalDAO();

    // Status bar
    private JLabel lblStatus;

    // ROOM components
    private JTable tblRoom;
    private DefaultTableModel roomModel;
    private JTextField txtRoomId, txtRoomName, txtRoomPrice, txtRoomStatus;
    private JTextField txtRoomSearch;

    // CUSTOMER components
    private JTable tblCustomer;
    private DefaultTableModel customerModel;
    private JTextField txtCustomerId, txtCustomerName, txtCustomerPhone, txtCustomerCccd;
    private JTextField txtCustomerSearch;

    // RENTAL components
    private JTable tblRental;
    private DefaultTableModel rentalModel;
    private JTextField txtRentalId, txtRentalRoomId, txtRentalCustomerId, txtRentalDate;
    private JTextField txtRentalSearch;

    public MainFrame() {
        setTitle("Rental Management System");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Room Management", createRoomPanel());
        tabs.addTab("Customer Management", createCustomerPanel());
        tabs.addTab("Rental Management", createRentalPanel());
        add(tabs, BorderLayout.CENTER);

        // Status bar
        lblStatus = new JLabel("Checking database connection...");
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(lblStatus);
        add(statusPanel, BorderLayout.SOUTH);

        checkDBConnection();

        loadRoomTable();
        loadCustomerTable();
        loadRentalTable();
    }

    // ===================== ROOM PANEL =====================

    private JPanel createRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // FORM
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtRoomId = new JTextField(); txtRoomId.setEditable(false); txtRoomId.setFocusable(false);
        txtRoomName = new JTextField();
        txtRoomPrice = new JTextField();
        txtRoomStatus = new JTextField("Available");

        form.add(new JLabel("Room ID:")); form.add(txtRoomId);
        form.add(new JLabel("Room Name:")); form.add(txtRoomName);
        form.add(new JLabel("Price:")); form.add(txtRoomPrice);
        form.add(new JLabel("Status:")); form.add(txtRoomStatus);

        panel.add(form, BorderLayout.NORTH);

        // SEARCH BAR
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtRoomSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        JButton btnShowAll = new JButton("Show All");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtRoomSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnShowAll);

        panel.add(searchPanel, BorderLayout.WEST);

        // TABLE
        roomModel = new DefaultTableModel(new Object[]{"Room ID", "Room Name", "Price", "Status"}, 0);
        tblRoom = new JTable(roomModel);
        styleTable(tblRoom);
        panel.add(new JScrollPane(tblRoom), BorderLayout.CENTER);

        // BUTTON BAR
        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnExportPDF = new JButton("Export PDF");
        
        
        
        buttons.add(btnAdd); buttons.add(btnUpdate); buttons.add(btnDelete); buttons.add(btnClear);
        panel.add(buttons, BorderLayout.SOUTH);

        // EVENTS ---------------------------------------

        tblRoom.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblRoom.getSelectedRow();
                txtRoomId.setText(roomModel.getValueAt(row, 0).toString());
                txtRoomName.setText(roomModel.getValueAt(row, 1).toString());
                txtRoomPrice.setText(roomModel.getValueAt(row, 2).toString());
                txtRoomStatus.setText(roomModel.getValueAt(row, 3).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                String name = txtRoomName.getText().trim();
                double price = Double.parseDouble(txtRoomPrice.getText().trim());
                String status = txtRoomStatus.getText().trim();

                Room r = new Room(name, price, status);
                if (roomDAO.insert(r)) {
                    JOptionPane.showMessageDialog(this, "Room added successfully");
                    loadRoomTable();
                    clearRoomForm();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Price must be a number");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (txtRoomId.getText().isEmpty()) return;
            try {
                Room r = new Room(
                        Integer.parseInt(txtRoomId.getText()),
                        txtRoomName.getText(),
                        Double.parseDouble(txtRoomPrice.getText()),
                        txtRoomStatus.getText()
                );
                if (roomDAO.update(r)) {
                    JOptionPane.showMessageDialog(this, "Updated successfully");
                    loadRoomTable();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid data!");
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtRoomId.getText().isEmpty()) return;
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this room??", "Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                roomDAO.delete(Integer.parseInt(txtRoomId.getText()));
                loadRoomTable();
                clearRoomForm();
            }
        });

        btnClear.addActionListener(e -> clearRoomForm());

        btnSearch.addActionListener(e -> {
            String key = txtRoomSearch.getText().trim().toLowerCase();
            roomModel.setRowCount(0);
            for (Room r : roomDAO.getAll()) {
                if (r.getRoomName().toLowerCase().contains(key)
                        || r.getStatus().toLowerCase().contains(key)) {
                    roomModel.addRow(new Object[]{r.getRoomId(), r.getRoomName(), r.getPrice(), r.getStatus()});
                }
            }
        });

        btnShowAll.addActionListener(e -> loadRoomTable());

        return panel;
    }

    private void clearRoomForm() {
        txtRoomId.setText("");
        txtRoomName.setText("");
        txtRoomPrice.setText("");
        txtRoomStatus.setText("Available");
    }

    private void loadRoomTable() {
        roomModel.setRowCount(0);
        for (Room r : roomDAO.getAll()) {
            roomModel.addRow(new Object[]{r.getRoomId(), r.getRoomName(), r.getPrice(), r.getStatus()});
        }
    }

    // ===================== CUSTOMER PANEL =====================

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // FORM
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtCustomerId = new JTextField(); txtCustomerId.setEditable(false); txtCustomerId.setFocusable(false);
        txtCustomerName = new JTextField();
        txtCustomerPhone = new JTextField();
        txtCustomerCccd = new JTextField();

        form.add(new JLabel("Customer ID:")); form.add(txtCustomerId);
        form.add(new JLabel("Full Name:")); form.add(txtCustomerName);
        form.add(new JLabel("Phone:")); form.add(txtCustomerPhone);
        form.add(new JLabel("ID Number:")); form.add(txtCustomerCccd);

        panel.add(form, BorderLayout.NORTH);

        // SEARCH
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCustomerSearch = new JTextField(20);
        JButton btnSearchCustomer = new JButton("Search");
        JButton btnShowAllCustomer = new JButton("Show All");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtCustomerSearch);
        searchPanel.add(btnSearchCustomer);
        searchPanel.add(btnShowAllCustomer);
        panel.add(searchPanel, BorderLayout.WEST);

        // TABLE
        customerModel = new DefaultTableModel(new Object[]{"Customer ID", "Full Name", "Phone", "ID Number"}, 0);
        tblCustomer = new JTable(customerModel);
        styleTable(tblCustomer);
        panel.add(new JScrollPane(tblCustomer), BorderLayout.CENTER);

        // BUTTONS
        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        buttons.add(btnAdd); 
        buttons.add(btnUpdate); 
        buttons.add(btnDelete);
        buttons.add(btnClear);
       
        panel.add(buttons, BorderLayout.SOUTH);

        // EVENTS
        tblCustomer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblCustomer.getSelectedRow();
                txtCustomerId.setText(customerModel.getValueAt(row, 0).toString());
                txtCustomerName.setText(customerModel.getValueAt(row, 1).toString());
                txtCustomerPhone.setText(customerModel.getValueAt(row, 2).toString());
                txtCustomerCccd.setText(customerModel.getValueAt(row, 3).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            Customer c = new Customer(
                    txtCustomerName.getText(),
                    txtCustomerPhone.getText(),
                    txtCustomerCccd.getText()
            );
            customerDAO.insert(c);
            loadCustomerTable();
            clearCustomerForm();
        });

        btnUpdate.addActionListener(e -> {
            if (txtCustomerId.getText().isEmpty()) return;
            Customer c = new Customer(
                    Integer.parseInt(txtCustomerId.getText()),
                    txtCustomerName.getText(),
                    txtCustomerPhone.getText(),
                    txtCustomerCccd.getText()
            );
            customerDAO.update(c);
            loadCustomerTable();
        });

        btnDelete.addActionListener(e -> {
            if (txtCustomerId.getText().isEmpty()) return;
            customerDAO.delete(Integer.parseInt(txtCustomerId.getText()));
            loadCustomerTable();
            clearCustomerForm();
        });

        btnClear.addActionListener(e -> clearCustomerForm());

        btnSearchCustomer.addActionListener(e -> {
            String key = txtCustomerSearch.getText().trim().toLowerCase();
            customerModel.setRowCount(0);
            for (Customer c : customerDAO.getAll()) {
                if (c.getName().toLowerCase().contains(key)
                        || c.getPhone().toLowerCase().contains(key)
                        || c.getCccd().toLowerCase().contains(key)) {
                    customerModel.addRow(new Object[]{
                            c.getCustomerId(), c.getName(), c.getPhone(), c.getCccd()
                    });
                }
            }
        });

        btnShowAllCustomer.addActionListener(e -> loadCustomerTable());

        return panel;
    }

    private void clearCustomerForm() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
        txtCustomerCccd.setText("");
    }

    private void loadCustomerTable() {
        customerModel.setRowCount(0);
        for (Customer c : customerDAO.getAll()) {
            customerModel.addRow(new Object[]{c.getCustomerId(), c.getName(), c.getPhone(), c.getCccd()});
        }
    }

    // ===================== RENTAL PANEL =====================

    private JPanel createRentalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // FORM
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtRentalId = new JTextField(); txtRentalId.setEditable(false); txtRentalId.setFocusable(false);
        txtRentalRoomId = new JTextField();
        txtRentalCustomerId = new JTextField();
        txtRentalDate = new JTextField("2025-01-01");

        form.add(new JLabel("Rental ID:")); form.add(txtRentalId);
        form.add(new JLabel("Room ID:")); form.add(txtRentalRoomId);
        form.add(new JLabel("Customer ID:")); form.add(txtRentalCustomerId);
        form.add(new JLabel("Rent Date:")); form.add(txtRentalDate);

        panel.add(form, BorderLayout.NORTH);

        // SEARCH BAR
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtRentalSearch = new JTextField(20);
        JButton btnSearchRental = new JButton("Search");
        JButton btnShowAllRental = new JButton("Show All");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtRentalSearch);
        searchPanel.add(btnSearchRental);
        searchPanel.add(btnShowAllRental);
        panel.add(searchPanel, BorderLayout.WEST);

        // TABLE
        rentalModel = new DefaultTableModel(new Object[]{"Rental ID", "Room ID", "Customer ID", "Rent Date"}, 0);
        tblRental = new JTable(rentalModel);
        styleTable(tblRental);
        panel.add(new JScrollPane(tblRental), BorderLayout.CENTER);

        // BUTTONS
        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Add Rental");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnExportPDF = new JButton("Export PDF");
        
        buttons.add(btnAdd); buttons.add(btnDelete); buttons.add(btnClear);buttons.add(btnExportPDF); 
        panel.add(buttons, BorderLayout.SOUTH);

        // EVENTS
        tblRental.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblRental.getSelectedRow();
                txtRentalId.setText(rentalModel.getValueAt(row, 0).toString());
                txtRentalRoomId.setText(rentalModel.getValueAt(row, 1).toString());
                txtRentalCustomerId.setText(rentalModel.getValueAt(row, 2).toString());
                txtRentalDate.setText(rentalModel.getValueAt(row, 3).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                Rental r = new Rental(
                        Integer.parseInt(txtRentalRoomId.getText()),
                        Integer.parseInt(txtRentalCustomerId.getText()),
                        Date.valueOf(txtRentalDate.getText())
                );
                rentalDAO.insert(r);
                loadRentalTable();
                clearRentalForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input !");
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtRentalId.getText().isEmpty()) return;
            rentalDAO.delete(Integer.parseInt(txtRentalId.getText()));
            loadRentalTable();
            clearRentalForm();
        });

        btnClear.addActionListener(e -> clearRentalForm());

        btnSearchRental.addActionListener(e -> {
            String key = txtRentalSearch.getText().trim();
            rentalModel.setRowCount(0);
            for (Rental r : rentalDAO.getAll()) {
                if (String.valueOf(r.getRoomId()).contains(key)
                        || String.valueOf(r.getCustomerId()).contains(key)) {
                    rentalModel.addRow(new Object[]{
                            r.getRentalId(), r.getRoomId(), r.getCustomerId(), r.getDateRent()
                    });
                }
            }
        });

        btnShowAllRental.addActionListener(e -> loadRentalTable());
        btnExportPDF.addActionListener(e -> {

            int selectedRow = tblRental.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a rental!");
                return;
            }

            int rentalId = Integer.parseInt(tblRental.getValueAt(selectedRow, 0).toString());

            Rental rental = rentalDAO.findById(rentalId);
            Room room = roomDAO.findById(rental.getRoomId());
            Customer customer = customerDAO.findById(rental.getCustomerId());

            utils.PDFExporter.exportRentalToPDF(rental, room, customer);

            JOptionPane.showMessageDialog(this, "Export PDF successfully!");
        });
        return panel;
    }

    private void clearRentalForm() {
        txtRentalId.setText("");
        txtRentalRoomId.setText("");
        txtRentalCustomerId.setText("");
        txtRentalDate.setText("2025-01-01");
    }

    private void loadRentalTable() {
        rentalModel.setRowCount(0);
        for (Rental r : rentalDAO.getAll()) {
            rentalModel.addRow(new Object[]{
                    r.getRentalId(), r.getRoomId(), r.getCustomerId(), r.getDateRent()
            });
        }
    }

    // ===================== STATUS CHECK =====================

    private void checkDBConnection() {
        try (java.sql.Connection con = dao.DBConnect.getConnection()) {
            lblStatus.setText("Connected to MySQL successfully");
            lblStatus.setForeground(new Color(0, 150, 0));
        } catch (Exception e) {
            lblStatus.setText("Failed to connect to MySQL ");
            lblStatus.setForeground(Color.RED);
        }
    }

    // ===================== TABLE STYLE =====================

    private void styleTable(JTable table) {
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(200, 230, 255));
        table.getTableHeader().setOpaque(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
    }

    // ===================== MAIN =====================

    public static void main(String[] args) {
        System.setProperty("apple.awt.disableAsyncTextInput", "true");
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}