package internetshop;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.OrderService;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) {
        // users
        User admin = new User("Admin", "admin", "123");
        User customer = new User("Customer", "customer", "111");

        Injector injector = Injector.getInstance("internetshop");

        UserService userService = (UserService) injector
                .getInstance(UserService.class);

        userService.create(admin);
        userService.create(customer);

        userService.getAll().forEach(System.out::println);
        System.out.println();

        // products
        Product xiaomiMi8 = new Product("Mi 8", 4000);
        Product xiaomiMi9 = new Product("Mi 9", 7000);

        ProductService productService = (ProductService) injector
                .getInstance(ProductService.class);

        productService.create(xiaomiMi8);
        productService.create(xiaomiMi9);
        productService.getAll().forEach(System.out::println);
        System.out.println();

        xiaomiMi9.setPrice(10000);
        xiaomiMi9 = productService.update(xiaomiMi9);
        productService.getAll().forEach(System.out::println);
        System.out.println();

        productService.delete(xiaomiMi8.getId());
        productService.getAll().forEach(System.out::println);
        System.out.println();

        // shopping carts
        ShoppingCartService cartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);

        ShoppingCart shoppingCart;
        shoppingCart = new ShoppingCart(new ArrayList<Product>(), admin.getId());
        shoppingCart = cartService.create(shoppingCart);

        shoppingCart = cartService.addProduct(shoppingCart, xiaomiMi9);
        shoppingCart = cartService.addProduct(shoppingCart, xiaomiMi8);

        cartService.getAll().forEach(System.out::println);
        System.out.println();

        OrderService orderService = (OrderService) injector
                .getInstance(OrderService.class);

        orderService.completeOrder(shoppingCart);
    }
}
