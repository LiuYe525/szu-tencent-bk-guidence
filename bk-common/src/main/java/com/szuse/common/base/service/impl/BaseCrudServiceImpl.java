package com.szuse.common.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szuse.common.base.entity.BaseEntity;
import com.szuse.common.base.enums.RedisCacheEnum;
import com.szuse.common.base.service.IBaseCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiuYe
 * @version 1.0
 * @date 5/4/2023 上午9:10
 * Entity 实体类型
 * User 用户类型
 * LikeRecord 点赞实体类型
 * CollectRecord 收藏实体类型
 * UserIdType 用户id类型
 * EntityIdType 实体id类型
 * Mapper 实体对应 Mp Mapper
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseCrudServiceImpl<Mapper extends BaseMapper<Entity>,
        Entity extends BaseEntity> extends ServiceImpl<Mapper, Entity>
        implements IBaseCrudService<Entity> {

    protected final Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    protected final RedisTemplate<String, Object> redisTemplate;
    //用户表mapper
    private final BaseEntity baseEntity;
    private final RedisCacheClient cacheClient;

    public BaseCrudServiceImpl(RedisCacheClient cacheClient) throws InstantiationException, IllegalAccessException {
        this.baseEntity = entityClass.newInstance();
        this.cacheClient = cacheClient;
        this.redisTemplate = this.cacheClient.getRedisTemplate();
    }

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean removeById(Serializable id) {
        Serializable key = RedisCacheEnum.getKeyByClass(entityClass, id);
        log.debug("basic-crud-remove: redis-key: {}", key);
        return cacheClient.removeById(key, id, super::removeById);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        List<Serializable> keys = idList.stream().map(id -> RedisCacheEnum.getKeyByClass(entityClass, id)).collect(Collectors.toList());
        log.debug("basic-crud-remove: redis-key: {}", keys);
        return cacheClient.removeBatchIds(keys, idList, super::removeByIds);
    }

    @Override
    public boolean updateById(Entity entity) {
        Serializable key = RedisCacheEnum.getKeyByClass(entityClass, entity.getEntityId());
        log.debug("basic-crud-update: redis-key: {}", key);
        return cacheClient.updateById(key, entity, super::updateById);
    }

    @Override
    public boolean updateBatchById(Collection<Entity> entityList) {
        List<Serializable> keys = entityList.stream().map(entity -> RedisCacheEnum.getKeyByClass(entityClass, entity.getEntityId())).collect(Collectors.toList());
        log.debug("basic-crud-update: redis-key: {}", keys);
        return cacheClient.updateBatchIds(keys, entityList, super::updateBatchById);
    }

    @Override
    public Entity getById(Serializable id) {
        Serializable key = RedisCacheEnum.getKeyByClass(entityClass, id);
        log.debug("basic-crud-one: redis-key: {}", key);
        return cacheClient.getById(key, id, super::getById);
    }

    @Override
    public Collection<Entity> listByIds(Collection<? extends Serializable> idList) {
        List<Serializable> keys = idList.stream().map(id -> RedisCacheEnum.getKeyByClass(entityClass, id)).collect(Collectors.toList());
        log.debug("basic-crud-list: redis-key: {}", keys);
        return cacheClient.listBatchIds(keys, idList, super::listByIds);
    }

    @Override
    public List<Entity> list() {
        System.out.println("no pram");
        Serializable key = RedisCacheEnum.getListKey(entityClass);
        log.debug("basic-crud-page: redis-key: {}", key);
        return cacheClient.listWithWrapper(key,() -> super.list(Wrappers.emptyWrapper()));
    }

    @Override
    public List<Entity> list(Wrapper<Entity> queryWrapper) {
        AbstractWrapper wrapper = (AbstractWrapper) queryWrapper;
        int hash = String.valueOf(wrapper.getSqlSegment().hashCode() + wrapper.getParamNameValuePairs().hashCode()).hashCode();
        Serializable key = RedisCacheEnum.getListWithWrapper(entityClass, hash);
        log.debug("basic-crud-list-wrapper: redis-key: {}", key);
        return cacheClient.listWithWrapper(key, () -> super.list(queryWrapper));
    }

    @Override
    public IPage<Entity> page(IPage<Entity> page) {
        Serializable key = RedisCacheEnum.getPageKey(entityClass, page.getCurrent(), page.getSize());
        log.debug("basic-crud-page: redis-key: {}", key);
        IPage<Entity> finalPage = page;
        page = cacheClient.page(key, finalPage, () -> super.page(finalPage, Wrappers.emptyWrapper()));
        return page;
    }

    @Override
    public IPage<Entity> page(IPage<Entity> page, Wrapper<Entity> queryWrapper) {
        if (queryWrapper.isEmptyOfWhere()) {
            return this.page(page);
        }
        AbstractWrapper wrapper = (AbstractWrapper) queryWrapper;
        int hash = String.valueOf(wrapper.getSqlSegment().hashCode() + wrapper.getParamNameValuePairs().hashCode()).hashCode();
        Serializable key = RedisCacheEnum.getPageKeyWithWrapper(entityClass, page.getCurrent(), page.getSize(), hash);
        log.debug("basic-crud-page-wrapper: redis-key: {}", key);
        return cacheClient.page(key, page, () -> super.page(page, queryWrapper));
    }

    //------------------------------------------------------------------------------------------------------------------
}
