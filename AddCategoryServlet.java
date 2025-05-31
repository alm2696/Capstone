package capstone;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddCategoryServlet. Handles
 * requests for adding a new category to the restaurant menu.
 * 
 * @author angel
 */
@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the GET request to display the form for adding a new category.
	 * 
	 * @param request           the HttpServletRequest object
	 * @param response          the HttpServletResponse object
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an input/output error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forward the request to the JSP page for displaying the add category form
		request.getRequestDispatcher("/WEB-INF/jsp/addCategory.jsp").forward(request, response);
	}

	/**
	 * Handles the POST request to process the form submission for adding a new category.
	 * 
	 * @param request           the HttpServletRequest object
	 * @param response          the HttpServletResponse object
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an input/output error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Retrieve the category heading and description from the form
			String heading = request.getParameter("heading");
			String description = request.getParameter("description");

			// Validate the category heading to ensure it's not empty
			if (heading == null || heading.trim().isEmpty()) {
				throw new IllegalArgumentException("Category name cannot be empty.");
			}

			// Create a new Category object with the provided heading and description
			Category category = new Category(heading, description);

			// Get the singleton instance of the RestaurantModel
			RestaurantModel model = RestaurantModel.getInstance();

			// Add the new category to the restaurant model
			model.addCategory(category);

			// Redirect to the admin menu page after successful addition
			response.sendRedirect("adminMenu");

		} catch (Exception exception) {
			// If any exception occurs, set it as an attribute and forward to the error page
			request.setAttribute("exception", exception);

			// Forward the request to the custom error page
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
