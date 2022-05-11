package com.example.revenueshare.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CodeMapperFactory {

	private Map<String, List<CodeMapperValue>> factory;
	
	public void put(Class<? extends CodeMapperType> e) {
		this.put(e.getSimpleName(), e);
	}
	public void put(String key, Class<? extends CodeMapperType> e) {
		factory.put(key, this.toEnumValue(e));
	}
	
	public List<CodeMapperValue> get(Class<? extends CodeMapperType> e){
		return this.get(e.getSimpleName());
	}
	public List<CodeMapperValue> get(String key){
		return factory.get(key);
	}
	
	
	private List<CodeMapperValue> toEnumValue(Class<? extends CodeMapperType> e){
		return Arrays.stream(e.getEnumConstants())	//CodeMapperType을 구현한 열거형 상수(code/codeNm SET)코드를
				.map(CodeMapperValue::new)			//CodeMapperValue 클래스로 생성하여
				.collect(Collectors.toList());      //List<CodeMapperValue>로 반환
	}
	
}
