package com.yao.geek.user.service.detail;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.common.exception.NoAttention;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.user.common.MapStructUser;
import com.yao.geek.user.model.dto.UserDetailDto;
import com.yao.geek.user.model.entity.UserDetailEntity;
import com.yao.geek.user.model.vo.UserDetailVo;
import com.yao.geek.user.service.fan.UserAttentionIService;
import com.yao.geek.user.service.fan.UserAttentionService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户详情服务
 */
@Service
public class UserDetailService extends ServiceImpl<UserDetailMapper, UserDetailEntity> implements UserDetailIService{
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();
    private final MapStructUser mapStructDetail;
    private final UserAttentionIService userAttentionIService;

    public UserDetailService(MapStructUser mapStructDetail, UserAttentionIService userAttentionIService) {
        this.mapStructDetail = mapStructDetail;
        this.userAttentionIService = userAttentionIService;
    }

    //获取用户信息
    public UserDetailVo getUserDetail(Long id) {
        B_LOGGER.info("获取用户信息,id:{}", id);

        UserDetailEntity userDetailEntity = getById(id);

        B_LOGGER.info("获取用户信息成功");
        return mapStructDetail.toUserDetailVo(userDetailEntity);
    }

    //更新用户信息
    @Transactional
    public void updateUserDetail(Long id,UserDetailDto userDetailVoDto) {
        B_LOGGER.info("更新用户信息,id:{}",id);

        lambdaUpdate()
                .eq(UserDetailEntity::getId, id)
                .set(userDetailVoDto.getRealName() != null, UserDetailEntity::getRealName, userDetailVoDto.getRealName())  // 真实姓名
                .set(userDetailVoDto.getBirthday() != null, UserDetailEntity::getBirthday, userDetailVoDto.getBirthday())  // 生日
                .set(userDetailVoDto.getLocation() != null, UserDetailEntity::getLocation, userDetailVoDto.getLocation())  // 所在地
                .set(userDetailVoDto.getCompany() != null, UserDetailEntity::getCompany, userDetailVoDto.getCompany())  // 公司
                .set(userDetailVoDto.getPosition() != null, UserDetailEntity::getPosition, userDetailVoDto.getPosition())  // 职位
                .set(userDetailVoDto.getWebsite() != null, UserDetailEntity::getWebsite, userDetailVoDto.getWebsite())  // 个人网站
                .set(userDetailVoDto.getGithub() != null, UserDetailEntity::getGithub, userDetailVoDto.getGithub())  // GitHub账户
                .set(userDetailVoDto.getWeibo() != null, UserDetailEntity::getWeibo, userDetailVoDto.getWeibo())  // 微博账户
                .set(userDetailVoDto.getBio() != null, UserDetailEntity::getBio, userDetailVoDto.getBio())  // 个人简介
                .set(userDetailVoDto.getSkills() != null, UserDetailEntity::getSkills, userDetailVoDto.getSkills())  // 技能标签
                .update();
    }

    //创建默认用户信息
    @Transactional
    public void createDefaultUserDetail(Long id) {
        B_LOGGER.info("创建默认用户信息,id:{}",id);

        save(UserDetailEntity.builder()
                .id(id)
                .build());
    }

    //删除用户信息
    @Transactional
    public void deleteUserDetail(Long id) {
        B_LOGGER.info("删除用户信息,id:{}",id);

        removeById(id);
    }

    //根据关注表看是否能获取其他用户信息
    public UserDetailVo getOtherUserDetail(Long attentionId, Long beAttentionId) {
        if(!userAttentionIService.isAttention(attentionId, beAttentionId)){
            throw new NoAttention(StatusCode.NO_ATTENTION);
        }

        return getUserDetail(beAttentionId);
    }
}
