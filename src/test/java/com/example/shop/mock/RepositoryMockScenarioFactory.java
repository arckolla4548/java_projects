package com.example.shop.mock;

import com.example.shop.entity.AppUser;
import com.example.shop.entity.Product;
import java.util.List;
import org.mockito.Mockito;

public final class RepositoryMockScenarioFactory {

    private RepositoryMockScenarioFactory() {
    }

    public static List<Product> successfulProductRepositoryResponse() {
        return ProductTestDataBuilder.validProductList();
    }

    public static List<AppUser> successfulUserRepositoryResponse() {
        return AppUserTestDataBuilder.validUserList();
    }

    public static RuntimeException repositoryFailure() {
        return new RuntimeException("repository failure");
    }

    public static RuntimeException entityNotFoundFailure() {
        return new RuntimeException("entity not found");
    }

    public static <T> T mockDependency(Class<T> dependencyClass) {
        return Mockito.mock(dependencyClass);
    }
}
