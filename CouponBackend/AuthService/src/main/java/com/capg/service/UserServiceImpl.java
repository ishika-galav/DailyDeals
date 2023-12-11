package com.capg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capg.client.CouponClient;
import com.capg.client.EmailClient;
import com.capg.dto.CouponDto;
import com.capg.dto.RegisterDto;
import com.capg.dto.UserDto;
import com.capg.dto.UserUpdateDto;
import com.capg.entity.Email;
import com.capg.entity.User;
import com.capg.exception.UserAlreadyExistsException;
import com.capg.exception.UserNotFoundException;
import com.capg.repository.SequenceRepository;
import com.capg.repository.UserRepository;
import com.capg.util.SequenceGeneratorService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SequenceRepository seqRepo;
	
	@Autowired
	SequenceGeneratorService seqGen;
	
	@Autowired
	CouponClient couponClient;
	
	@Autowired
	EmailClient emailClient;

	@Override
	public UserDto registerUser(RegisterDto newUser) throws UserAlreadyExistsException {
		Optional<User> opUser = userRepo.findByUsername(newUser.getUsername());
		if(opUser.isPresent()) {
			throw new UserAlreadyExistsException();
		}
		int nextSequence = seqGen.generateSequence(User.SEQUENCE_NAME);

	    // Create the new user with the generated sequence
	    User user = new User();
	    user.setUserId(nextSequence);
		user.setUsername(newUser.getUsername());
		user.setEmail(newUser.getEmail());
		user.setPhoneNumber(newUser.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setRole("ROLE_USER");
		user.setListOfCoupons(new ArrayList<>());
		User savedUser = userRepo.save(user);
		String subject = "Welcome to Daily Deals - Your one shop DISCOUNT Destination!";
		String body = "Dear " + savedUser.getUsername() + ",\r\n"
				+ "\r\n" +
				"We are excited to welcome you to Daily Deals, your new go-to destination for exclusive deals and discounts from a wide array of platforms and stores! Get ready to embark on a journey of savings, as we bring you the best coupons and offers for your favorite brands, including Amazon, Flipkart, Baskin Robbins, and many more.\n\n" +
				"We invite you to discover a world of savings at your fingertips. Say goodbye to full prices and hello to the best deals in town - all at your convenience.\n\n"   +
				"Why Choose Daily Deals?\n\n" +
                "- One-Stop Savings Hub: At Daily Deals, we've curated a vast collection of coupons and offers from a wide range of stores and platforms. You'll find everything you need, all in one place.\n" +
                "- Real-Time Updates: Our dedicated team tirelessly works to keep you updated with the latest and greatest deals. Don't miss out on limited-time offers and flash sales.\n" +
                "- User-Friendly Experience: We've designed our platform with you in mind. It's easy to navigate, ensuring a seamless and enjoyable user experience.\n\n" +
                "How It Works\n\n" +
                "1. Exclusive Deals: Explore an array of exclusive deals that will help you shop more while spending less.\n" +
                "2. Select: Choose your favorite coupon or offer and click to reveal the details.\n" +
                "3. Shop & Save: Complete your purchase on the respective platform while enjoying significant savings.\n" +
                "4. Personalized Coupons: Enjoy handpicked coupons tailored to your preferences, allowing you to maximize your savings.\n\n" +
                "Get Started Today\n\n" +
                "Simply log in to your Daily Deals app using your registered email address and password. The world of savings is just a few taps away.\n\n" +
                "Thank you for choosing Daily Deals as your savings companion. /n /n" +
                "Happy shopping and even happier savings!\n\n" +
                "Warm regards,\n\n" +
                "Team Daily Deals\n";
		//emailClient.sendMail(savedUser.getEmail(), subject, body);
		Email mail = new Email(savedUser.getEmail(), subject, body);
		emailClient.sendMail(mail);
		
		return new UserDto(savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole(), savedUser.getPhoneNumber(), new ArrayList<>());
	}
	
	@Override
	public UserDto registerAdmin(RegisterDto newUser) throws UserAlreadyExistsException {
		Optional<User> opUser = userRepo.findByUsername(newUser.getUsername());
		if(opUser.isPresent()) {
			throw new UserAlreadyExistsException();
		}
		User user = new User();
		user.setUserId(seqGen.generateSequence(user.SEQUENCE_NAME));
		user.setUsername(newUser.getUsername());
		user.setEmail(newUser.getEmail());
		user.setPhoneNumber(newUser.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setRole("ROLE_ADMIN");
		User savedUser = userRepo.save(user);
		return new UserDto(savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole(), savedUser.getPhoneNumber(), new ArrayList<>());
	}

	@Override
	public List<User> getAllUsers() {
//		Optional<List<User>> users = Optional.ofNullable(userRepo.findAll());
//		List<UserDto> udtos = new ArrayList<>();
//		for(User user : users.get()) {
//			Optional<List<String>> strs = Optional.ofNullable(user.getListOfCoupons());
//			List<CouponDto> coupons = new ArrayList<>();
//			for(String code : strs.get()) {
//				coupons.add(couponClient.getCouponByCouponCode(code));
//			}
//			udtos.add(new UserDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole(), user.getPhoneNumber(), coupons));
//		}
//		return udtos;
		return userRepo.findAll();
	}

	@Override
	public UserDto getUserByUsername(String username) throws UserNotFoundException {
		Optional<User> optUser = userRepo.findByUsername(username);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("Username is not Found!");
		}
		User existingUser = optUser.get();
		List<CouponDto> coupons = new ArrayList<>();
		for(String code : existingUser.getListOfCoupons()) {
			coupons.add(couponClient.getCouponByCouponCode(code));
		}
		return new UserDto(existingUser.getUserId(), existingUser.getUsername(), existingUser.getEmail(), existingUser.getRole(), existingUser.getPhoneNumber(), coupons);
	}
	
	@Override
	public UserDto getUserById(int id) throws UserNotFoundException {
		Optional<User> optUser = userRepo.findById(id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User Id not Found!");
		}
		User existingUser = optUser.get();
		List<CouponDto> coupons = new ArrayList<>();
		for(String code : existingUser.getListOfCoupons()) {
			coupons.add(couponClient.getCouponByCouponCode(code));
		}
		return new UserDto(existingUser.getUserId(), existingUser.getUsername(), existingUser.getEmail(), existingUser.getRole(), existingUser.getPhoneNumber(), coupons);
	}
	
	@Override
	public UserDto updateUser(UserUpdateDto userUpdateDTO) throws UserNotFoundException {
		Optional<User> optUser = userRepo.findByUsername(userUpdateDTO.getUsername());
		if(optUser.isPresent()) {
			User existingUser = optUser.get();
//			existingUser.setUsername(user.getUsername());
//			existingUser.setEmail(user.getEmail());
//			existingUser.setPhoneNumber(user.getPhoneNumber());
//
//			User updatedUser =  userRepo.save(existingUser);
//			List<CouponDto> coupons = new ArrayList<>();
//			for(String code : updatedUser.getListOfCoupons()) {
//				coupons.add(couponClient.getCouponByCouponCode(code));
//			}
			
			if(userUpdateDTO.getUsername() != null) { 
				existingUser.setUsername(userUpdateDTO.getUsername());
				}          
			if(userUpdateDTO.getEmail() != null) {
				existingUser.setEmail(userUpdateDTO.getEmail()); 
				}         
			if(userUpdateDTO.getPhoneNumber() != null) {  
				existingUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());  
				}
			
			//return new UserDto(updatedUser.getUserId(),updatedUser.getUsername(),updatedUser.getEmail(), updatedUser.getRole(), updatedUser.getPhoneNumber());
			User updatedUser = userRepo.save(existingUser);
			return new UserDto(updatedUser.getUserId(),updatedUser.getUsername(),updatedUser.getEmail(), updatedUser.getRole(), updatedUser.getPhoneNumber());
		}else {
			throw new UserNotFoundException("User not Found!");
		}

	}

	@Override
	public String deleteUserById(int id) throws UserNotFoundException {
		Optional<User> optuser = userRepo.findById(id);
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User ID not Present!!");
		}
		userRepo.deleteById(id);
		return "User with Id: " + id + " is deleted successfully!";
	}

	@Override
	public String deleteUserByUsername(String username) throws UserNotFoundException {
		Optional<User> optuser=userRepo.findByUsername(username);
		
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User Not Found!!");
		}
		userRepo.deleteByUsername(username);
		return "User with username: " +username+ " is deleted successfully!";
	}

	@Override
	public List<String> getCouponsByUserId(int id) throws UserNotFoundException {
		Optional<User> optuser = userRepo.findById(id);
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User not Found!!");
		}
		User existingUser = optuser.get();
		List<String> coupons = existingUser.getListOfCoupons();
	    return coupons;
	}
	
	@Override
	public List<CouponDto> buyCoupons(String username, List<String> listOfCoupons) throws UserNotFoundException {
		Optional<User> optuser=userRepo.findByUsername(username);
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User Not Found!!");
		}
		User existingUser = optuser.get();
		
        List<String> previousCoupons = existingUser.getListOfCoupons();
		
		for(String code : listOfCoupons) {
			previousCoupons.add(code);
		}
		existingUser.setListOfCoupons(previousCoupons);
		User updatedUser = userRepo.save(existingUser);
		
		List<CouponDto> list = new ArrayList<>();
		for(String code : updatedUser.getListOfCoupons()) {
			CouponDto cdto = couponClient.getCouponByCouponCode(code);
			list.add(cdto);
		}
		String subject = "Daily Deals - Thank you for your purchase!";
		StringBuilder body = new StringBuilder();
		body.append("Dear " + existingUser.getUsername() + ", Thank you for your purchase\n\n");
		body.append("You have bought " + list.size() + " coupons. They are:\n");
		double totalPrice = 0;
		for(CouponDto cdto : list) {
			totalPrice += cdto.getPrice();
			body.append("-> " + cdto.getCouponCode() + " - Rs." + cdto.getPrice() + "\n");
		}
		body.append("\nFor a total price of Rs." + totalPrice);
		body.append("\nThank you and Enjoy,\nDaily Deals Team");
		//emailClient.sendMail(updatedUser.getEmail(), subject, body.toString());
		return list;
	}

}