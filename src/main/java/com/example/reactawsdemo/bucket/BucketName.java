package com.example.reactawsdemo.bucket;

public enum BucketName {
	PROFILE_IMAGE("react-spring-demo");

	private final String bucketName;

	BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}
}
