<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, capstone.Category"%>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Category</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Edit Category</h1>

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

	<!-- Form to edit category -->
	<form action="editCategory" method="POST">
		<%
			// Retrieve the category object from the request scope
			Category categories = (Category) request.getAttribute("categories");
		%>

		<!-- Hidden input to store the category ID for updating -->
		<input type="hidden" name="category_id" value="<%=categories.getId()%>">

		<!-- Input for category name -->
		<label for="heading">Category Name:</label><br>
		<input type="text" id="heading" name="heading" value="<%=categories.getName()%>" required><br> <br>

		<!-- Input for category description -->
		<label for="description">Description:</label><br>
		<input type="text" id="description" name="description" value="<%=categories.getDescription()%>" required><br> <br>

		<!-- Submit button to save the changes -->
		<input type="submit" value="Save Changes">
	</form>

	<!-- Link to return to the admin menu -->
	<p class="link">
		<a href="adminMenu">Back to Admin Menu</a>
	</p>

</body>
</html>
