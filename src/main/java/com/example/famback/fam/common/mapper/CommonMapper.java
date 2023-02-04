package com.example.famback.fam.common.mapper;

import com.example.famback.fam.common.domain.CommonDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {
	List<CommonDomain> commonList();
	List<CommonDomain> findByCommonCode(CommonDomain commonDomain);
}
