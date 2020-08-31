package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Order;
import internetshop.model.ShoppingCart;
import internetshop.service.OrderService;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddOrderController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private ShoppingCartService shoppingCartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);
    private OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ShoppingCart cart = shoppingCartService.getByUserId(USER_ID);
        Order order = new Order(cart.getProducts(), cart.getUserId());
        orderService.create(order);
        shoppingCartService.clear(cart);

        resp.sendRedirect(req.getContextPath() + "/orders/userorders?uid=" + USER_ID);
    }
}
