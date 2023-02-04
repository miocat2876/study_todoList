package com.example.famback.fam.common.controller;

import com.example.famback.error.custom.CustomException;
import com.example.famback.error.custom.defaultException.CustomDefaultCodeType;
import com.example.famback.error.custom.CustomCodeGroup;
import com.example.famback.fam.common.domain.CommonDomain;
import com.example.famback.fam.common.request.CommonRequest;
import com.example.famback.fam.common.service.CommonService;
import com.example.famback.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommonController {
	private final CommonService commonService;

	@GetMapping("/common-code")
	public ResponseEntity<List<CommonDomain>> commonCodeList(){
		return new ResponseEntity<>(commonService.commonList(), new HttpHeaders(), HttpStatus.OK.value());
	}
	@GetMapping("/common-code/{codeKey}")
	public ResponseEntity<ResponseDto<Map<String, List<CommonDomain>>>> findByCommonCode(@PathVariable String codeKey, CommonRequest commonRequest) throws CustomException {
		CustomCodeGroup customCodeGroup = CustomDefaultCodeType.FAIL;
		commonRequest.setCodeKey(codeKey);
		Map<String, List<CommonDomain>> commonCodeMap = commonService.findByCommonCode(commonRequest);
		if(commonCodeMap != null){
			customCodeGroup = CustomDefaultCodeType.SUCCESS;
		}
		return new ResponseEntity<>(ResponseDto.<Map<String, List<CommonDomain>>>builder()
			.body(commonCodeMap)
			.customCodeGroup(customCodeGroup)
			.build(),
			new HttpHeaders(),
			HttpStatus.OK.value());
	}
}
