<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>index</title>
    <link href="./refs/booststrap.html">
    <link href="./refs/jquery.html">
</head>
<body>
<header>
    <%
        String path = request.getRequestURL() + "pm/";
        path = path.replace("http://localhost:8080", "");
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    %>
</header>
</body>
</html>