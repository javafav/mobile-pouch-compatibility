package com.mobilematching.admin.pouches;


import org.springframework.data.jpa.domain.Specification;

import com.mobilematching.entity.Pouch;

public class PouchSpecification {
    public static Specification<Pouch> hasMaterial(String material) {
        return (root, query, criteriaBuilder) -> 
            material == null ? null : criteriaBuilder.equal(root.get("material"), material);
    }

    public static Specification<Pouch> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> 
            category == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%");
    }

    public static Specification<Pouch> hasBrand(String brand) {
        return (root, query, criteriaBuilder) -> 
            brand == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Pouch> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) 
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) 
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
