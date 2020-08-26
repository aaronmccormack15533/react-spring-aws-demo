package com.example.reactawsdemo.profile;

import com.example.reactawsdemo.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
	private final UserProfileDataAccessService userProfileDataAccessService;
	private final FileStore fileStore;

	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}

	List<UserProfile> getUserProfiles(){
		return userProfileDataAccessService.getUserProfiles();
	}
}
