package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.service.OrderService;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayAllDbController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private ProductService productService = (ProductService) INJECTOR
            .getInstance(ProductService.class);
    private UserService userService = (UserService) INJECTOR
            .getInstance(UserService.class);
    private ShoppingCartService shoppingCartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, List> tables = new HashMap<>();
        tables.put("Products", productService.getAll());
        tables.put("Users", userService.getAll());
        tables.put("ShoppingCarts", shoppingCartService.getAll());
        tables.put("Orders", orderService.getAll());

        req.setAttribute("tables", tables);
        req.getRequestDispatcher("WEB-INF/views/displaydb.jsp").forward(req, resp);
    }
}
