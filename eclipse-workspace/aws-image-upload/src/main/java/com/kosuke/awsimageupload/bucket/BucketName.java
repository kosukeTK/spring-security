package com.kosuke.awsimageupload.bucket;

public enum BucketName {
	
	PROFILE_IMAGE("aws-image-upload-z1");
	
	private final String bucketName;
	
	private BucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	
}

