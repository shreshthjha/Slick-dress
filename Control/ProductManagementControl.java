package com.ecommerce.control;

import in.sp.dbcall.CategoryCrud;
import in.sp.dbcall.ProductCrud;
import my.ecommerce.entity.Account;
import my.ecommerce.entity.Category;
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

@WebServlet(name = "ProductManagementControl", value = "/product-management")
public class ProductManagementControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();
    CategoryCrud categoryDao = new CategoryCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the seller id from session.
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        int sellerId = account.getId();
        // Get products of seller from database.
        List<Product> productList = productDao.getSellerProducts(sellerId);
        // Get all category for category selection.
        List<Category> categoryList = categoryDao.getAllCategories();

        request.setAttribute("category_list", categoryList);
        request.setAttribute("product_list", productList);
        // Set attribute active status for product management tab in header.
        request.setAttribute("product_management_active", "active");
        // Get request dispatcher and render to product-management page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product-management.jsp");
        requestDispatcher.forward(request, response);
    }
}