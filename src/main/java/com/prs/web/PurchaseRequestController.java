package com.prs.web;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;

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
@RequestMapping(path = "/purchase-requests")
public class PurchaseRequestController {
	@Autowired
	PurchaseRequestRepository purchaserequestRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaserequestRepository.findAll());
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse getPurchaseRequest(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> PurchaseRequest = purchaserequestRepository.findById(id);
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
	public JsonResponse getPurchaseRequest(@RequestParam int start, @RequestParam int limit) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaserequestRepository.findAll(PageRequest.of(start, limit)));
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addProduct(@RequestBody PurchaseRequest pr) {
		return savePurchaseRequest(pr);

	}

	@PutMapping("/{id}")
	public JsonResponse updateProduct(@RequestBody PurchaseRequest pr, @PathVariable int id) {
		return savePurchaseRequest(pr);
	}

	private JsonResponse savePurchaseRequest(PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			purchaserequestRepository.save(pr);
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
			Optional<PurchaseRequest> pr = purchaserequestRepository.findById(id);
			if (pr.isPresent()) {
				purchaserequestRepository.deleteById(id);
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
