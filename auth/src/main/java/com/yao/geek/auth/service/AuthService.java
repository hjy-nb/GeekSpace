package com.yao.geek.auth.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.auth.common.FeignAuth;
import com.yao.geek.auth.common.MapStructAuth;
import com.yao.geek.auth.model.dto.LoginDto;
import com.yao.geek.auth.model.dto.RegisterDto;
import com.yao.geek.auth.model.dto.UserOutDto;
import com.yao.geek.auth.model.entity.UserEntity;
import com.yao.geek.auth.model.entity.UserRoleEntity;
import com.yao.geek.auth.model.query.NicknameQuery;
import com.yao.geek.auth.model.query.UserQuery;
import com.yao.geek.auth.model.vo.LoginVo;
import com.yao.geek.auth.model.vo.UserBaseDetailVo;
import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.common.exception.*;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.common.token.SecretKeyCreate;
import com.yao.geek.common.token.TokenCreate;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务类
 */
//验证码还缺redis，注册时判断验证码也要redis
@Service
public class AuthService extends ServiceImpl<UserMapper,UserEntity> implements UserIService{
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();

    private final BCryptPasswordEncoder encoder;    // 密码加密
    private final UserRoleMapper userRoleMapper;
    private final MapStructAuth mapper;
    private final FeignAuth feignAuth;
    private final UserMapper userMapper;

    public AuthService(BCryptPasswordEncoder encoder, UserRoleMapper userRoleMapper, MapStructAuth mapper,
                       FeignAuth feignAuth,UserMapper userMapper) {
        this.encoder = encoder;
        this.userRoleMapper = userRoleMapper;
        this.mapper = mapper;
        this.feignAuth = feignAuth;
        this.userMapper = userMapper;
    }

    //用户登录
    public LoginVo login(LoginDto loginDto) {
        B_LOGGER.info("用户请求登录,用户名：{}", loginDto.getUsername());

        UserEntity user= lambdaQuery().eq(UserEntity::getUsername, loginDto.getUsername())
                .one();

        if(user==null){
            throw new NoUsername(StatusCode.USERNAME_ERROR);
        }

        if(!encoder.matches(loginDto.getPassword(), user.getPasswordHash())){
            throw new ErrorPassword(StatusCode.PASSWORD_ERROR);
        }

        B_LOGGER.info("用户登录验证成功,userId:{}", user.getId());

        lambdaUpdate().eq(UserEntity::getId, user.getId())
                .set(UserEntity::getLastLoginTime, LocalDateTime.now())
                .update();

        B_LOGGER.info("最后登录时间已更新");

        List<String> authority = userMapper.getAuthority(user.getId()); // 获取权限
        SecretKey key = SecretKeyCreate.createSecretKey();  // 密钥
        String token = TokenCreate.createToken(user.getId(), authority, key); // 生成token

        B_LOGGER.info("token生成成功");

        return LoginVo.builder()
                .userId(user.getId())
                .token(token)
                .build();
    }

    //用户注册
    @Transactional
    public LoginVo register(RegisterDto registerDto) {
        B_LOGGER.info("用户请求注册,用户名：{}", registerDto.getUsername());

        if(UsernameIsExist(registerDto.getUsername())){
            throw new ExistUsername(StatusCode.USERNAME_EXIST);
        }

        if(EmailIsExist(registerDto.getEmail())){
            throw new ExistEmail(StatusCode.EMAIL_EXIST);
        }

        if(PhoneIsExist(registerDto.getPhone())){
            throw new ExistPhone(StatusCode.PHONE_EXIST);
        }

        if(!registerDto.getCode().equals("aaaa")){
            throw new ErrorCode(StatusCode.CODE_ERROR);
        }

        //构建用户
        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())   // 用户名
                .passwordHash(encoder.encode(registerDto.getPassword()))  // 密码
                .email(registerDto.getEmail())  // 邮箱
                .phone(registerDto.getPhone())  // 手机号
                .nickname(registerDto.getNickname())  // 昵称
                .status(1)  // 状态
                .lastLoginTime(LocalDateTime.now())  // 最后登录时间
                .avatar(NumConstant.DEFAULT_AVATAR_URL)
                .build();

        save(user);    // 保存用户

