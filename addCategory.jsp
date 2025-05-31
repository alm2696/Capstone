<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList"%>
<html>
<head>
<meta charset="UTF-8">
<title>Add Category</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Add New Category</h1>

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

	<!-- Form to add a new category -->
	<form action="addCategory" method="POST">
		<!-- Category name input field -->
		<label for="heading">Category Name:</label><br>
		<input type="text" id="heading" name="heading" required><br><br>

		<!-- Category description input field -->
		<label for="description">Description:</label><br>
		<textarea id="description" name="description" required></textarea>
		<br>

		<!-- Submit button to insert the new category -->
		<input type="submit" value="Insert"><br>
	</form>

	<!-- Link to navigate back to the Admin Menu -->
	<p class="link">
		<a href="adminMenu">Back to Admin Menu</a>
	</p>
	
</body>
</html>
