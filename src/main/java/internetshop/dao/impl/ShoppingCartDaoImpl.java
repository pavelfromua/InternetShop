package internetshop.dao.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import java.util.Optional;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);

        return shoppingCart;
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        ShoppingCart shoppingCartToAddProduct = getByUserId(shoppingCart.getUserId()).get();

        if (shoppingCartToAddProduct != null) {
            Storage.shoppingCarts.stream().filter(s -> s.equals(shoppingCartToAddProduct))
                    .forEach(s -> s.getProducts().add(product));

            return shoppingCartToAddProduct;
        } else {
            return shoppingCart;
        }
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        ShoppingCart shoppingCartToDeleteProduct = getByUserId(shoppingCart.getUserId()).get();

        if (shoppingCartToDeleteProduct != null) {
            int countOfProductsBefore = shoppingCartToDeleteProduct.getProducts().size();

            Storage.shoppingCarts.stream().filter(s -> s.equals(shoppingCartToDeleteProduct))
                    .forEach(s -> s.getProducts().removeIf(p -> p.getId() == product.getId()));

            ShoppingCart shoppingCartAfterDeleteProduct = getByUserId(shoppingCart
                    .getUserId()).get();
            int countOfProductsAfter = shoppingCartAfterDeleteProduct.getProducts().size();

            return countOfProductsAfter == countOfProductsBefore ? false : true;
        } else {
            return false;
        }
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        ShoppingCart shoppingToClear = getByUserId(shoppingCart.getUserId()).get();

        if (shoppingToClear != null) {
            Storage.shoppingCarts.stream().filter(s -> s.equals(shoppingToClear))
                    .forEach(s -> s.getProducts().clear());
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Optional.ofNullable(Storage.shoppingCarts.stream()
                .filter(s -> s.getUserId() == userId).findFirst().orElse(null));
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        ShoppingCart shoppingToDelete = getByUserId(shoppingCart.getUserId()).get();

        if (shoppingToDelete != null) {
            return Storage.shoppingCarts.removeIf(s -> s.equals(shoppingToDelete));
        } else {
            return false;
        }
    }
}
