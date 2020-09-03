package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        Product product = productService.get(Long.valueOf(id));

        Long userId = (Long) req.getSession().getAttribute("userId");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);

        shoppingCartService.addProduct(shoppingCart, product);

        resp.sendRedirect(req.getContextPath() + "/products/catalog?id=" + id);
    }
}
