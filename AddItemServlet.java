package capstone;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddItemServlet. Handles
 * requests for adding a new item to the restaurant menu.
 * 
 * @author angel
 */
@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the GET request to display the form for adding a new menu item.
	 * Populates the list of categories to allow the user to assign the item to a category.
	 * 
	 * @param request           the HttpServletRequest object
	 * @param response          the HttpServletResponse object
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an input/output error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get the singleton instance of RestaurantModel
			RestaurantModel model = RestaurantModel.getInstance();
			
			// Retrieve all categories to display in the form
			List<Category> categories = model.getAllCategories();

			// Set the list of categories as a request attribute
			request.setAttribute("categories", categories);

			// Forward the request to the JSP page for displaying the add item form
			request.getRequestDispatcher("/WEB-INF/jsp/addItem.jsp").forward(request, response);

		} catch (Exception exception) {
			// If an exception occurs, set it as an attribute and forward to the error page
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handles the POST request to process the form submission for adding a new menu item.
	 * Validates input data and handles potential errors such as invalid price or missing fields.
	 * 
	 * @param request           the HttpServletRequest object
	 * @param response          the HttpServletResponse object
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an input/output error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Retrieve the item details from the form
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String priceStr = request.getParameter("price");
			String categoryIdStr = request.getParameter("category_id");

			// Validate that name, description, and price are not empty
			if (name == null || name.trim().isEmpty() || description == null || description.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
				throw new IllegalArgumentException("Name, description, and price cannot be empty.");
			}

			// Convert price and category ID from strings to appropriate types
			float price = Float.parseFloat(priceStr.trim());
			int categoryId = Integer.parseInt(categoryIdStr.trim());

			// Validate that price is a positive number
			if (price <= 0) {
				throw new IllegalArgumentException("Price must be a positive number.");
			}

			// Create a new Item object with the provided details
			Item items = new Item(name, description, price, categoryId);

			// Get the singleton instance of the RestaurantModel
			RestaurantModel model = RestaurantModel.getInstance();

			// Add the new item to the restaurant model
			model.addItem(items);

			// Redirect to the admin menu page after successful addition
			response.sendRedirect("adminMenu");

		} catch (NumberFormatException exception) {
			// Handle case where the price input is not a valid number
			request.setAttribute("exception", "Invalid price format. Please enter a valid whole number for price.");
			request.getRequestDispatcher("/WEB-INF/jsp/addItem.jsp").forward(request, response);

		} catch (IllegalArgumentException exception) {
			// Handle any validation exceptions, such as missing fields or invalid price
			request.setAttribute("exception", exception.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/addItem.jsp").forward(request, response);

		} catch (Exception exception) {
			// Catch any other exceptions and forward to the custom error page
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
