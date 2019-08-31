package com.mkkim.myspringboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkkim.myspringboot.entity.User;
import com.mkkim.myspringboot.exception.ResourceNotFoundException;
import com.mkkim.myspringboot.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserRestController {
	@Autowired
	private UserRepository repository;

	/*
	Insert	@PostMapping
	Select	@GetMapping
	Update	@PutMapping
	Delete	@DeleteMapping
	 */

	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody User pUser) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setName(pUser.getName());
		user.setEmail(pUser.getEmail());
		return repository.save(user);
	}
	
	@PostMapping("")
	public User create(@RequestBody User user) {
		return repository.save(user);
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}
	
	@GetMapping("")
	public List<User> getUsers(){
		return repository.findAll();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestBody User pUser){		// @PathVariable url상의 데이터 받아오기
		Optional<User> optional = repository.findById(id);
		if (optional.isPresent()) {
			if (optional.get().getName().equals(pUser.getName())) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Bad request - unauthorized", HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/usersxml", produces= {"application/xml"})
	public List<User> getUsersXml() {
		return repository.findAll();
	}
}
