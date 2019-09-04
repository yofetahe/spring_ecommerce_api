package com.yhabtu.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.service.SizeService;

@RestController
@RequestMapping("/api")
public class SizeController {
	
	@Autowired
	private SizeService sizeService;

	@CrossOrigin(origins="http://localhost:3000")
	@PostMapping("/addSize")
	public @ResponseBody Long addSize(@RequestBody Size size) {
		
		return sizeService.addSize(size);
	}
	
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/fetchItemSizesByItemId/{item_id}")
	public @ResponseBody List<Size> fetchItemSizeByItemId(@PathVariable("item_id") int item_id){
		
		return sizeService.fetchItemSizeByItemId(item_id);
	}
	
}
