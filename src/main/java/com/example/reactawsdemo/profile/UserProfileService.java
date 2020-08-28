package com.example.reactawsdemo.profile;

import com.example.reactawsdemo.bucket.BucketName;
import com.example.reactawsdemo.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

	public void uploadUserProfileImage(UUID userProfileId, MultipartFile file){
		// check if the image is empty
		isFileEmpty(file);

		// check if file is image
		isImage(file);

		// check if the user exists in db
		UserProfile user = getUserProfileOrThrow(userProfileId);

		// grab the metadata from the file
		Map<String, String> metadata = extractMetaData(file);

		// insert the file to the bucket
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
		try {
			fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private Map<String, String> extractMetaData(MultipartFile file) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));
		return metadata;
	}

	private UserProfile getUserProfileOrThrow(UUID userProfileId) {
		return userProfileDataAccessService
				.getUserProfiles()
				.stream()
				.filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
	}

	private void isImage(MultipartFile file) {
		if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_GIF.getMimeType(), ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())){
			throw new IllegalStateException("file must be an image");
		}
	}

	private void isFileEmpty(MultipartFile file) {
		if(file.isEmpty()){
			throw new IllegalStateException("can't upload empty file [" + file.getSize() + "]");
		}
	}
}
