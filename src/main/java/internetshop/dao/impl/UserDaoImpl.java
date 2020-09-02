package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.ShoppingCartDao;
import internetshop.dao.UserDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

@Dao
public class UserDaoImpl implements UserDao {
    static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartDao cartDao;

    @Override
    public User create(User user) {
        Storage.addUser(user);

        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users.stream().collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        Storage.users.stream().filter(u -> u.getId() == user.getId()).forEach(u -> {
            u.setName(user.getName());
            u.setLogin(user.getLogin());
            u.setPassword(user.getPassword());
        });

        try {
            User obj = get(user.getId()).get();
            if (obj == null) {
                logger.info("User by " + user.getId()
                        + "isn't gotten from db");
                return user;
            } else {
                logger.info(obj + " is cloned");
                return obj.clone();
            }
        } catch (CloneNotSupportedException e) {
            logger.error("This is error : " + e.getMessage());
            return user;
        }
    }

    @Override
    public boolean delete(Long id) {
        orderDao.getUserOrders(id).forEach(o -> orderDao.delete(o.getId()));

        Optional<ShoppingCart> optionalShoppingCart = cartDao.getByUserId(id);
        if (optionalShoppingCart.isPresent()) {
            ShoppingCart cart = cartDao.getByUserId(id).get();
            cartDao.delete(cart.getId());
        }

        return Storage.users.removeIf(u -> u.getId() == id);
    }

    @Override
    public boolean isPresent(String login) {
        long count = Storage.users.stream().filter(u -> u.getLogin().equals(login)).count();
        return count == 0 ? false : true;
    }
}
