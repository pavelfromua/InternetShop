package internetshop.service.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.ShoppingCartDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Order;
import internetshop.model.ShoppingCart;
import internetshop.service.OrderService;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartDao cartDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        Order order = orderDao.get(id).get();
        if (order != null) {
            try {
                order = order.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        Order order = new Order(shoppingCart.getProducts(), shoppingCart.getUserId());

        cartDao.clear(shoppingCart);
        shoppingCart.getProducts().clear();

        return create(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderDao.getUserOrders(userId);
    }
}
