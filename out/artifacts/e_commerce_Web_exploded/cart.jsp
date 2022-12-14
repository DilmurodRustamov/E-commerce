<%@ page import="connectionService.DbConnection" %>
<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.ProductDao" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    request.setAttribute("decimalFormat", decimalFormat);
    User auth = (User) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }

    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        cartProduct = productDao.getCartProducts(cart_list);
        Double total = productDao.getTotalCartPrice(cart_list);
        request.setAttribute("cart_list", cart_list);
        request.setAttribute("total", total);

    }

%>


<html>
<head>
    <title>Title</title>
    <%@include file="includes/head.jsp" %>
</head>
<body>
<%@include file="includes/navbar.jsp" %>
<style type="text/css">
    .table tbody td {
        vertical-align: middle;
    }

    .btn-incre, .btn-decre {
        box-shadow: none;
        font-size: 25px;
    }
</style>
<div class="container">
    <div class="d-flex py-3">
        <h3>Total price: $ ${(total>0)?decimalFormat.format(total):0}</h3>
        <a href="cart-check-out" class="mx-3 btn btn-primary">Check outs</a>
    </div>
    <table class="table table-lought">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Buy now</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (cart_list != null) {
                for (Cart c : cartProduct) {%>
        <tr>
            <td><%= c.getName()%>
            </td>
            <td><%= c.getCategory()%>
            </td>
            <td>$<%= c.getPrice()%>
            </td>
            <td>
                <form action="order-now" method="get" class="form-inline">
                    <input type="hidden" name="id" value="<%= c.getId()%>" class="form-input">
                    <div class="form-group d-flex justify-content-between w-50">
                        <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId()%>"><i
                                class="fa fa-minus-square"></i></a>
                        <input type="text" name="quantity" class="form-control w-50" value="<%= c.getQuantity()%>"
                               style="text-align: center" readonly>
                        <a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId()%>"><i
                                class="fa fa-plus-square"></i></a>
                    </div>
                    <button type="submit" class="btn btn-primary btn-sm ">Buy</button>
                </form>
            </td>
            <td><a href="remove-from-cart?id=<%= c.getId()%>" class="btn btn-sm btn-danger">Remove</a></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<%@include file="includes/footer.jsp" %>
</body>
</html>
