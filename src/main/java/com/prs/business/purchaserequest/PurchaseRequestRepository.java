package com.prs.business.purchaserequest;

import org.springframework.data.repository.PagingAndSortingRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface PurchaseRequestRepository extends PagingAndSortingRepository<PurchaseRequest, Integer> {
// Insert custom methods here

}
