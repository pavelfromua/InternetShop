package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private UserService userService = (UserService) INJECTOR
            .getInstance(UserService.class);
    private ShoppingCartService shoppingCartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("cpassword");

        boolean isChecked = true;

        if (!confirmPassword.equals(password)) {
            isChecked = false;
            req.setAttribute("messagePassword", "Unequal with the password");
        }

        if (password.isEmpty()) {
            isChecked = false;
            req.setAttribute("messagePassword", "Password can't be empty");
        }

        if (userService.isPresent(login)) {
            isChecked = false;
            req.setAttribute("messageLogin", "The login is already taken");
        }

        if (isChecked) {
            User user = new User(name, login, password);

            userService.create(user);
            shoppingCartService.create(new ShoppingCart(new ArrayList<Product>(), user.getId()));

            resp.sendRedirect(req.getContextPath() + "/users/all");
        } else {
            req.setAttribute("name", name);
            req.setAttribute("login", login);

            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
