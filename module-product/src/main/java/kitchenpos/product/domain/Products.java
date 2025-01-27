package kitchenpos.product.domain;

import java.util.HashSet;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public boolean isSameSize(List<Long> productIds) {
        return products.size() == new HashSet<>(productIds).size();
    }

    public List<Product> getProducts() {
        return products;
    }
}
