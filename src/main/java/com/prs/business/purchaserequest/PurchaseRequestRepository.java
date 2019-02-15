package com.prs.business.purchaserequest;

import org.springframework.data.repository.CrudRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {
// Insert custom methods here
}
