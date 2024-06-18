package tyt.product.model;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    // Private constructor to prevent instantiation
    private ProductSpecification() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<ProductEntity> hasPriceGreaterThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<ProductEntity> hasPriceLessThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }

}