<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,capstone.Item,capstone.Category"%>
<html>
<head>
<meta charset="UTF-8">
<title>Restaurant Menu</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Our Menu</h1>

	<%-- Display the error message if one exists --%>
	<%
		// Retrieve any error message from the request attributes and display it if it exists
		String exception = (String) request.getAttribute("exception");
		if (exception != null) {
	%>
	<div class="error" style="color: red;">
		<%= exception %>
	</div>
	<%
		}
	%>

	<%
		// Retrieve the list of categories and items from the request attributes
		List<Category> categories = (List<Category>) request.getAttribute("categories");
		List<Item> items = (List<Item>) request.getAttribute("items");

		// Check if categories and items are available
		if (categories != null && items != null) {
			// Loop through each category to display menu items under it
			for (Category category : categories) {
	%>
	<h2><%= category.getName() %></h2> <!-- Display the category name -->
	<ol>
		<%
			// Loop through all items and display the ones belonging to the current category
			for (Item item : items) {
				if (item.getCategoryId() == category.getId()) {
		%>
		<li>
			<strong><%= item.getName() %></strong><br> <!-- Display the item name -->
			<%= item.getDescription() %><br>           <!-- Display the item description -->
			$<%= item.getPrice() %>                    <!-- Display the item price -->
		</li>
		<%
				}
			}
		%>
	</ol>
	<%
			}
		}
	%>

	<p class="link">
		<a href="index.html">Back to Home</a> <!-- Link back to the homepage -->
	</p>

</body>
</html>
