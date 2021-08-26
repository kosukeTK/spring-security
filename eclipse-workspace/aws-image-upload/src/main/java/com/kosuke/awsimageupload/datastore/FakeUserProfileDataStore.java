package com.kosuke.awsimageupload.datastore;

import org.springframework.stereotype.Repository;

import com.kosuke.awsimageupload.profile.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class FakeUserProfileDataStore {
	
	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
	
	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("ca584072-37b2-4089-a890-817dcf32a5c1"),"kosuke", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("55f73df5-c3f2-4fa6-beea-1d81537ca157"), "yuino", null));
	}
	
	public List<UserProfile> getUserProfiles() {
		return USER_PROFILES;
	}
}
