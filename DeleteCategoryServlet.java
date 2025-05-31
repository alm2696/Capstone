package capstone;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for deleting a category. This servlet handles
 * the POST request to delete a category from the restaurant menu.
 * 
 * @author angel
 */
@WebServlet("/deleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the POST request to delete a category based on the provided category ID.
	 * 
	 * @param request           the HttpServletRequest object that contains the request the client made to the servlet
	 * @param response          the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error occurs while the servlet is handling the request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Parse the category ID from the request parameters
			int id = Integer.parseInt(request.getParameter("category_id"));

			// Get the instance of the restaurant model
			RestaurantModel model = RestaurantModel.getInstance();

			// Delete the category from the model
			model.deleteCategory(id);

			// Redirect back to the admin menu after successful deletion
			response.sendRedirect("adminMenu");

		} catch (Exception exception) {
			// If any exception occurs, forward to the error page with the exception details
			request.setAttribute("exception", exception);
			request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}
