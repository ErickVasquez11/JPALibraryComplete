package com.erickvasquez.documentos.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"playlists"})
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {


	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "username", unique = true)
	private String username;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@Column(name = "active", insertable = false)
	private Boolean active;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PlayList> playlists;
	

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
