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

@WebServlet(name = "ShopControl", value = "/shop")
public class ShopControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();
    CategoryCrud categoryDao = new CategoryCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get page number from request.
        String index = request.getParameter("index");
        if (index == null) {
            index = "1";
        }

        // Get 12 products from database to display on each page.
        List<Product> productList = productDao.get12ProductsOfPage(Integer.parseInt(index));

        // Get all categories from database.
        List<Category> categoryList = categoryDao.getAllCategories();

        // Get total products to count pages.
        int totalProduct = productDao.getTotalNumberOfProducts();
        int totalPages = totalProduct / 12;
        if (totalProduct % 12 != 0) {
            totalPages++;
        }

        // Set attribute active class for home in header and page number.
        String active = "active";

        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        request.setAttribute("total_pages", totalPages);
        request.setAttribute("shop_active", active);
        request.setAttribute("page_active", index);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("shop.jsp");
        requestDispatcher.forward(request, response);
    }
}