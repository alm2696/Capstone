package capstone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the model layer of the restaurant application, providing methods
 * for interacting with the restaurant database. Including operations for adding, updating,
 * and deleting menu items and categories, as well as retrieving data from the database.
 * 
 * @author angel
 */
public class RestaurantModel {

    // Singleton instance of the RestaurantModel class
    private static RestaurantModel instance = null;

    // Database connection object
    private static Connection DBConn = null;

    // Database connection details
    private final String DBDriver = "com.mysql.cj.jdbc.Driver"; // MySQL JDBC Driver
    private final String DBURL = "jdbc:mysql://localhost:3306/CMSC230"; // Database URL
    private final String DBUser = "root"; // Database username
    private final String DBPassword = "Password!!"; // Database password

    /**
     * Private constructor to initialize the database connection.
     * 
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If the database driver class cannot be found
     */
    private RestaurantModel() throws SQLException, ClassNotFoundException {
        // If DBConn is not initialized, establish a new connection
        if (DBConn == null) {
            Class.forName(DBDriver); // Load the MySQL JDBC driver
            DBConn = DriverManager.getConnection(DBURL, DBUser, DBPassword); // Create connection
        }
    }

    /**
     * Singleton method to retrieve the instance of RestaurantModel.
     * 
     * @return                        The singleton instance of RestaurantModel
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If the database driver class cannot be found
     */
    public static synchronized RestaurantModel getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new RestaurantModel(); // Create instance if not already created
        }
        return instance;
    }

    /**
     * Adds a new menu item to the database.
     * 
     * @param items         The Item object containing the details of the item to be added
     * @throws SQLException If a database access error occurs
     */
    public void addItem(Item items) throws SQLException {
        // SQL query to insert a new item
        String addItem = "INSERT INTO items (name, description, price, category_id) VALUES (?, ?, ?, ?)";
        
        // Prepared statement to execute the query
        try (PreparedStatement queryItemAdd = DBConn.prepareStatement(addItem)) {

            // Set the values for the prepared statement
            queryItemAdd.setString(1, items.getName());
            queryItemAdd.setString(2, items.getDescription());
            queryItemAdd.setFloat(3, items.getPrice());
            queryItemAdd.setInt(4, items.getCategoryId());

            // Execute the update to insert the item into the database
            queryItemAdd.executeUpdate();
        }
    }

    /**
     * Adds a new category to the database.
     * 
     * @param categories    The Category object containing the details of the category to be added
     * @throws SQLException If a database access error occurs
     */
    public void addCategory(Category categories) throws SQLException {
        // SQL query to insert a new category
        String addCate = "INSERT INTO categories (heading, description) VALUES (?, ?)";

        try (PreparedStatement queryCategoryAdd = DBConn.prepareStatement(addCate)) {

            // Set the values for the prepared statement
            queryCategoryAdd.setString(1, categories.getName());
            queryCategoryAdd.setString(2, categories.getDescription());

            // Execute the update to insert the category into the database
            queryCategoryAdd.executeUpdate();
        }
    }

    /**
     * Updates an existing menu item in the database.
     * 
     * @param items          The Item object containing the updated details of the item
     * @throws SQLException If a database access error occurs
     */
    public void updateItem(Item items) throws SQLException {
        // SQL query to update an existing item
        String updateItem = "UPDATE items SET name = ?, description = ?, price = ?, category_id = ? WHERE item_id = ?";

        try (PreparedStatement queryItemEdit = DBConn.prepareStatement(updateItem)) {

            // Set the values for the prepared statement
            queryItemEdit.setString(1, items.getName());
            queryItemEdit.setString(2, items.getDescription());
            queryItemEdit.setFloat(3, items.getPrice());
            queryItemEdit.setInt(4, items.getCategoryId());
            queryItemEdit.setInt(5, items.getId());

            // Execute the update to modify the item in the database
            queryItemEdit.executeUpdate();
        }
    }

    /**
     * Deletes a menu item from the database.
     * 
     * @param item_id       The ID of the item to be deleted
     * @throws SQLException If a database access error occurs
     */
    public void deleteItem(int item_id) throws SQLException {
        // SQL query to delete an item by its ID
        String deleteItem = "DELETE FROM items WHERE item_id = ?";

        try (PreparedStatement queryItemDelete = DBConn.prepareStatement(deleteItem)) {

            // Set the item_id for the prepared statement
            queryItemDelete.setInt(1, item_id);

            // Execute the update to delete the item from the database
            queryItemDelete.executeUpdate();
        }
    }

    /**
     * Retrieves all menu items from the database.
     * 
     * @return              A list of all items in the database
     * @throws SQLException If a database access error occurs
     */
    public List<Item> getAllItems() throws SQLException {
        // SQL query to get all items ordered by category_id
        String getAllItems = "SELECT item_id, name, description, price, category_id FROM items ORDER BY category_id ASC";
        
        try (PreparedStatement queryItemEdit = DBConn.prepareStatement(getAllItems)) {
            ResultSet results = queryItemEdit.executeQuery();

            // List to store all items retrieved from the database
            List<Item> items = new ArrayList<>();
            try {
                // Process the results and populate the list of items
                while (results.next()) {
                    items.add(new Item(
                            results.getInt("item_id"),
                            results.getString("name"),
                            results.getString("description"),
                            results.getFloat("price"),
                            results.getInt("category_id")
                            ));
                }
            } finally {
                results.close(); // Ensure the ResultSet is closed
            }
            return items;
        }
    }

    /**
     * Updates an existing category in the database.
     * 
     * @param categories      The Category object containing the updated details of the category
     * @throws SQLException If a database access error occurs
     */
    public void updateCategory(Category categories) throws SQLException {
        // SQL query to update an existing category
        String updateCate = "UPDATE categories SET heading = ?, description = ? WHERE category_id = ?";

        try (PreparedStatement queryCategoryEdit = DBConn.prepareStatement(updateCate)) {

            // Set the values for the prepared statement
            queryCategoryEdit.setString(1, categories.getName());
            queryCategoryEdit.setString(2, categories.getDescription());
            queryCategoryEdit.setInt(3, categories.getId());

            // Execute the update to modify the category in the database
            queryCategoryEdit.executeUpdate();
        }
    }

    /**
     * Deletes a category from the database.
     * 
     * @param category_id   The ID of the category to be deleted
     * @throws SQLException If a database access error occurs
     */
    public void deleteCategory(int category_id) throws SQLException {
        // SQL query to delete a category by its ID
        String deleteCate = "DELETE FROM categories WHERE category_id = ?";

        try (PreparedStatement queryCategoryDelete = DBConn.prepareStatement(deleteCate)) {

            // Set the category_id for the prepared statement
            queryCategoryDelete.setInt(1, category_id);

            // Execute the update to delete the category from the database
            queryCategoryDelete.executeUpdate();
        }
    }

    /**
     * Retrieves all categories from the database.
     * 
     * @return              A list of all categories in the database
     * @throws SQLException If a database access error occurs
     */
    public List<Category> getAllCategories() throws SQLException {
        // SQL query to get all categories
        String getAllCate = "SELECT category_id, heading, description FROM categories";

        try (PreparedStatement queryCategoryList = DBConn.prepareStatement(getAllCate)) {
            ResultSet results = queryCategoryList.executeQuery();

            // List to store all categories retrieved from the database
            List<Category> categories = new ArrayList<>();
            try {
                // Process the results and populate the list of categories
                while (results.next()) {
                    categories.add(new Category(
                            results.getInt("category_id"),
                            results.getString("heading"),
                            results.getString("description")
                            ));
                }
            } finally {
                results.close(); // Ensure the ResultSet is closed
            }
            return categories;
        }
    }

    /**
     * Retrieves a category by its ID from the database.
     * 
     * @param category_id   The ID of the category to be retrieved
     * @return              The Category object if found, or null if not
     * @throws SQLException If a database access error occurs
     */
    public Category getCategoryById(int category_id) throws SQLException {
        // SQL query to get a category by its ID
        String getCate = "SELECT category_id, heading, description FROM categories WHERE category_id = ?";

        try (PreparedStatement queryCategoryHeading = DBConn.prepareStatement(getCate)) {
            queryCategoryHeading.setInt(1, category_id);

            // Execute the query and retrieve the result
            try (ResultSet results = queryCategoryHeading.executeQuery()) {
                if (results.next()) {
                    return new Category(
                            results.getInt("category_id"),
                            results.getString("heading"),
                            results.getString("description")
                            );
                }
            }
            return null; // Return null if no category is found
        }
    }

    /**
     * Retrieves an item by its ID from the database.
     * 
     * @param item_id       The ID of the item to be retrieved.
     * @return              The Item object if found, or null if not
     * @throws SQLException If a database access error occurs
     */
    public Item getItemById(int item_id) throws SQLException {
        // SQL query to get an item by its ID
        String getItem = "SELECT item_id, name, description, price, category_id FROM items WHERE item_id = ?";

        try (PreparedStatement queryItemId = DBConn.prepareStatement(getItem)) {
            queryItemId.setInt(1, item_id);

            // Execute the query and retrieve the result
            try (ResultSet results = queryItemId.executeQuery()) {
                if (results.next()) {
                    return new Item(
                            results.getInt("item_id"),
                            results.getString("name"),
                            results.getString("description"),
                            results.getFloat("price"),
                            results.getInt("category_id")
                            );
                }
            }
            return null; // Return null if no item is found
        }
    }
}
