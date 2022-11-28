package com.arthur.hesuanbackend.service;

import com.arthur.hesuanbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author ArthurEvans
* @description 针对表【User】的数据库操作Service
* @createDate 2022-11-21 14:57:55
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount  用户账户
     * @param userPassword  用户密码
     * @param checkPassword 用户校验密码
     * @return 新用户ID
     */
    long UserRegister(String userAccount,String userPassword,String checkPassword);

    /**
     *   用户登录
     * @param userAccount 用户账户
     * @param userPassword  用户密码
     * @return 用户信息（脱敏）
     */

    User UserLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     *
     * @param user   用户
     * @return 用户信息（脱敏）
     */
    User getSafetyUser(User user);


    int userLogout(HttpServletRequest request);
}
