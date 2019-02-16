package com.prs.business.vendor;

import org.springframework.data.repository.PagingAndSortingRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface VendorRepository extends PagingAndSortingRepository<Vendor, Integer> {
// Insert custom methods here
}
