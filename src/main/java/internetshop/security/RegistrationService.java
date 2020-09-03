package internetshop.security;

import internetshop.exceptions.RegistrationException;
import internetshop.model.User;

public interface RegistrationService {
    User register(String name, String login, String password, String cpassword)
            throws RegistrationException;
}
