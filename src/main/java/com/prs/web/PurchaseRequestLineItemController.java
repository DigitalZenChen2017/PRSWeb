package com.prs.web;

import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/purchaserequestlineitem")
public class PurchaseRequestLineItemController {
	@Autowired
	PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepository.findAll());
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse getProduct(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> PurchaseRequest = purchaseRequestLineItemRepository.findById(id);
			if (PurchaseRequest.isPresent()) {
				jr = JsonResponse.getInstance(PurchaseRequest);
			} else {
				jr = JsonResponse.getInstance("No User found for id:" + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addProduct(@RequestBody PurchaseRequestLineItem pr) {
		return savePurchaseRequestLineItem(pr);

	}

	@PutMapping("/{id}")
	public JsonResponse updateProduct(@RequestBody PurchaseRequestLineItem pr, @PathVariable int id) {
		return savePurchaseRequestLineItem(pr);
	}

	private JsonResponse savePurchaseRequestLineItem(PurchaseRequestLineItem pr) {
		JsonResponse jr = null;
		try {
			purchaseRequestLineItemRepository.save(pr);
			jr = JsonResponse.getInstance(pr);
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
			Optional<PurchaseRequestLineItem> pr = purchaseRequestLineItemRepository.findById(id);
			if (pr.isPresent()) {
				purchaseRequestLineItemRepository.deleteById(id);
				jr = JsonResponse.getInstance(pr);
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}
		return jr;
	}

}
