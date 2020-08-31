package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Order;
import internetshop.service.OrderService;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserOrderController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("uid");
        Long uid;
        if (userId == null) {
            uid = USER_ID;
        } else {
            uid = Long.valueOf(userId);
        }

        List<Order> userOrders = orderService.getUserOrders(uid);
        req.setAttribute("orders", userOrders);
        req.setAttribute("name", userService.get(uid).getName());

        req.getRequestDispatcher("/WEB-INF/views/orders/userOrders.jsp").forward(req, resp);
    }
}
