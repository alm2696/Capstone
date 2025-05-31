package capstone;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for deleting an item from the restaurant menu. This 
 * servlet handles the POST request to delete an item based on the provided item ID.
 * 
 * @author angel
 */
@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the POST request to delete an item based on the provided item ID.
	 * 
	 * @param request           the HttpServletRequest object that contains the request made by the client to the servlet
	 * @param response          the HttpServletResponse object that contains the response returned to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error occurs while the servlet is handling the request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Parse the item ID from the request parameters
			int id = Integer.parseInt(request.getParameter("item_id"));

			// Get the instance of the restaurant model
			RestaurantModel model = RestaurantModel.getInstance();

			// Delete the item from the model
			model.deleteItem(id);

			// Redirect back to the admin menu after successful deletion
			response.sendRedirect("adminMenu");

		} catch (NumberFormatException exception) {
			// Handle invalid item ID format and forward the error message
			request.setAttribute("exception", "Invalid item ID.");
			request.getRequestDispatcher("/WEB-INF/jsp/adminMenu.jsp").forward(request, response);

		} catch (Exception exception) {
			// If any other exception occurs, forward to the error page with the exception details
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
