package com.example.reactawsdemo.datastore;

import com.example.reactawsdemo.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

	static {
		USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "new user name", null));
		USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "new user name2", null));
		
	}

	public List<UserProfile> getUserProfiles(){
		return USER_PROFILES;
	}
}