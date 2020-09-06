package internetshop.dao.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    static final Logger logger = Logger.getLogger(ShoppingCartDaoImpl.class);

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);

        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts.stream().collect(Collectors.toList());
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.stream().filter(s -> s.getId() == shoppingCart.getId())
                .forEach(s -> {
                    List<Product> list = new ArrayList<>();
                    for (Product product: shoppingCart.getProducts()) {
                        list.add(product);
                    }
                    s.setProducts(list);
                });

        try {
            ShoppingCart obj = get(shoppingCart.getId()).get();
            if (obj == null) {
                logger.info("Shopping cart by " + shoppingCart.getId()
                        + "isn't gotten from db");
                return shoppingCart;
            } else {
                logger.info(obj + " is cloned");
                return obj.clone();
            }
        } catch (CloneNotSupportedException e) {
            logger.error("This is error : " + e.getMessage());
            return shoppingCart;
        }
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(s -> s.getId() == id);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        Storage.shoppingCarts.stream().filter(s -> s.getId() == shoppingCart.getId())
                .forEach(s -> s.getProducts().clear());

    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts.stream().filter(s -> s.getUserId() == userId).findFirst();
    }
}
