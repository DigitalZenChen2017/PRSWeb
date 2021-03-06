package com.prs.business.user;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
// write authenticate method
	Optional<User> findByUserNameAndPassword(String username, String password);
}
