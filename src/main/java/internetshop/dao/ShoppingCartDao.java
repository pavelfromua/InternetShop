package internetshop.dao;

import internetshop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    void clear(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);
}
