package com.speer.service;

import com.speer.dto.AuthUserResDto;
import com.speer.dto.ResponseDto;
import com.speer.dto.UserCredentialDto;
import com.speer.dto.UserReqDto;

public interface UserService {
    ResponseDto signUp(UserReqDto userReqDto);
    AuthUserResDto login(UserCredentialDto userCredentialDto);
}
