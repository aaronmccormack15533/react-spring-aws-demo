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
		USER_PROFILES.add(new UserProfile(UUID.fromString("db216fec-ea84-485f-aa99-07898c812fc6"), "new user name", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("f3674f50-ecf0-421d-a292-d271c4fda0cb"), "new user name2", null));
	}

	public List<UserProfile> getUserProfiles(){
		return USER_PROFILES;
	}
}
