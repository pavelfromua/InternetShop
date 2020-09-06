package internetshop.security;

import internetshop.exceptions.AuthenticationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

@Service
public class AuthenticationsServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.getByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect login or password"));

        if (user.getPassword().equals(password)) {
            return user;
        }

        throw new AuthenticationException("Incorrect login or password");
    }
}
