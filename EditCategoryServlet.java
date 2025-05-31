package capstone;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for editing a category. This servlet handles GET requests
 * to display the category data and POST requests to update the category details.
 * 
 * @author angel
 */
@WebServlet("/editCategory")
public class EditCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the GET request to display the category data for editing.
	 * 
	 * @param request           the HttpServletRequest object that contains the request made by the client to the servlet
	 * @param response          the HttpServletResponse object that contains the response returned to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error occurs while the servlet is handling the request
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Parse category ID from request parameters
			int categoryId = Integer.parseInt(request.getParameter("category_id"));

			// Get the categories from the model using the category ID
			RestaurantModel model = RestaurantModel.getInstance();
			Category categories = model.getCategoryById(categoryId);

			// If category not found, throw an exception
			if (categories == null) {
				throw new IllegalArgumentException("Category not found.");
			}

			// Set the categories in the request attributes and forward to the edit page
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/WEB-INF/jsp/editCategory.jsp").forward(request, response);

		} catch (IllegalArgumentException exception) {
			// Handle category not found or invalid input
			request.setAttribute("exception", exception.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/editCategory.jsp").forward(request, response);

		} catch (Exception exception) {
			// Handle any other exceptions and forward to the error page
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handles the POST request to update the category details.
	 * 
	 * @param request           the HttpServletRequest object that contains the request made by the client to the servlet
	 * @param response          the HttpServletResponse object that contains the response returned to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error occurs while the servlet is handling the request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Retrieve and validate the category ID
			String categoryIdStr = request.getParameter("category_id");

			if (categoryIdStr == null || categoryIdStr.isEmpty()) {
				throw new IllegalArgumentException("Category ID is required.");
			}

			int categoryId = Integer.parseInt(categoryIdStr.trim());

			// Retrieve and validate the category heading and description
			String heading = request.getParameter("heading");
			String description = request.getParameter("description");

			if (heading == null || heading.isEmpty() || description == null || description.isEmpty()) {
				throw new IllegalArgumentException("Both heading and description are required.");
			}

			// Create a new Category object with the provided data
			Category category = new Category(categoryId, heading, description);

			// Get the model and update the category
			RestaurantModel model = RestaurantModel.getInstance();
			model.updateCategory(category);

			// Redirect to the admin menu after successful update
			response.sendRedirect("adminMenu");

		} catch (IllegalArgumentException exception) {
			// Handle input validation errors and forward to the edit category page with error message
			request.setAttribute("exception", exception.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/editCategory.jsp").forward(request, response);

		} catch (Exception exception) {
			// Handle any other exceptions and forward to the error page
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
