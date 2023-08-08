package com.example.let.service;

import com.example.let.domain.UploadedFile;
import com.example.let.domain.User;
import com.example.let.domain.UserForMeal;
import com.example.let.domain.req.PasswordChangeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileUploadService {

	/**
	 * 파일을 등록한다.
	 *
	 * @param multipartFile 업로드 된 파일
	 * @return 파일 idx
	 */
	public UploadedFile registerFile(MultipartFile multipartFile, String id);


	/**
	 * 파일을 읽어온다.
	 *
	 * @param idx idx
	 * @return 파일 정보
	 */
	public UploadedFile getFile(Long idx);

	/**
	 * 주어진 파일 정보에 따라 실제 파일을 되돌린다.
	 *
	 * @param file 파일 정보
	 * @return 실제 파일
	 */
	public File getPhysicalFile(UploadedFile file);

	/**
	 * 주어진 파일을 삭제한다.
	 *
	 * @param fileIdx 파일 idx
	 */
	public void deleteFile(Long fileIdx);
	/**
	 * excel을 통한 신청 일괄 등록
	 *
	 * @param
	 * @return
	 */
	public List<User> setMealBundle(MultipartFile multipartFile) throws IOException;
	/**
	 * excel을 통한 신청 일괄 등록 양식 반환
	 *
	 * @param
	 * @return
	 */
	public File getMealBundleForm() throws IOException;
}