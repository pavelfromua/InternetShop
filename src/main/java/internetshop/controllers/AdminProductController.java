package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> catalog = productService.getAll();
        req.setAttribute("catalog", catalog);

        req.getRequestDispatcher("/WEB-INF/views/products/admin/catalog.jsp").forward(req, resp);
    }
}

