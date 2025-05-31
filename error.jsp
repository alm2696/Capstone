<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

	<%-- Declare variables that can be used throughout the page --%>
	<%! Exception exception; %> <!-- Declare a variable to hold the exception object -->

	<%-- Retrieve the exception from the request attribute --%>
	<%
		// Get the exception object from the request scope
		exception = (Exception) request.getAttribute("exception");
	%>

	<h1>Error Page</h1>

	<h2>
		<!-- Display the error message -->
		Error Message:
		<%= exception.getMessage() %> <!-- Output the exception message to inform the user -->
	</h2>

	<pre>
		<%-- Print the stack trace to the web page --%>
		<%
			// Output the full stack trace to assist with debugging
			exception.printStackTrace(new PrintWriter(out));
		%>
	</pre>

</body>
</html>
