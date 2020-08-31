package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.ShoppingCart;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFromCartController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private ShoppingCartService shoppingCartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ShoppingCart cart = shoppingCartService.getByUserId(USER_ID);
        String productId = req.getParameter("id");
        String elementId = req.getParameter("row");

        shoppingCartService.deleteProductByRow(cart, productService.get(Long.valueOf(productId)),
                Long.valueOf(elementId));

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
