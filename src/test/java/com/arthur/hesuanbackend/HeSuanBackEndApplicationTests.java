package com.arthur.hesuanbackend;

import com.arthur.hesuanbackend.mapper.InformMapper;
import com.arthur.hesuanbackend.mapper.UserMapper;
import com.arthur.hesuanbackend.model.domain.User;
import com.arthur.hesuanbackend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@SpringBootTest
class HeSuanBackEndApplicationTests {

    @Resource
    private UserService userService;

    @Resource
    private InformMapper informMapper;

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void testDate(){
        // 2.密码加工
        String userPassword="12345678";
        String newPassword = DigestUtils.md5DigestAsHex(("Arthur"+userPassword).getBytes());
        //查询用户和密码是否存在
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        Object userAccount = "arthur";
        queryWrapper.eq("userName",userAccount);
        queryWrapper.eq("userPassword",newPassword);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    void testTime(){
        long nextTimestamp = System.currentTimeMillis();
        int userId = Integer.parseInt(String.valueOf(nextTimestamp).substring(5));
        System.out.println(userId);
    }

    @Test
    void test1(){

    }


}
