package com.prs.business.vendor;

import org.springframework.data.repository.CrudRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface VendorRepository extends CrudRepository<Vendor, Integer> {
// Insert custom methods here
}
