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
 * Servlet for displaying the restaurant menu, including all categories and items.
 * Handles requests to view the menu and forwards the data to the menu.jsp page.
 * 
 * @author angel
 */
@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the menu. Retrieves categories
     * and items from the model and forwards them to the menu.jsp page.
     * 
     * @param request           The HTTP request
     * @param response          The HTTP response
     * @throws ServletException If an error occurs during servlet processing
     * @throws IOException      If an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the model instance to retrieve data
            RestaurantModel model = RestaurantModel.getInstance();
            
            // Fetch all categories and items from the model
            List<Category> categories = model.getAllCategories();
            List<Item> items = model.getAllItems();

            // Set attributes to be used in the JSP page
            request.setAttribute("categories", categories);
            request.setAttribute("items", items);

            // Forward the request to the JSP page for displaying the menu
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp");
            dispatcher.forward(request, response);

        } catch (Exception exception) {
            // Handle exceptions and forward to the error page with an error message
            request.setAttribute("exception", "An error occurred: " + exception.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
