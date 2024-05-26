package com.ecommerce.control;

import in.sp.dbcall.AccountCrud;
import in.sp.dbcall.OrderCrud;
import in.sp.dbcall.ProductCrud;
import my.ecommerce.entity.Account;
import my.ecommerce.entity.CartProduct;
import my.ecommerce.entity.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderManagementControl", value = "/order-management")
public class OrderManagementControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();
    OrderCrud orderDao = new OrderCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get account id from session.
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        int accountId = account.getId();

        // Get product list of seller.
        List<Product> productList = productDao.getSellerProducts(accountId);
        List<CartProduct> cartProductList = null;
        for (Product product : productList) {
            if (cartProductList == null) {
                cartProductList = orderDao.getSellerOrderDetail(product.getId());
            } else {
                cartProductList.addAll(orderDao.getSellerOrderDetail(product.getId()));
            }
        }

        request.setAttribute("order_detail_list", cartProductList);
        // Set attribute active for order management tab.
        request.setAttribute("order_management_active", "active");
        // Get request dispatcher and render to order-management page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("order-management.jsp");
        requestDispatcher.forward(request, response);
    }
}