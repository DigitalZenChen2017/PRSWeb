package com.prs.web;

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepository.findAll());
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse getProduct(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Product> Product = productRepository.findById(id);
			if (Product.isPresent()) {
				jr = JsonResponse.getInstance(Product);
			} else {
				jr = JsonResponse.getInstance("No User found for id:" + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("")
	public JsonResponse getProduct(@RequestParam int start, @RequestParam int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepository.findAll(PageRequest.of(start, limit)));
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addProduct(@RequestBody Product p) {
		return saveProduct(p);

	}

	@PutMapping("/{id}")
	public JsonResponse updateProduct(@RequestBody Product p, @PathVariable int id) {
		return saveProduct(p);
	}

	private JsonResponse saveProduct(Product p) {
		JsonResponse jr = null;
		try {
			productRepository.save(p);
			jr = JsonResponse.getInstance(p);
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
			ex.printStackTrace();
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteProduct(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Product> p = productRepository.findById(id);
			if (p.isPresent()) {
				productRepository.deleteById(id);
				jr = JsonResponse.getInstance(p);
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}
		return jr;
	}

}
