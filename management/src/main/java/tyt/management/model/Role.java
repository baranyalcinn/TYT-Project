package tyt.management.model;


import lombok.Getter;

@Getter
public enum Role{
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    CASHIER("CASHIER");

    private final String name;

    Role(String name) { this.name = name; }
}
