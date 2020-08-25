package internetshop.service.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao cartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return cartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        return cartDao.addProduct(shoppingCart, product);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return cartDao.deleteProduct(shoppingCart, product);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        cartDao.clear(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return cartDao.getByUserId(userId).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return cartDao.delete(shoppingCart);
    }
}
