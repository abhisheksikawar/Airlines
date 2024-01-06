package com.infosys.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.entity.User;
import com.infosys.exception.ResourceNotFoundException;
import com.infosys.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	//For Admin purpose only
	@GetMapping("/allUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	//get User
	@GetMapping("user/{userName}")
	public User getUserByID(@PathVariable String userName) throws Throwable {
		return userRepository.findByUserName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user name : " + userName));
	}

	// Create User
	@PostMapping("/createUser")
	public ResponseEntity createUser(@RequestBody User user) {
		Optional<User> existingUser = userRepository.findByUserName(user.getUserName());
		if(existingUser.isPresent()){
			return ResponseEntity.badRequest().body("Username already exists");
		}
		User addedUser=userRepository.save(user);
		return ResponseEntity.created(URI.create("/"+user.getUserName())).build();
	}

	// Update User
	@PutMapping("update/{userName}")
	public User updateUser(@RequestBody User user, @PathVariable String userName) {
		// fetching the existing user with given user name
		User existingUser = userRepository.findByUserName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user name : " + userName));

		// updating the existing user with updated data
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setAddress(user.getAddress());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setPhoneNo(user.getPhoneNo());

		return userRepository.save(existingUser);
	}

	// Delete User
	@DeleteMapping("delete/{userName}")
	public ResponseEntity<User> deleteUser(@PathVariable String userName) {
		//fetching the existing user with given user name
		User existingUser = userRepository.findByUserName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with user name : " + userName));

		userRepository.delete(existingUser);
		return ResponseEntity.ok(existingUser);
	}
}
