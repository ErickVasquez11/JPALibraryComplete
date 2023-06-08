package com.erickvasquez.documentos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickvasquez.documentos.models.dtos.response.MessageDTO;
import com.erickvasquez.documentos.models.dtos.tokens.TokenDTO;
import com.erickvasquez.documentos.models.dtos.users.ChangePasswordDTO;
import com.erickvasquez.documentos.models.dtos.users.LoginDTO;
import com.erickvasquez.documentos.models.dtos.users.RegisterUserDTO;
import com.erickvasquez.documentos.models.dtos.users.UpdateUserDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Token;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.services.PlaylistService;
import com.erickvasquez.documentos.services.UserService;
import com.erickvasquez.documentos.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute @Valid LoginDTO info, BindingResult validations) {
        if(validations.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findOneById(info.getId());

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!userService.comparePassword(info.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Token token = userService.registerToken(user);
            return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("")
	public ResponseEntity<?> getUsers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		User user = userService.findOneById(id);
		
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("Warn! user not found"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	

	
	
	@GetMapping("/playlists")
	public ResponseEntity<?> getPlaylistsByUser(@RequestParam("users") String userData, @RequestParam(required = false) String title){
		User user = userService.findOneByUsernameOrEmail(userData);
		if(user == null) {
				return new ResponseEntity<>(
						new MessageDTO("user not found"), HttpStatus.NOT_FOUND);			
		}
		
		if(title != null) {
			List<PlayList> playlist = playlistService.findTitle(userData);
			if(playlist == null) {
				return new ResponseEntity<>(
						new MessageDTO("Playlist not found"), HttpStatus.NOT_FOUND);
			}
			
			List<PlayList> Result = playlist.stream()
					.filter(element -> element.getUser().equals(user))
					.collect(Collectors.toList());		
				return new ResponseEntity<>(Result,HttpStatus.OK);		
		}
		
		List<PlayList> playlistResponse = user.getPlaylists();
		return new ResponseEntity<>(playlistResponse,HttpStatus.OK);	
	}
			
	@PutMapping("")
	public ResponseEntity<?> updateUser(
			@ModelAttribute @Valid UpdateUserDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/change-password")
	public ResponseEntity<?> changePassword(
			@ModelAttribute @Valid ChangePasswordDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}