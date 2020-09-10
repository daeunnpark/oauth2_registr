package com.example.springsecurityjpa.repository;

import com.example.springsecurityjpa.model.Oauth2Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Oauth2ClientRepository extends JpaRepository<Oauth2Client, String> {
	Oauth2Client findByClientId(String clientId);
	List<Oauth2Client> findByUserUsername(String userId);
	Optional<Oauth2Client> findByUserUsernameAndName(String username, String name);
	//void deleteById(Integer id);
}