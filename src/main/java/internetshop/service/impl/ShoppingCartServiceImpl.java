package internetshop.service.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.service.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao cartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return cartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart get(Long id) {
        ShoppingCart cart = cartDao.get(id).get();
        if (cart != null) {
            try {
                cart = cart.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return cart;
    }

    @Override
    public List<ShoppingCart> getAll() {
        return cartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return cartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);

        return cartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean isRemoved = shoppingCart.getProducts().removeIf(p -> p.getId() == product.getId());

        if (isRemoved) {
            cartDao.update(shoppingCart);
        }
        return isRemoved;
    }

    @Override
    public boolean deleteProductByRow(ShoppingCart shoppingCart, Product product, Long rowNumber) {
        boolean isRemoved = false;

        if (rowNumber != null) {
            List<Product> list = shoppingCart.getProducts();

            for (int i = 0; i < list.size(); i++) {
                if (product.getId() == list.get(i).getId()) {
                    if (rowNumber == i) {
                        shoppingCart.getProducts().remove(i);
                        isRemoved = true;
                        break;
                    }
                }
            }
        }

        if (isRemoved) {
            cartDao.update(shoppingCart);
        }
        return isRemoved;
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
    public boolean delete(Long id) {
        return cartDao.delete(id);
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        clear(shoppingCart);

        return delete(shoppingCart.getId());
    }
}
