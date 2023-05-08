package com.example.let.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Data
public class RegisterTimeEntity {
	
	/**
	 * 등록 시간
	 */
	protected String registerTime;
	
	/**
	 * 등록 시간
	 */
	protected String updateTime;
	
	/**
	 * 삭제 여부
	 */
	protected String deletedYn;
	
	/**
	 * 삭제 시간
	 */
	private String deletedTime;	
	
	
	@JsonIgnore
	public Date getRegisterTimeDate() {
		return getDate(registerTime);
	}
	
	@JsonIgnore
	public Date getUpdateTimeDate() {
		return getDate(updateTime);
	}
	
	@JsonIgnore
	public Date getDeletedTimeDate() {
		return getDate(deletedTime);
	}
	
	private Date getDate(String str) {
		if (str == null) {
			return null;
		}
		
		try {
			TemporalAccessor temporalAccessor = 
					DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str);
			Instant instant = Instant.from(temporalAccessor);
			return Date.from(instant);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
