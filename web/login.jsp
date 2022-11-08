<%@ page import="model.User" %>
<%@ page import="model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Dilmurod
  Date: 11/3/2022
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        response.sendRedirect("index.jsp");
    }
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
%>
<html>
<%@include file="includes/head.jsp" %>
<head>
    <title>Shopping card login</title>
</head>
<body>
<%@include file="includes/navbar.jsp" %>
<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">User login</div>
        <div class="card-body">
            <form action="user-login" method="post">
                <div class="form-group">
                    <label for="">Email Address</label>
                    <input type="email" class="form-control" name="login-email" placeholder="Enter your  name" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" name="login-password" placeholder="********" required>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp" %>
</body>

</html>
