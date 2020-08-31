package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.OrderService;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
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
        User admin = new User("Admin", "admin", "123");
        User customer = new User("Customer", "customer", "111");

        userService.create(admin);
        userService.create(customer);

        Product xiaomiMi8 = new Product("Mi 8", 4000);
        Product xiaomiMi9 = new Product("Mi 9", 7000);

        productService.create(xiaomiMi8);
        productService.create(xiaomiMi9);

        shoppingCartService.create(new ShoppingCart(new ArrayList<Product>(), admin.getId()));
        shoppingCartService.create(new ShoppingCart(new ArrayList<Product>(), customer.getId()));

        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
