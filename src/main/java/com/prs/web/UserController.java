package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				jr = JsonResponse.getInstance(user);
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addUser(@RequestBody User u) {
		return saveUser(u);
	}

	@PutMapping("/{id}")
	public JsonResponse updateUser(@RequestBody User u, @PathVariable int id) {
		return saveUser(u);
	}

	private JsonResponse saveUser(User u) {
		JsonResponse jr = null;
		try {
			userRepository.save(u);
			jr = JsonResponse.getInstance(u);
		} catch (DataIntegrityViolationException cve) {
			jr = JsonResponse.getInstance(cve.getMessage());
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteUser(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<User> u = userRepository.findById(id);
			if (u.isPresent()) {
				userRepository.deleteById(id);
				jr = JsonResponse.getInstance(u);
			} else {
				jr = JsonResponse.getInstance("Delete failed. No user for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}

		return jr;
	}

}
