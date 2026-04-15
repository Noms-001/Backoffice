<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Test DB</title>
</head>
<body>

<h1>Spring Boot + PostgreSQL</h1>

<%
    String dbStatus = (String) request.getAttribute("dbStatus");
%>

<p><%= dbStatus %></p>

</body>
</html>