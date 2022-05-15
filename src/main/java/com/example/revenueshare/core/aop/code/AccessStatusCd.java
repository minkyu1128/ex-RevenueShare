package com.example.revenueshare.core.aop.code;

import com.example.revenueshare.core.code.CodeMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccessStatusCd implements CodeMapperType {

	noAuth("권한없음")
	,req("요청")
	,cmplt("처리완료")
	,fail("처리실패")
	;
	
	@Getter
	private final String codeNm;
	
	@Override
	public String getCode() {
		return this.name();
	}

}
