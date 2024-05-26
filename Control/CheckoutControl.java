package com.ecommerce.control;

import in.sp.dbcall.AccountCrud;
import in.sp.dbcall.OrderCrud;
import my.ecommerce.entity.Account;
import my.ecommerce.entity.Order;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CheckoutControl", value = "/checkout")
public class CheckoutControl extends HttpServlet {
    // Call DAO class to access with database.
    OrderCrud orderDao = new OrderCrud();
    AccountCrud accountDao = new AccountCrud();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Get information from input field.
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
        }
        else {
            double totalPrice = (double) session.getAttribute("total_price");
            Order order = (Order) session.getAttribute("order");
            Account account = (Account) session.getAttribute("account");

            // Insert information to account.
            int accountId = account.getId();
            accountDao.updateProfileInformation(accountId, firstName, lastName, address, email, phone);
            // Insert order to database.
            orderDao.createOrder(account.getId(), totalPrice, order.getCartProducts());
            session.removeAttribute("order");
            session.removeAttribute("total_price");

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("thankyou.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
