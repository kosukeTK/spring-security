package com.kosuke.awsimageupload.profile;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.client.builder.AdvancedConfig.Key;
import com.kosuke.awsimageupload.bucket.BucketName;
import com.kosuke.awsimageupload.filestore.FileStore;

//import com.amazonaws.services.cloudsearchdomain.model.ContentType;

/**
 * UserProfileDataAccessServiceクラスからデータを取得するクラス
 * @author torit
 *
 */
@Service
public class UserProfileService {
	
	private final UserProfileDataAccessService userProfileDataAccessService;
	private final FileStore fileStore;
	
	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
							  FileStore fileStore) {
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}
	
	List<UserProfile> getUserProfiles() {
		return userProfileDataAccessService.getUserProfiles();
	}
	
	void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
		//Fileが空じゃないかチェック
		if (file.isEmpty()) {
			throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
		}
		//FileがImageであるかチェック
		if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), 
						   ContentType.IMAGE_PNG.getMimeType(),
						   ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
			throw new IllegalStateException("File must be an image");
		}
		//database上にユーザが存在するかチェック
		UserProfile user = userProfileDataAccessService
			.getUserProfiles()
			.stream() // 集める
			.filter(userProfile -> userProfile.getUserprofileId().equals(userProfileId)) // ユーザIDが同じものを
			.findFirst() //最初の行の
			.orElseThrow(() -> new IllegalStateException(String.format("User profile $s not found", userProfileId)));
		
		//ファイルからメタデータを取得する
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));
		
		//S3に画像を保存 and databaseを更新
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserprofileId());
		String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
		
		try {
			//ImageFileを保存
			fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
			//ダウンロードできるようにlinkを設定
			user.setUserProfileImageLink(filename);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public byte[] downloadUserProfileImage(UUID userProfileId) {
		UserProfile user = userProfileDataAccessService
				.getUserProfiles()
				.stream() // 集める
				.filter(userProfile -> userProfile.getUserprofileId().equals(userProfileId)) // ユーザIDが同じものを
				.findFirst() //最初の行の
				.orElseThrow(() -> new IllegalStateException(String.format("User profile $s not found", userProfileId)));
		String path = String.format("%s/%s",
									BucketName.PROFILE_IMAGE.getBucketName(),
									user.getUserprofileId());
		
		return user.getUserProfileImageLink()
				.map(Key -> fileStore.download(path, Key))
				.orElse(new byte[0]);
	}
}
