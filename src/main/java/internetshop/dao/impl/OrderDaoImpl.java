package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(List.copyOf(shoppingCart.getProducts()), shoppingCart.getUserId());

        Storage.orders.add(order);
        shoppingCart.getProducts().clear();

        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream().filter(o -> o.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.ofNullable(Storage.orders.stream().filter(o -> o.getId() == id).findFirst()
                .orElse(null));
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders.stream().collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(o -> o.getId() == id);
    }
}
