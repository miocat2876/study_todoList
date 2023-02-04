package com.example.famback.fam.user.mapper;

import com.example.famback.fam.user.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int createUser(UserDomain userDomain);
    String findLastUserKey();
    String findByMemberClassFkToMemberFK(UserDomain userDomain);
    int updateUser(UserDomain userDomain);
    UserDomain findOneByUserKey(UserDomain userDomain);
    List<UserDomain> findUserListByMemberClassKey(UserDomain userDomain);
    List<UserDomain> findClassByMemberKey(UserDomain userDomain);
    String findUserAuthorityCodeByUserKey(UserDomain userDomain);
    int deleteUser(UserDomain userDomain);
    int deleteUserByMemberKey(UserDomain userDomain);
}
