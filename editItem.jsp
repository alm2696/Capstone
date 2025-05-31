<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, capstone.Item, capstone.Category"%>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Item</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Edit Item</h1>

	<!-- Display the error message if one exists -->
	<%
		// Retrieve any error message set in the request scope
		String exception = (String) request.getAttribute("exception");
		
		// If an error message exists, display it in red color
		if (exception != null) {
	%>
	<div class="error" style="color: red;">
		<%=exception%> <!-- Display the error message dynamically -->
	</div>
	<%
		}
	%>

	<!-- Form to edit item details -->
	<form action="editItem" method="post">
		<%
			// Retrieve the item object from the request scope
			Item items = (Item) request.getAttribute("items");
		%>

		<!-- Hidden input for item ID to identify the item being edited -->
		<input type="hidden" name="item_id" value="<%=items.getId()%>" />

		<!-- Input field for the item name -->
		<label for="name">Name:</label>
		<input type="text" name="name" value="<%=items.getName()%>" required />

		<!-- Textarea for the item description -->
		<label for="description">Description:</label>
		<textarea name="description"><%=items.getDescription()%></textarea>

		<!-- Input field for the item price, allowing decimal values -->
		<label for="price">Price:</label>
		<input type="number" step="0.01" name="price" value="<%=items.getPrice()%>" required />

		<!-- Dropdown menu to select the category of the item -->
		<label for="category_id">Category:</label>
		<select name="category_id" required>
			<%
				// Retrieve the list of categories from the request scope
				List<Category> categories = (List<Category>) request.getAttribute("categories");
				for (Category category : categories) {
					// Set the selected category based on the item’s category ID
					String selected = (category.getId() == items.getCategoryId()) ? "selected" : "";
			%>
			<!-- Display the category options with the selected category pre-selected -->
			<option value="<%=category.getId()%>" <%=selected%>>
				<%=category.getName()%>
			</option>
			<%
				}
			%>
		</select>
		<br>

		<!-- Submit button to save the edited item details -->
		<input type="submit" value="Save Changes">
	</form>

	<!-- Link to navigate back to the Admin Menu -->
	<p class="link">
		<a href="adminMenu">Back to Admin Menu</a>
	</p>

</body>
</html>
