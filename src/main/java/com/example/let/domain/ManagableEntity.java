package com.example.let.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagableEntity extends RegisterTimeEntity {
	
	/**
	 * 등록자
	 */
	protected User registrant;
	
	/**
	 * 활성화 여부
	 */
	protected String enabledYn;
	
}
