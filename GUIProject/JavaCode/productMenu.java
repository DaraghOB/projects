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

public class productMenu extends JFrame {
private Connection conn;
private int productId = -1;

    public productMenu() {
        setTitle("Product Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(55, 55, 150));

        // create and add message label to top of window
        JLabel message = new JLabel("Product Menu", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 24));
        message.setForeground(Color.YELLOW);
        add(message, BorderLayout.NORTH);

        // create buttons for each row
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createProdButton = new JButton("Create Product");
        setButtonSize(createProdButton, 150, 50);
        createProdButton.setBackground(new Color(55, 55, 170));
        createProdButton.setForeground(Color.YELLOW);
        buttonPanel.add(createProdButton);

        
        //Action Listener for Create Product
        createProdButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // create new JFrame for creating a new product
        JFrame createProdFrame = new JFrame("Create Product");
        createProdFrame.setLayout(new GridLayout(5, 2));
        createProdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createProdFrame.getContentPane().setBackground(new Color(55, 55, 170));
        // create labels and text fields for input
        JLabel prodNameLabel = new JLabel("Product Name:");
        prodNameLabel.setForeground(Color.YELLOW);
        JTextField prodNameField = new JTextField();
        JLabel prodCostLabel = new JLabel("Cost:");
        prodCostLabel.setForeground(Color.YELLOW);
        JTextField prodCostField = new JTextField();
        JLabel prodStockLabel = new JLabel("Stock Level:");
        prodStockLabel.setForeground(Color.YELLOW);
        JTextField prodStockField = new JTextField();      

        // add labels and text fields to JFrame
        createProdFrame.add(prodNameLabel);
        createProdFrame.add(prodNameField);
        createProdFrame.add(prodCostLabel);
        createProdFrame.add(prodCostField);
        createProdFrame.add(prodStockLabel);
        createProdFrame.add(prodStockField);     

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
        String prodName = prodNameField.getText();
        String prodCost = prodCostField.getText();
        String prodStock = prodStockField.getText();
        createProduct(prodName, prodCost, prodStock);
        createProdFrame.dispose(); // close create product window
            }
        });

        // add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createProdFrame.dispose(); // close create product window
            }
        });

        // add buttons to JFrame
        createProdFrame.add(createButton);
        createProdFrame.add(cancelButton);

        // set size and visibility of JFrame
        createProdFrame.pack();
        createProdFrame.setSize(600, 300);
        createProdFrame.setVisible(true);
        createProdFrame.getContentPane().setBackground(new Color(55, 55, 170));
        prodNameLabel.setForeground(Color.YELLOW);
        prodCostLabel.setForeground(Color.YELLOW);
        prodStockLabel.setForeground(Color.YELLOW);
        createButton.setBackground(new Color(0, 255, 0));
        createButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        }
    });

        JButton retrieveProdButton = new JButton("Retrieve Product");
        setButtonSize(retrieveProdButton, 150, 50);
        retrieveProdButton.setBackground(new Color(55, 55, 170));
        retrieveProdButton.setForeground(Color.YELLOW);
        buttonPanel.add(retrieveProdButton);

        retrieveProdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveProduct();
            }
        });

        JButton updateProdButton = new JButton("Update Product");
        setButtonSize(updateProdButton, 150, 50);
        updateProdButton.setBackground(new Color(55, 55, 170));
        updateProdButton.setForeground(Color.YELLOW);
        buttonPanel.add(updateProdButton);

        //action listener for Update Product
        updateProdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        JButton deleteProdButton = new JButton("Delete Product");
        setButtonSize(deleteProdButton, 150, 50);
        deleteProdButton.setBackground(new Color(55, 55, 170));
        deleteProdButton.setForeground(Color.YELLOW);
        buttonPanel.add(deleteProdButton);

        //action listener for Delete Product
        deleteProdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // prompt the user for the product ID to delete
                String productIdStr = JOptionPane.showInputDialog("Enter product ID to delete:");
                if (productIdStr != null) {
                    try {
                        int productId = Integer.parseInt(productIdStr);
                        deleteProduct(productId);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid product ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
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
        new productMenu();
    }
    
    /**

    Creates a new product in the database with the given name, cost, and stock level.

    Validates user input and displays appropriate error messages.

    param prodName the name of the product to create

    param prodCost the cost of the product to create

    param prodStock the stock level of the product to create
    */


    public void createProduct(String prodName, String prodCost, String prodStock) {
        PreparedStatement stmt = null;
        Connection conn = null;
        boolean validInputs = false;
        int attempts = 0;
    
        while (!validInputs && attempts < 3) {
            try {
                // validate input
                int stockLevel = Integer.parseInt(prodStock);
                double cost = Double.parseDouble(prodCost);
                validInputs = true;
    
                // create a new database connection
                conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
    
                // check if product already exists in the database
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM product WHERE prodName = ?");
                stmt.setString(1, prodName);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    // product already exists, notify the user and return
                    JOptionPane.showMessageDialog(null, "A product with the name " + prodName + " already exists in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // prepare a statement to insert a new product into the product table
                if (stmt != null) {
                    stmt.close(); // close previous statement
                }
                stmt = conn.prepareStatement("INSERT INTO product (prodName, prodCost, prodStock) VALUES (?, ?, ?)");
                stmt.setString(1, prodName);
                stmt.setDouble(2, cost);
                stmt.setInt(3, stockLevel);
    
                // execute the statement to insert the new product
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    // display the new product's information to the user
                    String message = "New product created:\n";
                    message += "Name: " + prodName + "\n";
                    message += "Cost: €" + cost + "\n";
                    message += "Stock level: " + stockLevel + "\n";
                    JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create a new product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                // Display a message asking the user to enter valid inputs
                prodCost = JOptionPane.showInputDialog(null, "Invalid cost. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                prodStock = JOptionPane.showInputDialog(null, "Invalid stock level. Please enter an integer value.", "Error", JOptionPane.ERROR_MESSAGE);
                attempts++;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while creating a new product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while closing the prepared statement or database connection: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    
        if (!validInputs) {
            JOptionPane.showMessageDialog(null, "Failed to create a new product after three attempts.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**

    Deletes a product from the database with the given product ID.

    param productId the ID of the product to delete
    */

    public void deleteProduct(int productId) {
        ResultSet rs = null;
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
             PreparedStatement stmt = conn.prepareStatement("SELECT prodName, prodCost, prodStock FROM product WHERE prodID = ?");
             PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM product WHERE prodID = ?")) {
    
            // prepare a statement to retrieve the product details from the product table
            stmt.setInt(1, productId);
            // execute the statement to retrieve the product details
            rs = stmt.executeQuery();
            if (rs.next()) {
                // display the product details to the user
                String message = "Are you sure you want to delete this product?\n\n";
                message += "Name: " + rs.getString("prodName") + "\n";
                message += "Cost: €" + rs.getDouble("prodCost") + "\n";
                message += "Stock level: " + rs.getInt("prodStock") + "\n";
                int choice = JOptionPane.showOptionDialog(null, message, "Confirm Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"Yes", "No"}, "No");
                if (choice == JOptionPane.YES_OPTION) {
                    // prepare a statement to delete the product from the product table
                    deleteStmt.setInt(1, productId);
    
                    // execute the statement to delete the product
                    int rowsDeleted = deleteStmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(null, "The product has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No product with that ID was found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No product with that ID was found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while deleting the product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    Displays a form to update a product in the database.

    Prompts the user to enter a product ID, retrieves the product details from the database, and displays them in a form.

    Allows the user to edit the product details, and updates the database with the new values upon confirmation.

    If no product is found with the specified ID, displays an error message and returns.
    */

    public void updateProduct() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        JDialog updateProdFrame = new JDialog();
    
        try {
            // create a new database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
    
            // ask the user to enter a product ID
            productId = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID:"));
    
            // prepare a statement to retrieve the product details from the product table
            stmt = conn.prepareStatement("SELECT prodName, prodCost, prodStock FROM product WHERE prodID = ?");
            stmt.setInt(1, productId);
    
            // execute the query and retrieve the results
            rs = stmt.executeQuery();
    
            // create a form to display the product details
            JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
            final JTextField prodNameField;
            final JTextField prodCostField;
            final JTextField prodStockField;
            if (rs.next()) { // move the cursor to the first row
                prodNameField = new JTextField(rs.getString("prodName"));
                prodNameField.setEditable(false);
                prodCostField = new JTextField(String.valueOf(rs.getDouble("prodCost")));
                prodCostField.setEditable(false);
                prodStockField = new JTextField(String.valueOf(rs.getInt("prodStock")));
                prodStockField.setEditable(false);
    
                JLabel prodNameLabel = new JLabel("Product Name:");
                JLabel prodCostLabel = new JLabel("Cost:");
                JLabel prodStockLabel = new JLabel("Stock Level:");
    
                // set foreground and background colors
                prodNameLabel.setForeground(Color.YELLOW);
                prodCostLabel.setForeground(Color.YELLOW);
                prodStockLabel.setForeground(Color.YELLOW);
                form.setBackground(new Color(55, 55, 170));
                prodNameField.setBackground(Color.WHITE);
                prodCostField.setBackground(Color.WHITE);
                prodStockField.setBackground(Color.WHITE);
    
                form.add(prodNameLabel);
                form.add(prodNameField);
                form.add(prodCostLabel);
                form.add(prodCostField);
                form.add(prodStockLabel);
                form.add(prodStockField);
            } else {
                // no rows returned
                JOptionPane.showMessageDialog(null, "No product found with ID " + productId, "Error", JOptionPane.ERROR_MESSAGE);
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
                    prodNameField.setEditable(isEditable);
                    prodCostField.setEditable(isEditable);
                    prodStockField.setEditable(isEditable);
    
                    // change the text on the edit button based on the value of isEditable
                    editButton.setText(isEditable ? "Lock" : "Edit");
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
                             // prepare a statement to update the product details in the product table
                    PreparedStatement updateStmt = conn.prepareStatement("UPDATE product SET prodName = ?, prodCost = ?, prodStock = ? WHERE prodID = ?");

                    // get the updated values from the text fields
                    String prodName = prodNameField.getText();
                    String prodCost = prodCostField.getText();
                    String prodStock = prodStockField.getText();

                    // set the parameters in the update statement
                    updateStmt.setString(1, prodName);
                    updateStmt.setString(2, prodCost);
                    updateStmt.setString(3, prodStock);
                    updateStmt.setInt(4, productId); // use the productId entered by the user earlier

                    // execute the update statement
                    int rowsUpdated = updateStmt.executeUpdate();

                    // show a message indicating the number of rows updated
                    JOptionPane.showMessageDialog(null, rowsUpdated + " rows updated", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // close the dialog box
                    updateProdFrame.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }});

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 165, 0));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProdFrame.dispose(); // close the dialog box
            }
        });

        // add buttons to a panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(new Color(55, 55, 170));
        buttonPanel.add(backButton);
        buttonPanel.add(editButton);
        buttonPanel.add(confirmButton);

        // add button panel to the form
        form.add(buttonPanel);

        // display the form in a dialog box
        updateProdFrame.setTitle("Update Product");
        updateProdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateProdFrame.add(form);
        updateProdFrame.pack();
        updateProdFrame.setSize(600, 300);
        updateProdFrame.setVisible(true);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    /**

    Retrieves product details from the database and displays them in a dialog box.

    Asks the user to enter a product ID and retrieves the corresponding product details

    from the "product" table in the "purchases" database using a prepared statement.

    Displays the product details in a form and allows the user to close the form.

    If the product ID entered by the user does not exist in the database, displays an error message.
    */

    public void retrieveProduct() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            // create a new database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/purchases", "root", "C00171575");
    
            // ask the user to enter a product ID
            int productId = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID:"));
    
            // prepare a statement to retrieve the product details from the product table
            stmt = conn.prepareStatement("SELECT prodName, prodCost, prodStock FROM product WHERE prodID = ?");
            stmt.setInt(1, productId);
    
            // execute the query and retrieve the results
            rs = stmt.executeQuery();
    
            // create a form to display the product details
            JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
            final JTextField prodNameField;
            final JTextField prodCostField;
            final JTextField prodStockLevelField;
    
            if (rs.next()) { // move the cursor to the first row
                prodNameField = new JTextField(rs.getString("prodName"));
                prodNameField.setEditable(false);
                prodCostField = new JTextField(rs.getString("prodCost"));
                prodCostField.setEditable(false);
                prodStockLevelField = new JTextField(rs.getString("prodStock"));
                prodStockLevelField.setEditable(false);
    
                JLabel prodNameLabel = new JLabel("Product Name:");
                JLabel prodCostLabel = new JLabel("Product Cost:");
                JLabel prodStockLevelLabel = new JLabel("Product Stock Level:");
    
                // set foreground and background colors
                prodNameLabel.setForeground(Color.YELLOW);
                prodCostLabel.setForeground(Color.YELLOW);
                prodStockLevelLabel.setForeground(Color.YELLOW);
                form.setBackground(new Color(55, 55, 170));
                prodNameField.setBackground(Color.WHITE);
                prodCostField.setBackground(Color.WHITE);
                prodStockLevelField.setBackground(Color.WHITE);
    
                form.add(prodNameLabel);
                form.add(prodNameField);
                form.add(prodCostLabel);
                form.add(prodCostField);
                form.add(prodStockLevelLabel);
                form.add(prodStockLevelField);
            } else {
                // no rows returned
                JOptionPane.showMessageDialog(null, "No product found with ID " + productId, "Error", JOptionPane.ERROR_MESSAGE);
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
                    JDialog retrieveProdFrame = (JDialog) SwingUtilities.getWindowAncestor(form);
                    retrieveProdFrame.dispose();
                }
            });
    
            // add button panel to the form
            form.add(closeButton);
    
            // display the form in a dialog box
            JDialog retrieveProdFrame = new JDialog();
            retrieveProdFrame.setTitle("Product Details");
            retrieveProdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            retrieveProdFrame.add(form);
            retrieveProdFrame.pack();
            retrieveProdFrame.setSize(600, 300);
            retrieveProdFrame.setVisible(true);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
