package com.arthur.hesuanbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthur.hesuanbackend.common.BaseResponse;
import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.arthur.hesuanbackend.model.domain.Inform;
import com.arthur.hesuanbackend.model.domain.User;
import com.arthur.hesuanbackend.model.request.UserLoginRequest;
import com.arthur.hesuanbackend.model.request.UserRegisterRequest;
import com.arthur.hesuanbackend.service.UserService;
import com.arthur.hesuanbackend.utils.ResultUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.arthur.hesuanbackend.constant.userConstant.ADMIN_ROLE;
import static com.arthur.hesuanbackend.constant.userConstant.user_Login_Status;


/**
 * 用户接口
 * @author  Arthur
 */
@RestController
@RequestMapping("/user")
public class UserController {





    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount =userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result=  userService.UserRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount  =userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"请求数据为空");
        }
        User user = userService.UserLogin(userAccount, userPassword, request);

        return ResultUtils.success(user);
    }



    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        // 只是取到登录的凭证
        Object userObj = request.getSession().getAttribute(user_Login_Status);
        User CurrentUser = (User) userObj;
        if(CurrentUser==null)
        {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userID = CurrentUser.getId();
        //记得优化
        // todo 校验用户是否合法
        User user = userService.getById(userID);

        User result = userService.getSafetyUser(user);
        return ResultUtils.success(result);

    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username,HttpServletRequest HttpRequest){

        if(!isAdmin(HttpRequest)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("userName",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Integer  userId){
       if(userId<=0){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
       }
       boolean result = userService.removeById(userId);
       return ResultUtils.success(result);
    }

    /**
     *
     * @param HttpRequest
     * @return 是否为管理员
     */

    private boolean isAdmin(HttpServletRequest HttpRequest){

        Object userObj =  HttpRequest.getSession().getAttribute(user_Login_Status);
        User user = (User) userObj;
        return user!=null && user.getUserRole()==ADMIN_ROLE;
    }

}
