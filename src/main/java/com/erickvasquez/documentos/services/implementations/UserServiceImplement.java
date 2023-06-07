package com.erickvasquez.documentos.services.implementations;

import java.lang.ProcessHandle.Info;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.dtos.users.RegisterUserDTO;
import com.erickvasquez.documentos.models.dtos.users.UpdateUserDTO;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.UserRepository;
import com.erickvasquez.documentos.services.UserService;

@Service
public class UserServiceImplement  implements UserService{

	//password encrypt
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	
	/* public void save(RegisterUserDTO userInfo) throws Exception {
		User user = new User(
				userInfo.setUsername(userInfo.getUsername()),
				userInfo.setEmail(userInfo.getEmail()),
				userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()))
				);
		
		userRepository.save(user);
		
	} */
	
	@Override
	public void save(RegisterUserDTO userInfo) throws Exception{
		User user = new User();
		
		user.setUsername(userInfo.getUsername());
		user.setEmail(userInfo.getEmail());
		user.setPassword(
							passwordEncoder.encode(userInfo.getPassword())
						);
		userRepository.save(user);
	}
	@Override
	public void update(UpdateUserDTO userInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findOneById(String code) {
		return userRepository.findOneByUsernameOrEmail(code, code);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	

	@Override
	public User findOneByUsernameOrEmail(String userData) {
		
		return userRepository.findOneByUsernameOrEmail(userData, userData);
	}
}
