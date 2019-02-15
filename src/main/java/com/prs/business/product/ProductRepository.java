package com.prs.business.product;

import org.springframework.data.repository.CrudRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface ProductRepository extends CrudRepository<Product, Integer> {
// Insert custom methods here
}
