package com.yao.geek.user.service.fan;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.common.exception.HaveAttention;
import com.yao.geek.common.exception.NoAttention;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import com.yao.geek.user.common.FeignUser;
import com.yao.geek.user.model.entity.UserAttentionEntity;
import com.yao.geek.user.model.query.UserQuery;
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

    private final FeignUser feignUser;

    public UserAttentionIService(FeignUser feignAuth) {
        this.feignUser = feignAuth;
    }

    //关注用户
    @Transactional
    public void attentionUser(Long attentionId, Long beAttentionId) {
        if(isAttention(attentionId, beAttentionId)){
            throw new HaveAttention(StatusCode.HAVE_ATTENTION);
        }

        B_LOGGER.info("关注用户,attentionId:{},beAttentionId:{}", attentionId, beAttentionId);

        save(UserAttentionEntity.builder()
                .attentionId(attentionId)
                .beAttentionId(beAttentionId)
                .build());
    }

    //取消关注
    @Transactional
    public void cancelAttention(Long attentionId, Long beAttentionId) {
        if(!isAttention(attentionId, beAttentionId)){
            throw new NoAttention(StatusCode.NO_ATTENTION);
        }

        B_LOGGER.info("取消关注,attentionId:{},beAttentionId:{}", attentionId, beAttentionId);

         remove(Wrappers.lambdaQuery(UserAttentionEntity.class)
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId));
    }

    //删除用户所有关注信息
    @Transactional
    public void deleteUserAttention(Long attentionId) {
        B_LOGGER.info("删除用户所有关注信息,attentionId:{}", attentionId);

        remove(Wrappers.lambdaQuery(UserAttentionEntity.class)
                .eq(UserAttentionEntity::getAttentionId, attentionId));
    }

    //获取关注列表分两步查询,应该分页(跨服务还是分两步查询)
    public Page<UserBaseDetailVo> getAttentionIdList(Long attentionId, UserQuery query) {
        B_LOGGER.info("获取关注列表,attentionId:{}", attentionId);

        Page<UserAttentionEntity> page = new Page<>(query.getPage(), query.getSize());

        Page<UserAttentionEntity> pageIds=lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .select(UserAttentionEntity::getBeAttentionId)
                .page(page);

        if(pageIds.getRecords().isEmpty()){
            B_LOGGER.info("关注列表为空");

            return new Page<>(query.getPage(), query.getSize(),0);
        }

        //先获取关注的id
        List<Long> ids = pageIds.getRecords().stream()
                .map(UserAttentionEntity::getBeAttentionId)
                .toList();

        B_LOGGER.info("获取关注列表成功");

        return feignUser.getUserBaseDetail(ids, query);
    }

    //获取粉丝列表，应该分页
    public Page<UserBaseDetailVo> getFansIdList(Long beAttentionId, UserQuery query) {
        B_LOGGER.info("获取粉丝列表,beAttentionId:{}", beAttentionId);

        Page<UserAttentionEntity> page = new Page<>(query.getPage(), query.getSize());

        Page<UserAttentionEntity> pageIds=lambdaQuery()
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId)
                .select(UserAttentionEntity::getAttentionId)
                .page(page);

        if(pageIds.getRecords().isEmpty()){
            B_LOGGER.info("粉丝列表为空");

            return new Page<>(query.getPage(), query.getSize(), 0);
        }

        List<Long> attentionIdList = pageIds.getRecords()
                .stream()
                .map(UserAttentionEntity::getAttentionId)
                .toList();


        return feignUser.getUserBaseDetail(attentionIdList, query);
    }

    //判断是否关注
    public boolean isAttention(Long attentionId, Long beAttentionId) {
        return lambdaQuery()
                .eq(UserAttentionEntity::getAttentionId, attentionId)
                .eq(UserAttentionEntity::getBeAttentionId, beAttentionId)
                .exists();
    }
}
