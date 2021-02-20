package com.craterzone.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.craterzone.demo.model.Address;
import com.craterzone.demo.model.User;
import com.craterzone.demo.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	


	
	@Autowired
	private UserService userService;


	//get getAll
/*
	@GetMapping("/user")
	private ResponseEntity<Optional<List<User>>> getAll(){
		Optional<List<User>> user = userService.getAll();
		if(userid!=null) 
			return ResponseEntity.status(HttpStatus.OK).body(user);
	
			return ResponseEntity.noContent().build();
	 }
	*/
	@GetMapping("/list")
	public ResponseEntity<Optional<List<User>>> getAll()
	
	{
		Optional<List<User>> list = userService.getAll();
		if(list!=null)
		 return ResponseEntity.status(HttpStatus.OK).body(list);		
			
		return ResponseEntity.noContent().build();
	}
	
	/*
	 * @GetMapping("/id/{user_id}") private ResponseEntity<Optional<Integer>>
	 * getuserbyid(@PathVariable("user_id") int user_id ,User user){
	 * Optional<Integer> userid = userService.getUserById(user_id);
	 * if(userid.isPresent()) {
	 * 
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	 * 
	 * }
	 * 
	 * return ResponseEntity.status(HttpStatus.FOUND).body(userid); }
	 */

	
	@DeleteMapping("/{user_id}")
	private ResponseEntity<User> deleteUser(@PathVariable("user_id") int user_id){
		if(!Objects.isNull(user_id)) {
		userService.deleteById(user_id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("")
	private ResponseEntity<User> registerUser(@RequestBody User user){
		//validation
		Optional<User> userfromdatabase = userService.registerUser(user);
		if(!Objects.isNull(userfromdatabase)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		}
		return new ResponseEntity<User>(HttpStatus.CONFLICT);
	}
	@PostMapping("/login")
	private ResponseEntity<Optional<User>> login(@RequestBody User user){
		//validation
		Optional<User> userdb = userService.login(user);
		if(userdb!=null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(userdb);
		}
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	

	@GetMapping("/{id}")
	private ResponseEntity<Optional<User>> getUserById(@PathVariable("id") int id){
		Optional<User> returnuser = userService.getUserById(id);
		if(returnuser.isPresent()){
		return ResponseEntity.status(HttpStatus.FOUND).body(returnuser);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	
	@PatchMapping("/{id}/Address")
	private ResponseEntity updateAddress(@PathVariable("id") int id,@RequestBody Address address) {
		Optional<User> addres = userService.updateAddress(id, address);
		if(addres.isPresent()) {
		
			return ResponseEntity.status(HttpStatus.OK).body(addres);
		}
		return ResponseEntity.badRequest().build();
	}
}
