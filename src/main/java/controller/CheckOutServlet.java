package controller;

import connectionService.DbConnection;
import dto.OrderDao;
import model.Cart;
import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {

    private static final long serialVersionUID = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF=8");
        try (PrintWriter out = response.getWriter()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
            User auth = (User) request.getSession().getAttribute("auth");

            if (cart_list != null && auth != null) {
                for (Cart cart : cart_list) {
                    Order order = new Order();
                    order.setId(cart.getId());
                    order.setUid(auth.getId());
                    order.setQuantity(cart.getQuantity());
                    order.setDate(simpleDateFormat.format(date));

                    OrderDao orderDao = new OrderDao(DbConnection.getConnection());
                    boolean result = orderDao.insertOrder(order);
                    if (!result) break;
                }

                cart_list.clear();
                response.sendRedirect("orders.jsp");
            } else {
                if (auth == null) {
                    response.sendRedirect("login.jsp");
                    response.sendRedirect("card.jsp");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
