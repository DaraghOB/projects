import javax.swing.*;
import java.awt.*;
import java . sql .Connection;
import java . sql .DriverManager;
import java . sql .PreparedStatement;
import java . sql .SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JTextField;


public class customerMenu extends JFrame {

    private Connection conn;
    private int customerId = -1;
    
    public customerMenu() {
        setTitle("Customer Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(55, 55, 150));

        // create and add message label to top of window
        JLabel message = new JLabel("Customer Menu", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 24));
        message.setForeground(Color.YELLOW);
        add(message, BorderLayout.NORTH);

        // create buttons for each row
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createCustButton = new JButton("Create Customer");
        setButtonSize(createCustButton, 150, 50);
        createCustButton.setBackground(new Color(55, 55, 170));
        createCustButton.setForeground(Color.YELLOW);
        buttonPanel.add(createCustButton);

        
        //Action Listener for Create Customer
        createCustButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // create new JFrame for creating a new customer
        JFrame createCustFrame = new JFrame("Create Customer");
        createCustFrame.setLayout(new GridLayout(5, 2));
        createCustFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createCustFrame.getContentPane().setBackground(new Color(55, 55, 170));
        // create labels and text fields for input
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.YELLOW);
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.YELLOW);
        JTextField lastNameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.YELLOW);
        JTextField addressField = new JTextField();
        JLabel phoneNoLabel = new JLabel("Phone Number:");
        phoneNoLabel.setForeground(Color.YELLOW);
        JTextField phoneNoField = new JTextField();

        // add labels and text fields to JFrame
        createCustFrame.add(firstNameLabel);
        createCustFrame.add(firstNameField);
        createCustFrame.add(lastNameLabel);
        createCustFrame.add(lastNameField);
        createCustFrame.add(addressLabel);
        createCustFrame.add(addressField);
        createCustFrame.add(phoneNoLabel);
        createCustFrame.add(phoneNoField);

        // create "Create" and "Cancel" buttons
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        createButton.setBackground(new Color(0, 255, 0));
        createButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 0, 0));
        cancelButton.setForeground(Color.WHITE);

        // add action listener to create button
        createButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String address = addressField.getText();
        String phoneNoStr = phoneNoField.getText();
        createCustomer(firstName, lastName, address, phoneNoStr);
        createCustFrame.dispose(); // close create customer window
            }
        });

        // add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createCustFrame.dispose(); // close create customer window
            }
        });

        // add buttons to JFrame
        createCustFrame.add(createButton);
        createCustFrame.add(cancelButton);

        // set size and visibility of JFrame
        createCustFrame.pack();
        createCustFrame.setSize(600, 300);
        createCustFrame.setVisible(true);
        createCustFrame.getContentPane().setBackground(new Color(55, 55, 170));
        firstNameLabel.setForeground(Color.YELLOW);
        lastNameLabel.setForeground(Color.YELLOW);
        addressLabel.setForeground(Color.YELLOW);
        phoneNoLabel.setForeground(Color.YELLOW);
        createButton.setBackground(new Color(0, 255, 0));
        createButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        }
    });

        JButton retrieveCustButton = new JButton("Retrieve Customer");
        setButtonSize(retrieveCustButton, 150, 50);
        retrieveCustButton.setBackground(new Color(55, 55, 170));
        retrieveCustButton.setForeground(Color.YELLOW);
        buttonPanel.add(retrieveCustButton);

        retrieveCustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveCustomer();
            }
        });

        JButton updateCustButton = new JButton("Update Customer");
        setButtonSize(updateCustButton, 150, 50);
        updateCustButton.setBackground(new Color(55, 55, 170));
        updateCustButton.setForeground(Color.YELLOW);
        buttonPanel.add(updateCustButton);

        //action listener for Update Customer
        updateCustButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        JButton deleteCustButton = new JButton("Delete Customer");
        setButtonSize(deleteCustButton, 150, 50);
        deleteCustButton.setBackground(new Color(55, 55, 170));
        deleteCustButton.setForeground(Color.YELLOW);
        buttonPanel.add(deleteCustButton);

        //action listener for Delete Customer
        deleteCustButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // prompt the user for the customer ID to delete
                String customerIdStr = JOptionPane.showInputDialog("Enter customer ID to delete:");
                if (customerIdStr != null) {
                    try {
                        int customerId = Integer.parseInt(customerIdStr);
                        deleteCustomer(customerId);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid customer ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        add(buttonPanel, BorderLayout.CENTER);

        // add Main Menu button at bottom right corner
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBackground(new Color(0, 128, 0));
        mainMenuButton.setForeground(Color.WHITE);
        setButtonSize(mainMenuButton, 100, 50);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(55, 55, 150));
        bottomPanel.add(mainMenuButton);

        

        // add ActionListener to Main Menu button
        mainMenuButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // close the current window
            new mainMenu(); // open a new instance of the main menu
            }
        });

        add(bottomPanel, BorderLayout.SOUTH);

        // set the size of the window
        setSize(new Dimension(600, 300));

        // pack and display the window
        setVisible(true);
    }

    
    /** 
     * @param button
     * @param width
     * @param height
     */
    public void setButtonSize(JButton button, int width, int height) {
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        new customerMenu();
    }
    
    /**

    Creates a new customer with the given first name, last name, address, and phone number. This method
    prompts the user to enter a phone number in a valid format, checks if the format is correct, and inserts
    the new customer into the database if the phone number is valid. If the customer creation is successful,
    this method displays a message containing the new customer's information to the user. If the customer
    creation fails, an error message is displayed to the user.
    param firstName the first name of the new customer
    param lastName the last name of the new customer
    param address the address of the new customer
    param phoneNoStr the phone number of the new customer as a string
    */


    public void createCustomer(String firstName, String lastName, String address, String phoneNoStr) {
        PreparedStatement stmt = null;
        String phoneNo = null;
        boolean isValid = false;
        JTextField phoneNoField = new JTextField();
    
        // loop until a valid phone number is entered or the user cancels
        while (!isValid) {
            try {
                // store phone number as a string to preserve leading 0's
                phoneNo = phoneNoStr;
                // check if phone number has a valid format
                String regex = "[0-9+\\-() ]+";
                if (phoneNo.matches(regex)) {
                    isValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                    phoneNoStr = "";
                    // Clear the phone number field in the format
                    phoneNoField.getText();
                    phoneNoField.setText("");
                }
            } catch (NullPointerException ex) {
                // user clicked cancel
                return;
            }
            if (!isValid) {
                phoneNoStr = JOptionPane.showInputDialog("Enter phone number:");
            }
        }
    
        try {
            // create a new database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
    
            // prepare a statement to insert a new customer into the customer table
            stmt = conn.prepareStatement("INSERT INTO customer (firstName, lastName, address, phoneNo) VALUES (?, ?, ?, ?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, address);
            stmt.setString(4, phoneNo);
    
            // execute the statement to insert the new customer
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // display the new customer's information to the user
                String message = "New customer created:\n";
                message += "Name: " + firstName + " " + lastName + "\n";
                message += "Address: " + address + "\n";
                message += "Phone number: " + phoneNo + "\n";
                JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create a new customer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while creating a new customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while closing the database connection: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**

    Deletes a customer with the given customer ID from the customer table. This method retrieves the customer's
    information from the database and displays it to the user in a confirmation dialog. If the user confirms the
    deletion, the customer is deleted from the database. If the deletion is successful, this method displays a
    message to the user indicating that the customer has been deleted. If the deletion fails, an error message is
    displayed to the user.
    param customerId the ID of the customer to delete
    */


    public void deleteCustomer(int customerId) {
        ResultSet rs = null;
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
             PreparedStatement stmt = conn.prepareStatement("SELECT firstName, lastName, address, phoneNo FROM customer WHERE custID = ?");
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM customer WHERE custID = ?")) {
            
            // prepare a statement to retrieve the customer details from the customer table
            stmt.setInt(1, customerId);
            
            // execute the statement to retrieve the customer details
            rs = stmt.executeQuery();
            if (rs.next()) {
                // display the customer details to the user
                String message = "Are you sure you want to delete this customer?\n\n";
                message += "Name: " + rs.getString("firstName") + " " + rs.getString("lastName") + "\n";
                message += "Address: " + rs.getString("address") + "\n";
                message += "Phone number: " + rs.getString("phoneNo") + "\n";
                int choice = JOptionPane.showOptionDialog(null, message, "Confirm Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"Yes", "No"}, "No");
                if (choice == JOptionPane.YES_OPTION) {
                    // prepare a statement to delete the customer from the customer table
                    deleteStmt.setInt(1, customerId);
                    
                    // execute the statement to delete the customer
                    int rowsDeleted = deleteStmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "The customer has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No customer with that ID was found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No customer with that ID was found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while closing the result set: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    /**

    Displays a form for updating a customer's details. This method prompts the user to enter a customer ID and
    retrieves the customer's details from the database. The customer details are displayed in a form, and the user can
    edit the details by clicking on the "Edit" button. Once the user has made the desired changes, they can click on the
    "Confirm" button to update the customer's details in the database. If the update is successful, a message is displayed
    to the user indicating the number of rows that were updated. If the update fails, an error message is displayed to the
    user.
    */
    
    
    public void updateCustomer() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        JDialog updateCustFrame = new JDialog();
        
        
        
        try {
            // create a new database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
            
        
            // ask the user to enter a customer ID
            customerId = Integer.parseInt(JOptionPane.showInputDialog("Enter customer ID:"));
        
            // prepare a statement to retrieve the customer details from the customer table
            stmt = conn.prepareStatement("SELECT firstName, lastName, address, phoneNo FROM customer WHERE custID = ?");
            stmt.setInt(1, customerId);
        
            // execute the query and retrieve the results
            rs = stmt.executeQuery();
        
            // create a form to display the customer details
            JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
            final JTextField firstNameField;
            final JTextField lastNameField;
            final JTextField addressField;
            final JTextField phoneNoField;
            if (rs.next()) { // move the cursor to the first row
                firstNameField = new JTextField(rs.getString("firstName"));
                firstNameField.setEditable(false);
                lastNameField = new JTextField(rs.getString("lastName"));
                lastNameField.setEditable(false);
                addressField = new JTextField(rs.getString("address"));
                addressField.setEditable(false);
                phoneNoField = new JTextField(rs.getString("phoneNo"));
                phoneNoField.setEditable(false);
        
                JLabel firstNameLabel = new JLabel("First Name:");
                JLabel lastNameLabel = new JLabel("Last Name:");
                JLabel addressLabel = new JLabel("Address:");
                JLabel phoneNoLabel = new JLabel("Phone Number:");
                
                // set foreground and background colors
                firstNameLabel.setForeground(Color.YELLOW);
                lastNameLabel.setForeground(Color.YELLOW);
                addressLabel.setForeground(Color.YELLOW);
                phoneNoLabel.setForeground(Color.YELLOW);
                form.setBackground(new Color(55, 55, 170));
                firstNameField.setBackground(Color.WHITE);
                lastNameField.setBackground(Color.WHITE);
                addressField.setBackground(Color.WHITE);
                phoneNoField.setBackground(Color.WHITE);
        
                form.add(firstNameLabel);
                form.add(firstNameField);
                form.add(lastNameLabel);
                form.add(lastNameField);
                form.add(addressLabel);
                form.add(addressField);
                form.add(phoneNoLabel);
                form.add(phoneNoField);
            } else {
                // no rows returned
                JOptionPane.showMessageDialog(null, "No customer found with ID " + customerId, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
    
            JButton editButton = new JButton("Edit");
            editButton.setBackground(new Color(0, 255, 0));
            editButton.setForeground(Color.WHITE);

            editButton.addActionListener(new ActionListener() {
                private boolean isEditable = false;
            
                @Override
                public void actionPerformed(ActionEvent e) {
                    isEditable = !isEditable; // toggle the value of isEditable
            
                    // set the editability of the text fields based on the value of isEditable
                    firstNameField.setEditable(isEditable);
                    lastNameField.setEditable(isEditable);
                    addressField.setEditable(isEditable);
                    phoneNoField.setEditable(isEditable);
            
                    // change the text on the edit button based on the value of isEditable
                    editButton.setText(isEditable ? "Lock" : "Edit");
                }
            });


            JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 165, 0));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustFrame.dispose(); // close the dialog box
            }
        });
            
            JButton confirmButton = new JButton("Confirm");
            confirmButton.setBackground(new Color(255, 0, 0));
            confirmButton.setForeground(Color.WHITE);

           
      
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Prompt the user with a confirmation dialog before updating the record
                    int confirmResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the record?", "Confirm Update", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        try {
                            // prepare a statement to update the customer details in the customer table
                            PreparedStatement updateStmt = conn.prepareStatement("UPDATE customer SET firstName = ?, lastName = ?, address = ?, phoneNo = ? WHERE custID = ?");
            
                            // get the updated values from the text fields
                            String firstName = firstNameField.getText();
                            String lastName = lastNameField.getText();
                            String address = addressField.getText();
                            String phoneNo = phoneNoField.getText();
            
                            // set the parameters in the update statement
                            updateStmt.setString(1, firstName);
                            updateStmt.setString(2, lastName);
                            updateStmt.setString(3, address);
                            updateStmt.setString(4, phoneNo);
                            updateStmt.setInt(5, customerId); // use the customerId entered by the user earlier
            
                            // execute the update statement
                            int rowsUpdated = updateStmt.executeUpdate();
            
                            // show a message indicating the number of rows updated
                            JOptionPane.showMessageDialog(null, rowsUpdated + " rows updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            
                            // close the dialog box
                            updateCustFrame.dispose();
            
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

                    
                    
            // add buttons to a panel
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            buttonPanel.setBackground(new Color(55, 55, 170));
            buttonPanel.add(backButton);
            buttonPanel.add(editButton);
            buttonPanel.add(confirmButton);
            
            // add button panel to the form
            form.add(buttonPanel);
    
            // display the form in a dialog box
            
            updateCustFrame.setTitle("Update Customer");
            updateCustFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            updateCustFrame.add(form);
            updateCustFrame.pack();
            updateCustFrame.setSize(600, 300);
            updateCustFrame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
    }
    
    /**
     * Retrieves and displays the details of a customer with the given ID from the
     * database.
     *
     * throws SQLException if there is an error executing the SQL query
     */

    public void retrieveCustomer() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            // create a new database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
    
            // ask the user to enter a customer ID
            int customerId = Integer.parseInt(JOptionPane.showInputDialog("Enter customer ID:"));
    
            // prepare a statement to retrieve the customer details from the customer table
            stmt = conn.prepareStatement("SELECT firstName, lastName, address, phoneNo FROM customer WHERE custID = ?");
            stmt.setInt(1, customerId);
    
            // execute the query and retrieve the results
            rs = stmt.executeQuery();
    
            // create a form to display the customer details
            JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
            final JTextField firstNameField;
            final JTextField lastNameField;
            final JTextField addressField;
            final JTextField phoneNoField;
    
            if (rs.next()) { // move the cursor to the first row
                firstNameField = new JTextField(rs.getString("firstName"));
                firstNameField.setEditable(false);
                lastNameField = new JTextField(rs.getString("lastName"));
                lastNameField.setEditable(false);
                addressField = new JTextField(rs.getString("address"));
                addressField.setEditable(false);
                phoneNoField = new JTextField(rs.getString("phoneNo"));
                phoneNoField.setEditable(false);
    
                JLabel firstNameLabel = new JLabel("First Name:");
                JLabel lastNameLabel = new JLabel("Last Name:");
                JLabel addressLabel = new JLabel("Address:");
                JLabel phoneNoLabel = new JLabel("Phone Number:");
    
                // set foreground and background colors
                firstNameLabel.setForeground(Color.YELLOW);
                lastNameLabel.setForeground(Color.YELLOW);
                addressLabel.setForeground(Color.YELLOW);
                phoneNoLabel.setForeground(Color.YELLOW);
                form.setBackground(new Color(55, 55, 170));
                firstNameField.setBackground(Color.WHITE);
                lastNameField.setBackground(Color.WHITE);
                addressField.setBackground(Color.WHITE);
                phoneNoField.setBackground(Color.WHITE);
    
                form.add(firstNameLabel);
                form.add(firstNameField);
                form.add(lastNameLabel);
                form.add(lastNameField);
                form.add(addressLabel);
                form.add(addressField);
                form.add(phoneNoLabel);
                form.add(phoneNoField);
            } else {
                // no rows returned
                JOptionPane.showMessageDialog(null, "No customer found with ID " + customerId, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // create a button to close the form
            JButton closeButton = new JButton("Close");
            closeButton.setBackground(new Color(0, 255, 0));
            closeButton.setForeground(Color.WHITE);
    
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // close the form
                    JDialog retrieveCustFrame = (JDialog) SwingUtilities.getWindowAncestor(form);
                    retrieveCustFrame.dispose();
                }
            });
    
            // add button panel to the form
            form.add(closeButton);
    
            // display the form in a dialog box
            JDialog retrieveCustFrame = new JDialog();
            retrieveCustFrame.setTitle("Customer Details");
            retrieveCustFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            retrieveCustFrame.add(form);
            retrieveCustFrame.pack();
            retrieveCustFrame.setSize(600, 300);
            retrieveCustFrame.setVisible(true);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}