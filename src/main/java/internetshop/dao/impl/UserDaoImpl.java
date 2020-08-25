package internetshop.dao.impl;

import internetshop.dao.UserDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.addUser(user);

        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(Storage.users.stream().filter(u -> u.getId() == id).findAny()
                .orElse(null));
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

        return user;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.removeIf(u -> u.getId() == id);
    }
}
