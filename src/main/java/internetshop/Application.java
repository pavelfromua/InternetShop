package internetshop;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ProductService;

public class Application {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("internetshop");
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product xiaomiMi8 = new Product("Mi 8", 4000);
        Product xiaomiMi9 = new Product("Mi 9", 7000);

        productService.create(xiaomiMi8);
        productService.create(xiaomiMi9);
        productService.getAll().forEach(System.out::println);

        xiaomiMi9.setPrice(10000);
        productService.update(xiaomiMi9);
        productService.getAll().forEach(System.out::println);

        productService.delete(xiaomiMi8.getId());
        productService.getAll().forEach(System.out::println);
    }
}
