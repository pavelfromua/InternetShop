package internetshop.security;

import internetshop.exceptions.RegistrationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Product;
import internetshop.model.Role;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Inject
    private UserService userService;

    @Inject
    private ShoppingCartService cartService;

    @Override
    public User register(String name, String login, String password, String cpassword,
                         Set<Role> roles)
            throws RegistrationException {
        if (login.isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }

        if (password.isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }

        if (!cpassword.equals(password)) {
            throw new RegistrationException("Confirm password unequal to the password");
        }

        Optional<User> userOptional = userService.getByLogin(login);
        if (userOptional.isPresent()) {
            throw new RegistrationException("Login is already taken");
        }

        User user = new User(name, login, password, roles);

        userService.create(user);
        cartService.create(new ShoppingCart(new ArrayList<Product>(), user.getId()));

        return user;
    }
}
