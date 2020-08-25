package internetshop.service;

import internetshop.model.Product;
import internetshop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart); //remove all products from the shoppingCart

    ShoppingCart getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
