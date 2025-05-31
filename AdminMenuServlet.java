package capstone;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminMenuServlet. Handles the display of
 * the admin menu, which shows all items and categories in the restaurant.
 * 
 * @author angel
 */
@WebServlet("/adminMenu")
public class AdminMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the GET request to display the admin menu page. Retrieves the
	 * list of all menu items and categories to be displayed on the admin menu.
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

			// Retrieve all menu items and categories from the model
			List<Item> items = model.getAllItems();
			List<Category> categories = model.getAllCategories();

			// Set the list of items and categories as request attributes
			request.setAttribute("items", items);
			request.setAttribute("categories", categories);

			// Forward the request to the admin menu JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/adminMenu.jsp");
			dispatcher.forward(request, response);

		} catch (Exception exception) {
			// If an exception occurs, set it as an attribute and forward to the error page
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
