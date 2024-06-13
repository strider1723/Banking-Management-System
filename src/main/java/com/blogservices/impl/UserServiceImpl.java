package com.blogservices.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepo;
import com.blogservices.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updateUser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(updateUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>users=this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
		
	}

	@Override
	public void deleteUser(Integer userId) {
	User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id", userId));
	this.userRepo.delete(user);
	

	}
	
	private User dtoToUser(UserDto userDto) { 
			User user=new User();
			user.setId(userDto.getId());
	 		user.setEmail(userDto.getEmail());
			user.setName(userDto.getName());
			user.setAbout(userDto.getAbout());
			user.setPassword(userDto.getPassword());
		return user;
		
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto =new UserDto() ;
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getName());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());
		
		return userDto;
	}

}
