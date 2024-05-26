package com.ecommerce.control;

import in.sp.dbcall.CategoryCrud;
import in.sp.dbcall.ProductCrud;
import my.ecommerce.entity.Category;
import my.ecommerce.entity.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryControl", value = "/category")
public class CategoryControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();
    CategoryCrud categoryDao = new CategoryCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the id of the selected category.
        int category_id = Integer.parseInt(request.getParameter("category_id"));

        // Get all products with the given category_id.
        List<Product> productList = productDao.getAllCategoryProducts(category_id);
        // Get all categories from database.
        List<Category> categoryList = categoryDao.getAllCategories();

        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("shop.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
