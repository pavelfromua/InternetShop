package internetshop.dao.impl;

import internetshop.dao.ProductDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);

        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products.stream().collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        Storage.products.stream().filter(p -> p.getId() == product.getId()).forEach(p -> {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
        });

        try {
            Product obj = get(product.getId()).get();
            if (obj == null) {
                return product;
            } else {
                return obj.clone();
            }
        } catch (CloneNotSupportedException e) {
            return product;
        }
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(p -> p.getId() == id);
    }
}
