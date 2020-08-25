package internetshop.model;

import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order(List<Product> products, Long userId) {
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
}
