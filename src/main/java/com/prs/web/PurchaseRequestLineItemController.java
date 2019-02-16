package com.prs.web;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;

import java.util.List;
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
@RequestMapping(path = "/purchase-request-line-items")
public class PurchaseRequestLineItemController {
	@Autowired
	PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	PurchaseRequestRepository purchaseRequestRepository;

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
	public JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
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

	@GetMapping("")
	public JsonResponse getPurchaseRequestLineItem(@RequestParam int start, @RequestParam int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepository.findAll(PageRequest.of(start, limit)));
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem pr) {
		return savePurchaseRequestLineItem(pr);
	}

	@PutMapping("/{id}")
	public JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem pr, @PathVariable int id) {
		return savePurchaseRequestLineItem(pr);
	}

	private JsonResponse savePurchaseRequestLineItem(PurchaseRequestLineItem pr) {
		JsonResponse jr = null;
		try {
			purchaseRequestLineItemRepository.save(pr);
			jr = JsonResponse.getInstance(pr);
			recalculateTotal(pr);
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
			ex.printStackTrace();
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deletePurchaseRequestLineItem(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> pr = purchaseRequestLineItemRepository.findById(id);
			if (pr.isPresent()) {
				purchaseRequestLineItemRepository.deleteById(id);
				jr = JsonResponse.getInstance(pr);
				PurchaseRequestLineItem getPR = pr.get();
				recalculateTotal(getPR);
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}
		return jr;
	}

//	 recalculateTotal method (insert into maintenance functions - create, update,
//	 delete)
	private void recalculateTotal(PurchaseRequestLineItem prli) {
		PurchaseRequest pr = prli.getPurchaseRequest(); // gets PRID from PRLI
		List<PurchaseRequestLineItem> filteredPRLIs = purchaseRequestLineItemRepository.findByPurchaseRequest(pr);
		double total = 0;
		for (PurchaseRequestLineItem prlis : filteredPRLIs) {
			double subtotal = prlis.getProduct().getPrice() * prlis.getQuantity();
			total += subtotal;
		}
		pr.setTotal(total);
		purchaseRequestRepository.save(pr);
	}
}
