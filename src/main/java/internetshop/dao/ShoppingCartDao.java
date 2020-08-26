package internetshop.dao;

import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    Optional<ShoppingCart> getByUserId(Long userId);
}
