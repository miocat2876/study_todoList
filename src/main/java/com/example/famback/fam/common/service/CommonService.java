package com.example.famback.fam.common.service;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.NotRequiredDataException;
import com.example.famback.fam.common.domain.CommonDomain;
import com.example.famback.fam.common.mapper.CommonMapper;
import com.example.famback.fam.common.request.CommonRequest;
import com.example.famback.fam.member.exception.NotMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonService {
	private final CommonMapper commonMapper;
	public List<CommonDomain> commonList(){
		return commonMapper.commonList();
	}
	public Map<String, List<CommonDomain>> findByCommonCode(CommonRequest commonRequest) throws CustomException {
		if(commonRequest == null || commonRequest.getCodeKey() == null) throw new NotRequiredDataException();
		CommonDomain commonDomain = CommonDomain.builder()
				.codeKeys(commonRequest.getCodeKey().split(","))
				.build();
		Map<String, List<CommonDomain>> resultMap = commonMapper.findByCommonCode(commonDomain).stream()
		.collect(Collectors.groupingBy(CommonDomain::getCodeGroup));
		if(resultMap.size() == 0){
			return null;
		}
		return resultMap;
	}
}
