package com.prs.business.purchaserequest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.prs.business.user.User;

// <T, ID> are <Type, Primary Key Data Type>
public interface PurchaseRequestRepository extends PagingAndSortingRepository<PurchaseRequest, Integer> {
// Insert custom methods here

// show only requests "In Review Status AND Not Assigned to Reviewer
	List<PurchaseRequest> findByStatusAndUserNot(String status, User user);
}
