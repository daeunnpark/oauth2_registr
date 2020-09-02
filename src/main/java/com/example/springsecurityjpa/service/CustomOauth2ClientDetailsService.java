package com.example.springsecurityjpa.service;

import com.example.springsecurityjpa.model.Oauth2Client;
import com.example.springsecurityjpa.repository.Oauth2ClientRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOauth2ClientDetailsService implements ClientDetailsService{

    @Autowired
    private Oauth2ClientRepository oauth2ClientRepository;

    @Override
    @Transactional
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Oauth2Client client = oauth2ClientRepository.findByClientId(clientId);
        return new BaseClientDetails(client);
    }
    
    public List<Oauth2Client> findByUserId(Integer userId){
    	return oauth2ClientRepository.findByUserId(userId);	
    }

    public Oauth2Client findByUserIdAndName(Integer id, String name){
        Optional<Oauth2Client> client = oauth2ClientRepository.findByUserIdAndName(id, name);
        System.out.println("id = " + id + "name = " + name);
        client.orElseThrow(()->new NoSuchClientException("No client registered with " + name));
        return client.get();
    }

    public void save(Oauth2Client client) throws NoSuchAlgorithmException {
        client.setClientId(client.getName()+System.currentTimeMillis());
        client.setClientSecret(sha256(UUID.randomUUID().toString()));
        //client.setRedirectUri("http://10.113.97.165:8081");
        client.setAccessTokenValiditySeconds(3600);
        client.setAuthorities("USER");
        client.setAutoApprove(false);
        client.setGrantTypes("authorization_code");
        client.setRefreshTokenValiditySeconds(3600);
        client.setResourceIds("oauth2-resource");
        client.setScope("read");

        oauth2ClientRepository.save(client);
    }

    public void delete(Oauth2Client client){
        oauth2ClientRepository.delete(client);
    }

    public String sha256(String original) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        return new String(Hex.encodeHexString(digest));
    }



}
