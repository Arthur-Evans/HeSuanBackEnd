package com.arthur.hesuanbackend.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author  Arthur
 */
@Data
public class UserRegisterRequest implements Serializable {

    //用户账号
    private String userAccount;
    //用户密码
    private String userPassword;
    //密码校验
    private String checkPassword;


}
