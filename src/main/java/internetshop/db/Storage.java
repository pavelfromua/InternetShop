package internetshop.db;

import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();

    public static long productId = 0L;
    public static long userId = 0L;
    public static long orderId = 0L;
    public static long shoppingCartId = 0L;

    public static void addProduct(Product product) {
        product.setId(++productId);
        try {
            products.add(product.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        user.setId(++userId);
        try {
            users.add(user.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void addOrder(Order order) {
        order.setId(++orderId);
        try {
            orders.add(order.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++shoppingCartId);
        try {
            shoppingCarts.add(shoppingCart.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