        B_LOGGER.info("用户注册信息已保存,用户名：{}", registerDto.getUsername());

        // 构建用户角色
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .userId(user.getId())
                .roleId(NumConstant.DEFAULT_ROLE_ID)
                .build();

        userRoleMapper.insert(userRoleEntity); // 保存用户角色

        B_LOGGER.info("用户默认角色已创建");

        //生成用户基本信息
        feignAuth.createDefaultUserDetail(user.getId());

        B_LOGGER.info("用户基本信息生成成功");

        SecretKey key = SecretKeyCreate.createSecretKey(); // 密钥
        List<String> authority = userRoleMapper.getAuthority(NumConstant.DEFAULT_ROLE_ID);  // 获取默认角色权限
        String token = TokenCreate.createToken(user.getId(), authority, key);

        B_LOGGER.info("token生成");

        return LoginVo.builder()
                .userId(user.getId())
                .token(token)
                .build();
    }

    //昵称查询(模糊匹配可用ELK),要分页
    public Page<UserBaseDetailVo> getUserIdByNickname(NicknameQuery query) {
        B_LOGGER.info("用户请求查询昵称,昵称：{}", query.getNickname());

        Page<UserBaseDetailVo> page=new Page<>(query.getPage(), query.getSize());

        return userMapper.getUserIdByNickname(page, query.getNickname());
    }

    //用户注销
    @Transactional
    public void logout(UserOutDto userOutDto,Long id) {
        B_LOGGER.info("用户请求注销,用户id：{}", id);

        UserEntity user = lambdaQuery().eq(UserEntity::getId, id)
                .one();

        if(!encoder.matches(userOutDto.getPassword(), user.getPasswordHash())){
            throw new ErrorPassword(StatusCode.PASSWORD_ERROR);
        }

        B_LOGGER.info("用户注销验证成功");

        //删除返回值一般是基于删除数据的个数的，不用判断是否成功，因为默认事务行为是加入到一个事务中，如果删除失败，事务都会回滚，所以这里不需要判断是否成功

        // 删除用户角色
        userRoleMapper.delete(new LambdaQueryWrapper<UserRoleEntity>()
                .eq(UserRoleEntity::getUserId, id));

        B_LOGGER.info("用户角色已删除");

        //删除用户信息
        feignAuth.deleteUserDetail(id);

        B_LOGGER.info("用户信息已删除");

        // 删除用户关注信息
        feignAuth.deleteAttention(id);

        B_LOGGER.info("用户关注信息已删除");

        // 软删除用户
        lambdaUpdate().eq(UserEntity::getId, id)
                .set(UserEntity::getStatus, 0)  // 状态禁用
                .set(UserEntity::getUpdateTime, LocalDateTime.now()) // 更新时间
                .update();

        B_LOGGER.info("用户已删除");
    }

    //用户登出即判断token有没有过期，前端直接判断就行

    //批量获取用户基本信息
    public Page<UserBaseDetailVo> getUserBaseDetail(List<Long> ids, UserQuery query) {
        B_LOGGER.info("用户请求获取用户基本信息");

        Page<UserBaseDetailVo> page = new Page<>(query.getPage(), query.getSize());

        return userMapper.getUserBaseDetail(page, ids);
    }

    //获取验证码
    public void getCode(HttpServletResponse response) throws IOException {
        B_LOGGER.info("用户请求获取验证码");

        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(100, 40, 4, 4);
        shearCaptcha.setGenerator(new RandomGenerator(NumConstant.CODE_RANGE,4));

        B_LOGGER.info("验证码生成成功");

        String code = shearCaptcha.getCode();

        // 保存验证码

        shearCaptcha.write(response.getOutputStream());
    }

    //判断用户名是否存在
    public boolean UsernameIsExist(String username) {
        return lambdaQuery().eq(UserEntity::getUsername, username)
                .exists();
    }

    //判断邮箱是否存在
    public boolean EmailIsExist(String email) {
        return lambdaQuery().eq(UserEntity::getEmail, email)
                .exists();
    }

    //判断手机号是否存在
    public boolean PhoneIsExist(String phone) {
        return lambdaQuery().eq(UserEntity::getPhone, phone)
                .exists();
    }


}
