package TYT.auth.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    CASHIER("CASHIER");


    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
