package com.example.springsecurityjpa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Client")
public class Oauth2Client implements ClientDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private User user;
    private String name;
    private String homepageUrl;
    private String clientId;
	private String resourceIds;
    private String clientSecret;
    private String scope;
    private String grantTypes;
    private String redirectUri;
	private String authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Boolean autoApprove;

    public void setUser(User user) {
        this.user = user;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }


    
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        if (resourceIds == null) return null;
        String[] s = resourceIds.split(",");
        return new HashSet<>(Arrays.asList(s));
    }

    @Override
    public boolean isSecretRequired() {
        return clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scope != null;
    }

    @Override
    public Set<String> getScope() {
        if (scope == null) return null;
        String[] s = scope.split(",");
        return new HashSet<>(Arrays.asList(s));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (grantTypes == null) return null;
        String[] s = grantTypes.split(",");
        return new HashSet<>(Arrays.asList(s));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (redirectUri == null) return null;
        String[] s = redirectUri.split(",");
        return new HashSet<>(Arrays.asList(s));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) return new ArrayList<>();
        return AuthorityUtils.createAuthorityList(authorities.split(","));
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return autoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public String getHomepageUrl() {
		return homepageUrl;
	}

	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}
	
    public void setClientId(String clientId) {
		this.clientId = clientId;
	}
      
    public void setClientSecret(String clientSecret) {
    	this.
    	clientSecret=clientSecret;
    }
    
    public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
}




