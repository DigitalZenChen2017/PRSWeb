package com.prs.business.purchaserequest;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

// <T, ID> are <Type, Primary Key Data Type>
public interface PurchaseRequestLineItemRepository
		extends PagingAndSortingRepository<PurchaseRequestLineItem, Integer> {
// Insert custom methods here
	List<PurchaseRequestLineItem> findByPurchaseRequest(PurchaseRequest pr);
}
