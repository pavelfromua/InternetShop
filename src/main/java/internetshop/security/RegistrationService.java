package internetshop.security;

import internetshop.exceptions.RegistrationException;
import internetshop.model.Role;
import internetshop.model.User;
import java.util.Set;

public interface RegistrationService {
    User register(String name, String login, String password, String cpassword,
                  Set<Role> roles)
            throws RegistrationException;
}
