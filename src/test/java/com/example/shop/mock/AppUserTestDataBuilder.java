package com.example.shop.mock;

import com.example.shop.entity.AppUser;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppUserTestDataBuilder {

    private Long id;
    private String username;
    private String password;

    public AppUserTestDataBuilder() {
        this.id = 100L;
        this.username = "shop-user";
        this.password = "SecurePassword123";
    }

    public AppUserTestDataBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AppUserTestDataBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public AppUserTestDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AppUser build() {
        AppUser appUser = new AppUser();
        applyField(appUser, "id", id);
        applyField(appUser, "username", username);
        applyField(appUser, "password", password);
        return appUser;
    }

    public static AppUser validUser() {
        return new AppUserTestDataBuilder().build();
    }

    public static AppUser boundaryUser() {
        return new AppUserTestDataBuilder()
                .withId(Long.MAX_VALUE)
                .withUsername("u")
                .withPassword("p")
                .build();
    }

    public static AppUser nullUsernameUser() {
        return new AppUserTestDataBuilder()
                .withId(101L)
                .withUsername(null)
                .withPassword("Password123")
                .build();
    }

    public static AppUser nullPasswordUser() {
        return new AppUserTestDataBuilder()
                .withId(102L)
                .withUsername("null-password-user")
                .withPassword(null)
                .build();
    }

    public static List<AppUser> validUserList() {
        return Arrays.asList(validUser(), boundaryUser());
    }

    public static List<AppUser> emptyUserList() {
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
