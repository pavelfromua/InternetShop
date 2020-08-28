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
        Injector injector = Injector.getInstance("internetshop");

        ProductService productService = (ProductService) injector
                .getInstance(ProductService.class);
        UserService userService = (UserService) injector
                .getInstance(UserService.class);
        ShoppingCartService cartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        OrderService orderService = (OrderService) injector
                .getInstance(OrderService.class);

        // users
        User admin = new User("Admin", "admin", "123");
        User customer = new User("Customer", "customer", "111");

        userService.create(admin);
        userService.create(customer);

        userService.getAll().forEach(System.out::println);
        System.out.println();

        // products
        Product xiaomiMi8 = new Product("Mi 8", 4000);
        Product xiaomiMi9 = new Product("Mi 9", 7000);

        productService.create(xiaomiMi8);
        productService.create(xiaomiMi9);
        productService.getAll().forEach(System.out::println);
        System.out.println();

        xiaomiMi9.setPrice(10000);
        xiaomiMi9 = productService.update(xiaomiMi9);
        productService.getAll().forEach(System.out::println);
        System.out.println();

        //productService.delete(xiaomiMi8.getId());
        productService.getAll().forEach(System.out::println);
        System.out.println();

        // shopping carts
        ShoppingCart shoppingCart2;
        shoppingCart2 = new ShoppingCart(new ArrayList<Product>(), admin.getId());
        shoppingCart2 = cartService.create(shoppingCart2);

        shoppingCart2 = cartService.addProduct(shoppingCart2, xiaomiMi9);
        shoppingCart2 = cartService.addProduct(shoppingCart2, xiaomiMi8);

//        shoppingCart = new ShoppingCart(new ArrayList<Product>(), customer.getId());
//        cartService.create(shoppingCart);
        System.out.println("before deleting");
        cartService.getAll().forEach(System.out::println);
        System.out.println();

        shoppingCart2.getProducts().clear();
        cartService.getAll().forEach(System.out::println);
        System.out.println();

        System.out.println(shoppingCart2);
    }
}
