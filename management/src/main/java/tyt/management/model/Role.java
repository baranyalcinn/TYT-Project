package tyt.management.model;

import lombok.Getter;

/**
 * This is an enumeration representing the different roles a user can have in the system.
 * Each role is associated with a string name.
 * The roles include ADMIN, MANAGER, and CASHIER.
 */
@Getter
public enum Role{
    /**
     * The ADMIN role. This is typically associated with users who have the highest level of access in the system.
     */
    ADMIN("ADMIN"),

    /**
     * The MANAGER role. This is typically associated with users who have managerial responsibilities.
     */
    MANAGER("MANAGER"),

    /**
     * The CASHIER role. This is typically associated with users who handle transactions.
     */
    CASHIER("CASHIER");

    /**
     * The name of the role.
     */
    private final String name;

    /**
     * Constructor for the Role enum.
     *
     * @param name The name of the role.
     */
    Role(String name) { this.name = name; }
}