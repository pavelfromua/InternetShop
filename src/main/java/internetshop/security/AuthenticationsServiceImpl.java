package internetshop.security;

import internetshop.exceptions.AuthenticationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;
import internetshop.util.HashUtil;

@Service
public class AuthenticationsServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    public boolean isPasswordValid(User user, String password) {
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return true;
        }

        return false;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.getByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect login or password"));

        if (isPasswordValid(user, password)) {
            return user;
        }

        throw new AuthenticationException("Incorrect login or password");
    }
}
