package com.prs.business.product;

import org.springframework.data.repository.PagingAndSortingRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
// Insert custom methods here
}
