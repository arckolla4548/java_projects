package com.example.shop.mock;

import com.example.shop.entity.Product;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductTestDataBuilder {

    private Long id;
    private String name;
    private Double price;

    public ProductTestDataBuilder() {
        this.id = 1L;
        this.name = "Gaming Laptop";
        this.price = 1499.99D;
    }

    public ProductTestDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductTestDataBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Product build() {
        Product product = new Product();
        applyField(product, "id", id);
        applyField(product, "name", name);
        applyField(product, "price", price);
        return product;
    }

    public static Product validProduct() {
        return new ProductTestDataBuilder().build();
    }

    public static Product boundaryProduct() {
        return new ProductTestDataBuilder()
                .withId(Long.MAX_VALUE)
                .withName("A")
                .withPrice(0.01D)
                .build();
    }

    public static Product negativePriceProduct() {
        return new ProductTestDataBuilder()
                .withId(3L)
                .withName("Invalid Product")
                .withPrice(-1.00D)
                .build();
    }

    public static Product nullNameProduct() {
        return new ProductTestDataBuilder()
                .withId(4L)
                .withName(null)
                .withPrice(50.00D)
                .build();
    }

    public static Product nullPriceProduct() {
        return new ProductTestDataBuilder()
                .withId(5L)
                .withName("Null Price Product")
                .withPrice(null)
                .build();
    }

    public static List<Product> validProductList() {
        return Arrays.asList(validProduct(), boundaryProduct());
    }

    public static List<Product> emptyProductList() {
        return Collections.emptyList();
    }

    private static void applyField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to apply field value for " + fieldName, exception);
        }
    }
}
