<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List, capstone.Item, capstone.Category"%>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Menu Management</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h1>Admin Menu Management</h1>

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

	<h2>Menu Items</h2>
	<table>
		<tr>
			<th>Item Name</th>
			<th>Description</th>
			<th>Price</th>
			<th>Category</th>
			<th>Actions</th>
		</tr>
		<%
			// Retrieve the list of items from the request
			List<Item> items = (List<Item>) request.getAttribute("items");

			// If items are available, loop through each item to display its details
			if (items != null) {
				for (Item item : items) {
		%>
		<tr>
			<!-- Display item details -->
			<td><%=item.getName()%></td>
			<td><%=item.getDescription()%></td>
			<td><%=String.format("%.2f", item.getPrice())%></td>
			<td><%=item.getCategoryId()%></td>
			<td>
				<!-- Edit item form -->
				<form action="editItem" method="GET" style="display: inline;">
					<input type="hidden" name="item_id" value="<%=item.getId()%>">
					<button type="submit">Edit</button>
				</form>

				<!-- Delete item form -->
				<form action="deleteItem" method="POST" style="display: inline;">
					<input type="hidden" name="item_id" value="<%=item.getId()%>">
					<button type="submit">Delete</button>
				</form>
			</td>
		</tr>
		<%
				}
			}
		%>
	</table>

	<!-- Link to add a new item -->
	<p class="link">
		<a href="addItem">Add New Item</a>
	</p>

	<h2>Categories</h2>
	<table>
		<thead>
			<tr>
				<th>Heading</th>
				<th>Description</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<%
				// Retrieve the list of categories from the request
				List<Category> categories = (List<Category>) request.getAttribute("categories");

				// If categories are available, loop through each category to display its details
				if (categories != null) {
					for (Category category : categories) {
			%>
			<tr>
				<!-- Display category details -->
				<td><%=category.getName()%></td>
				<td><%=category.getDescription()%></td>
				<td>
					<!-- Edit category form -->
					<form action="editCategory" method="GET" style="display: inline;">
						<input type="hidden" name="category_id" value="<%=category.getId()%>">
						<button type="submit">Edit</button>
					</form>

					<!-- Delete category form -->
					<form action="deleteCategory" method="POST" style="display: inline;">
						<input type="hidden" name="category_id" value="<%=category.getId()%>">
						<button type="submit">Delete</button>
					</form>
				</td>
			</tr>
			<%
					}
				}
			%>
		</tbody>
	</table>

	<!-- Link to add a new category -->
	<p class="link">
		<a href="addCategory">Add New Category</a>
	</p>

	<!-- Link to navigate back to the home page -->
	<p class="link">
		<a href="index.html">Back to Home</a>
	</p>

</body>
</html>
