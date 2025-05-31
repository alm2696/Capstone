<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, capstone.Category"%>
<html>
<head>
<meta charset="UTF-8">
<title>Add Item</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Add New Item</h1>

	<!-- Display the error message if one exists -->
	<%
		// Retrieve any error message set in the request scope
		String exception = (String) request.getAttribute("exception");
		
		// If an error message exists, display it
		if (exception != null) {
	%>
	<div class="error" style="color: red;">
		<%=exception%> <!-- Display the error message dynamically -->
	</div>
	<%
		}
	%>

	<!-- Form to add a new item -->
	<form action="addItem" method="POST">
		<!-- Item name input field -->
		<label for="name">Item Name:</label><br>
		<input type="text" id="name" name="name" required><br><br>

		<!-- Item description input field -->
		<label for="description">Description:</label><br>
		<textarea id="description" name="description" required></textarea>
		<br><br>

		<!-- Item price input field -->
		<label for="price">Price:</label><br>
		<input type="number" id="price" name="price" required><br><br>

		<!-- Category selection dropdown -->
		<label for="category_id">Category:</label><br>
		<select id="category_id" name="category_id" required>
			<%
				// Retrieve the list of categories from the request
				List<Category> categories = (List<Category>) request.getAttribute("categories");

				// Loop through each category and create an option in the dropdown
				for (Category category : categories) {
			%>
			<option value="<%=category.getId()%>"><%=category.getName()%></option>
			<%
				}
			%>
		</select><br><br>

		<!-- Submit button to insert the new item -->
		<input type="submit" value="Insert">
	</form>

	<!-- Link to navigate back to the Admin Menu -->
	<p class="link">
		<a href="adminMenu">Back to Admin Menu</a>
	</p>
	
</body>
</html>
