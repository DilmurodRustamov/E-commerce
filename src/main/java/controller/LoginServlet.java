package controller;

import connectionService.DbConnection;
import dto.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF=8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("login-email");
            String password = request.getParameter("login-password");
            UserDao userDao = new UserDao(DbConnection.getConnection());
            User user = userDao.userLogin(email, password);
            if (user != null) {
                request.getSession().setAttribute("auth",user);
                response.sendRedirect("index.jsp");
            } else {
                out.print("user login failed");
            }
        }
    }
}
