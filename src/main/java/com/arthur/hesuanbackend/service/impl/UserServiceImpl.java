package com.arthur.hesuanbackend.service.impl;

import com.arthur.hesuanbackend.common.ErrorCode;
import com.arthur.hesuanbackend.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arthur.hesuanbackend.model.domain.User;
import com.arthur.hesuanbackend.service.UserService;
import com.arthur.hesuanbackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.arthur.hesuanbackend.constant.userConstant.user_Login_Status;

/**
* @author ArthurEvans
* @description 针对表【User】的数据库操作Service实现
* @createDate 2022-11-21 14:57:55
*/

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    /**
     * 盐值 混淆密码
     */
    private static final String SALT  = "Arthur";

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录状态值
     */

    @Override
    public long UserRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.用户校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号小于4位");
        }
        if(userPassword.length()<8||checkPassword.length()<8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }

        // 校验账户不能包含特殊字符
        /**
         * 用户名校验
         * 必须是4-17位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
         * 不能以数字开头
         */
        String value_account = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(value_account).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号不能包含特殊字符");
        }

        // 密码和校验密码相同
        if(!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户两次密码不一致");

        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("userName",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count>0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户已重复注册");
        }


        //2.加密

        String newPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        System.out.println(newPassword);
        // 3. 向数据库插入用户
        User user =new User();
        user.setUserName(userAccount);
        user.setUserPassword(newPassword);
        boolean saveresult = this.save(user);
        if(!saveresult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库插入失败");
        }
        return user.getId();

    }


    @Override
    public User UserLogin(String userAccount, String userPassword,  HttpServletRequest request) {
        // 1.用户校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号小于4位");
        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        String value_account = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(value_account).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号不能包含特殊字符");
        }


        // 2.密码加工
        String newPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        //查询用户和密码是否存在
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("userName",userAccount);
        queryWrapper.eq("userPassword",newPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user == null){
            log.info("user login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号和密码不匹配");
        }
        // 3.数据脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 如何记录用户的登陆状态
        request.getSession().setAttribute(user_Login_Status,safetyUser);
        System.out.println(safetyUser);
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User user){
        if(user==null)
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"获取的user为空");
        User clearUser =new User();
        clearUser.setId(user.getId());
        clearUser.setUserRealName(user.getUserRealName());
        clearUser.setUserName(user.getUserName());
        clearUser.setUserProfile(user.getUserProfile());
        clearUser.setUserEmail(user.getUserEmail());
        clearUser.setUserStatus(user.getUserStatus());
        clearUser.setUserCreateTime(user.getUserCreateTime());
        clearUser.setUserRole(user.getUserRole());
        return clearUser;
    }


    /**
     * 请求用户注销
     * @param request 发送一个请求
     * @return 标识符
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(user_Login_Status);
        //移除登录态
        return 1;
    }

}




