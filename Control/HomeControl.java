package com.ecommerce.control;

import in.sp.dbcall.CategoryCrud;
import in.sp.dbcall.ProductCrud;
import my.ecommerce.entity.Category;
import my.ecommerce.entity.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeControl", value = "")
public class HomeControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();
    CategoryCrud categoryDao = new CategoryCrud();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get all products from database.
        List<Product> productList = productDao.getAllProducts();
        // Get all categories from database.
        List<Category> categoryList = categoryDao.getAllCategories();


        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        // Set attribute active class for home in header.
        request.setAttribute("home_active", "active");
        // Get request dispatcher and render to index page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}
