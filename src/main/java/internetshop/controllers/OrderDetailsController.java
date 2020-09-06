package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderDetailsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");

    private OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null) {
            List<Product> products = orderService.get(Long.valueOf(id)).getProducts();
            req.setAttribute("orderId", id);
            req.setAttribute("products", products);
        }

        req.getRequestDispatcher("/WEB-INF/views/orders/orderDetails.jsp").forward(req, resp);
    }
}
