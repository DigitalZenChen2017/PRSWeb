package com.prs.web;

import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;

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
@RequestMapping(path = "/vendor")
public class VendorController {
	@Autowired
	VendorRepository vendorRepository;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepository.findAll());
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse getVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> vendor = vendorRepository.findById(id);
			if (vendor.isPresent()) {
				jr = JsonResponse.getInstance(vendor);
			} else {
				jr = JsonResponse.getInstance("No User found for id:" + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex);
		}
		return jr;
	}

	@PostMapping("/")
	public JsonResponse addVendor(@RequestBody Vendor v) {
		return saveVendor(v);

	}

	@PutMapping("/{id}")
	public JsonResponse updateVendor(@RequestBody Vendor v, @PathVariable int id) {
		return saveVendor(v);
	}

	private JsonResponse saveVendor(Vendor v) {
		JsonResponse jr = null;
		try {
			vendorRepository.save(v);
			jr = JsonResponse.getInstance(v);
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Vendor> v = vendorRepository.findById(id);
			if (v.isPresent()) {
				vendorRepository.deleteById(id);
				jr = JsonResponse.getInstance(v);
			} else {
				jr = JsonResponse.getInstance("No user found for id: " + id);
			}
		} catch (Exception ex) {
			jr = JsonResponse.getInstance(ex.getMessage());
		}
		return jr;
	}

}
