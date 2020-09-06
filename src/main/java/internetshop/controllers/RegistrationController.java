package internetshop.controllers;

import internetshop.exceptions.RegistrationException;
import internetshop.lib.Injector;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.security.RegistrationService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private RegistrationService regService = (RegistrationService) INJECTOR
            .getInstance(RegistrationService.class);

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

        try {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.of("USER"));
            User user = regService.register(name, login, password, confirmPassword, roles);

            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (RegistrationException e) {
            req.setAttribute("message", e.getMessage());
            req.setAttribute("name", name);
            req.setAttribute("login", login);

            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
