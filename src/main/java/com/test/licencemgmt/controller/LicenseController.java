package com.test.licencemgmt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.licencemgmt.LicenseService;
import com.test.licencemgmt.models.Product;

import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.connection.Exchange.ResponseBodySource;

@RestController
public class LicenseController {
	
	@Autowired
	LicenseService licenseService;
	@PostMapping("/token")
	public ResponseEntity<String> generateToken(@RequestParam String accountId){
		return new ResponseEntity<>(licenseService.GenerateToken(accountId).getBody().toString(),HttpStatus.OK);
	}
	@GetMapping("/licenses")
	public ResponseEntity<String> getAllLicense(@RequestParam String accountId,@RequestParam String token) throws IOException {
		return new ResponseEntity<String>(licenseService.getAllLicenses(accountId, token).body().string(),HttpStatus.OK); 
	}
	@GetMapping("/licenses/{id}")
	public ResponseEntity<String> getLicenceById(@PathVariable String id,@RequestParam String accountId, @RequestParam String token) throws IOException
	{
		return new ResponseEntity<String>(licenseService.getLicenceById(id, accountId, token).body().string(),HttpStatus.OK);
	}
	@PostMapping("/product")
	public ResponseEntity<String> createProduct(@RequestParam String accountId,@RequestParam String token , @RequestBody Product product) throws IOException{
		return new ResponseEntity<String>(licenseService.createProduct(accountId, token, product).body().string(),HttpStatus.OK);
	}
	@PostMapping("/policy")
	public ResponseEntity<String> createPolicy(@RequestParam String accountId,@RequestParam String productId,@RequestParam String token,@RequestParam int duration) throws IOException
	{
		return new ResponseEntity<String>(licenseService.createPolicy(accountId, productId, token, duration).body().string(),HttpStatus.OK);
	}
	@PostMapping("/license")
	public ResponseEntity<String> createLicense(@RequestParam String accountId,
			@RequestParam String token,@RequestParam String policy_id) throws IOException {
		return new ResponseEntity<String>(licenseService.createLicence(accountId, token, policy_id).body().string(),HttpStatus.OK);
	}
	@PostMapping("validate/{id}")
	public ResponseEntity<String> validate(@PathVariable String id,@RequestParam String accountId,@RequestParam String token) throws IOException
	{
		return new ResponseEntity<>(licenseService.validateLicense(id, accountId, token).body().string(),HttpStatus.OK);
	}
	@PostMapping("validateByKey/{key}")
	public ResponseEntity<String> validateByKey(@PathVariable String key,@RequestParam String accountId,@RequestParam String token) throws IOException
	{
		return new ResponseEntity<String>(licenseService.validateLicenseByKey(key, accountId, token).body().string(),HttpStatus.OK);
	}
}
