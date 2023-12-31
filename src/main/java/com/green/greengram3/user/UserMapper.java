package com.green.greengram3.user;

import com.green.greengram3.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto dto);
    UserSignProcDto selUser(UserSignDto dto);
    UserInfoVo userInfo(UserInfoSelDto userInfoSelDto);
    int upUserPic(int iuser);

    int insFollow(UserFollowDto dto);
    int delFollow(UserFollowDto dto);

    int updUserFirebaseToken(UserFirebaseTokenPatchDto dto);
    int updUserPic(UserPicPatchDto dto);
}
