package com.yao.geek.user.service.fan;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.common.exception.HaveAttention;
import com.yao.geek.common.exception.NoAttention;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.user.common.FeignUser;
import com.yao.geek.user.model.entity.UserAttentionEntity;
import com.yao.geek.user.model.vo.UserBaseDetailVo;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户关注服务实现类
 */
@Service
public class UserAttentionIService extends ServiceImpl<UserAttentionMapper, UserAttentionEntity> implements UserAttentionService{
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();

    private final FeignUser feignAuth;

    public UserAttentionIService(FeignUser feignAuth) {
        this.feignAuth = feignAuth;
    }

    //关注用户
    @Transactional
    public boolean attentionUser(Long attentionId, Long beAttentionId) {
        if(isAttention(attentionId, beAttentionId)){
            throw new HaveAttention(StatusCode.HAVE_ATTENTION);
        }

        B_LOGGER.info("关注用户,attentionId:{},beAttentionId:{}", attentionId, beAttentionId);

        return save(UserAttentionEntity.builder()
                .attentionId(attentionId)
                .beAttentionId(beAttentionId)
                .build());
    }

    //取消关注
    @Transactional
    public boolean cancelAttention(Long attentionId, Long beAttentionId) {
        if(!isAttention(attentionId, beAttentionId)){
            throw new NoAttention(StatusCode.NO_ATTENTION);
        }

        B_LOGGER.info("取消关注,attentionId:{},beAttentionId:{}", attentionId, beAttentionId);

        return remove(lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId));
    }

    //删除用户所有关注信息
    @Transactional
    public boolean deleteUserAttention(Long attentionId) {
        B_LOGGER.info("删除用户所有关注信息,attentionId:{}", attentionId);

        return remove(lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId));
    }

    //获取关注列表分两步查询,应该分页
    public List<UserBaseDetailVo> getAttentionIdList(Long attentionId) {
        B_LOGGER.info("获取关注列表,attentionId:{}", attentionId);

        //先获取关注的id
        List<Long> beAttentionIdList = lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .select(UserAttentionEntity::getBeAttentionId)
                .list()    //为空返回一个空集合
                .stream()
                .map(UserAttentionEntity::getBeAttentionId)
                .toList();

        B_LOGGER.info("获取关注列表");

        if(beAttentionIdList.isEmpty()){
            B_LOGGER.info("关注列表为空");

            return List.of();
        }

        return feignAuth.getUserBaseDetail(beAttentionIdList);
    }

    //获取粉丝列表，应该分页
    public List<UserBaseDetailVo> getFansIdList(Long beAttentionId) {
        B_LOGGER.info("获取粉丝列表,beAttentionId:{}", beAttentionId);

        List<Long> attentionIdList = lambdaQuery()
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId)
                .select(UserAttentionEntity::getAttentionId)
                .list()    //为空返回一个空集合
                .stream()
                .map(UserAttentionEntity::getAttentionId)
                .toList();

        if(attentionIdList.isEmpty()){
            B_LOGGER.info("粉丝列表为空");

            return List.of();
        }

        return feignAuth.getUserBaseDetail(attentionIdList);
    }

    //判断是否关注
    public boolean isAttention(Long attentionId, Long beAttentionId) {
        return lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId)
                .exists();
    }
}
