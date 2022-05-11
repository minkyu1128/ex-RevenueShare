package com.example.revenueshare.core.mapstruct;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * 특정 타입이나 객체간 매핑을 MapStruct 스스로할 수 없거나 다른 Mapper를 이용해야 한다면 'uses'를 사용할 수 있다.
 * 'uses = JsonMapper.class'로 지정하면 객체에서 String으로 변환이 필요할 때 JsonMapper를 사용한다.
 * spring bean으로 등록하기 위해 componentModel = "spring" 속성 필수
 * [참고사이트](https://ryanwoo.tistory.com/240)
 */
@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE) //, builder = @Builder(disableBuilder = true)) //, uses = JsonMapper.class) //, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = JsonMapper.class)
public class StructMapperConfig {
}
