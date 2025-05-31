package capstone;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for editing an item. This servlet handles GET requests to
 * display the item data for editing and POST requests to update the item details.
 * 
 * @author angel
 */
@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles the GET request to display the item data for editing.
     * 
     * @param request           the HttpServletRequest object that contains the request made by the client to the servlet
     * @param response          the HttpServletResponse object that contains the response returned to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	// Retrieve and validate the input parameters
        	String itemIdStr = request.getParameter("item_id");
        	
            // Retrieve the item ID from the request parameters
        	int itemId = Integer.parseInt(itemIdStr);
            
            // Get the items and categories from the model
            RestaurantModel model = RestaurantModel.getInstance();
            Item items = model.getItemById(itemId);
            List<Category> categories = model.getAllCategories();

            // Set the items and categories as request attributes for the JSP
            request.setAttribute("items", items);
            request.setAttribute("categories", categories);

            // Forward the request to the editItem.jsp page
            request.getRequestDispatcher("/WEB-INF/jsp/editItem.jsp").forward(request, response);

        } catch (Exception exception) {
            // Handle any exceptions and forward to the error page
            request.setAttribute("exception", exception);
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the POST request to update the item details.
     * 
     * @param request           the HttpServletRequest object that contains the request made by the client to the servlet
     * @param response          the HttpServletResponse object that contains the response returned to the client
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve and validate the input parameters
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("category_id");
            String itemIdStr = request.getParameter("item_id");

            // Ensure that name, description, and price are provided
            if (name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Name, description, and price cannot be empty.");
            }

            // Parse price and category ID
            float price = Float.parseFloat(priceStr.trim());
            int categoryId = Integer.parseInt(categoryIdStr.trim());
            int itemId = Integer.parseInt(itemIdStr.trim());

            // Ensure price is a positive number
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be a positive number.");
            }

            // Create a new Item object with the updated details
            Item item = new Item(itemId, name, description, price, categoryId);

            // Get the model and update the item
            RestaurantModel model = RestaurantModel.getInstance();
            model.updateItem(item);

            // Redirect to the admin menu after successful update
            response.sendRedirect("adminMenu");

        } catch (NumberFormatException exception) {
            // Handle invalid number format for price or category ID
            request.setAttribute("exception", "Invalid input. Please ensure price and category ID are valid numbers.");
            request.getRequestDispatcher("/WEB-INF/jsp/editItem.jsp").forward(request, response);

        } catch (IllegalArgumentException exception) {
            // Handle invalid arguments, like missing fields or invalid price
            request.setAttribute("exception", exception.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/editItem.jsp").forward(request, response);

        } catch (Exception exception) {
            // Handle other exceptions and forward to the error page
            request.setAttribute("exception", exception);
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
