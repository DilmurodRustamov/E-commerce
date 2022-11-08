<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="ISO-8859-1" %>
<%@ page import="connectionService.DbConnection" %>
<%@ page import="model.User" %>
<%@ page import="model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
//        response.sendRedirect("index.jsp");
    }
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<html>
<head>
    <title>Orders page</title>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<%@include file="includes/navbar.jsp" %>

<%@include file="includes/footer.jsp" %>
</body>
</html>
