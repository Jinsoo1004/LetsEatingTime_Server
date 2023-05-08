package com.example.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * 업로드 된 파일을 나타낸다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile extends ManagableEntity {
	
	/**
	 * Idx 
	 */
	private Long fileIdx;
	
	/**
	 * 원래 파일 이름
	 */
	private String originalFileName;
	
	/**
	 * 실제 파일 이름
	 */
	@JsonIgnore
	private String fileName;
	
	/**
	 * 파일 크기 
	 */
	private Long size;
	
	/**
	 * Content type
	 */
	private String contentType;
	
	public String getExtension() {
		if (originalFileName == null) {
			return "";
		}

		int index = originalFileName.lastIndexOf(".");
		
		if (index > 0 && (index + 1) < originalFileName.length()) {
			return originalFileName.substring(index + 1);
		}

		return "";
	}
	
}
