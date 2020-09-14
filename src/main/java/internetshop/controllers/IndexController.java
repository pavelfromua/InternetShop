package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IndexController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private UserService userService = (UserService) INJECTOR
            .getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        req.setAttribute("roles", new HashSet<Role>());
        User user = userService.get(userId);
        if (user != null) {
            req.setAttribute("roles", user.getRoles());
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
