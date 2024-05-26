package com.ecommerce.control;

import in.sp.dbcall.ProductCrud;
import my.ecommerce.entity.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetail", value = "/product-detail")
public class ProductDetail extends HttpServlet {
    // Call DAO class to access with database.
    ProductCrud productDao = new ProductCrud();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the sending link from out of stock request or not.
        boolean alert = request.getParameter("invalid-quantity") != null;
        // Get the id of selected product.
        int id = Integer.parseInt(request.getParameter("id"));

        // Get product from database with the given id.
        Product product = productDao.getProduct(id);

        // Check number product available.
        String disabled = "";
        if (product.getAmount() <= 0) {
            disabled = "disabled";
        }

        // Get all products for feature section.
        List<Product> productList = productDao.getAllProducts();

        // Set attribute active class for home in header.
        String active = "active";

        request.setAttribute("alert", alert);
        request.setAttribute("disabled", disabled);
        request.setAttribute("shop_active", active);
        request.setAttribute("product", product);
        request.setAttribute("product_list", productList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product-detail.jsp");
        requestDispatcher.forward(request, response);
    }
}