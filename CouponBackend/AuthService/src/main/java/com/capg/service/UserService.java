package com.capg.service;

import java.util.List;

import com.capg.dto.CouponDto;
import com.capg.dto.RegisterDto;
import com.capg.dto.UserDto;
import com.capg.dto.UserUpdateDto;
import com.capg.entity.User;
import com.capg.exception.UserAlreadyExistsException;
import com.capg.exception.UserNotFoundException;

public interface UserService {
	
    UserDto registerUser(RegisterDto user) throws UserAlreadyExistsException;
	UserDto registerAdmin(RegisterDto user) throws UserAlreadyExistsException;
    List<User> getAllUsers();
	UserDto getUserByUsername(String username) throws UserNotFoundException;
	UserDto getUserById(int id) throws UserNotFoundException;
	UserDto updateUser(UserUpdateDto userUpdateDTO) throws UserNotFoundException;
    String deleteUserByUsername(String username) throws UserNotFoundException;
	String deleteUserById(int id) throws UserNotFoundException;
	List<String> getCouponsByUserId(int id) throws UserNotFoundException;
	List<CouponDto> buyCoupons(String username, List<String> listOfCoupons) throws UserNotFoundException;

}
