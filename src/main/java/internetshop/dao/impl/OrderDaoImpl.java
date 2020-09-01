package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoImpl implements OrderDao {
    static final Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);

        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders.stream().collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        Storage.orders.stream().filter(o -> o.getId() == order.getId())
                .forEach(s -> {
                    List<Product> list = new ArrayList<>();
                    for (Product product: order.getProducts()) {
                        list.add(product);
                    }
                    s.setProducts(list);
                });

        try {
            Order obj = get(order.getId()).get();
            if (obj == null) {
                logger.info("Order by " + order.getId()
                        + "isn't gotten from db");
                return order;
            } else {
                logger.info(obj + " is cloned");
                return obj.clone();
            }
        } catch (CloneNotSupportedException e) {
            logger.error("This is error : " + e);
            return order;
        }
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(o -> o.getId() == id);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream().filter(o -> o.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
