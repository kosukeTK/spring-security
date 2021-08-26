package com.kosuke.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
	
	private final UUID userProfileId;
	private final String username;
	private String userProfileImageLink;
	
	
	public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
		this.userProfileId = userProfileId;
		this.username = username;
		this.userProfileImageLink = userProfileImageLink;
	}
	
	
	public UUID getUserprofileId() {
		return userProfileId;
	}
//	public void setUserProfileId(UUID userProfileId) {
//		this.userProfileId = userProfileId;
//	}
	
	public String getUsername() {
		return username;
	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}
	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(userProfileImageLink, other.userProfileImageLink) &&
			   Objects.equals(username, other.username) &&
			   Objects.equals(userProfileId, other.userProfileId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userProfileImageLink, username, userProfileId);
	}
}
