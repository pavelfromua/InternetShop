package internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Cloneable {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(List<Product> products, Long userId) {
        this.products = products;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", products=" + products
                + ", userId=" + userId
                + '}';
    }

    @Override
    public ShoppingCart clone() throws CloneNotSupportedException {
        List<Product> productsClone = new ArrayList<Product>(products.size());
        for (Product product: products) {
            productsClone.add(product);
        }

        ShoppingCart cart = new ShoppingCart(productsClone, userId);
        cart.setId(id);

        return cart;
    }
}
