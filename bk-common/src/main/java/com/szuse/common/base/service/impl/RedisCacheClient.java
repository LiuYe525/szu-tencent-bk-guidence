package com.szuse.common.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.szuse.common.base.enums.RedisCacheEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author LiuYe
 * @version 1.0
 * @date 2/5/2023 下午8:01
 */
@Slf4j
@Component
@SuppressWarnings("rawtypes,unchecked")
public class RedisCacheClient {
    @Value("${configuration.redis.expiration}")
    private int expireTime;
    private final TimeUnit expireTimeUnit = TimeUnit.MINUTES;
    private final RedisTemplate redisTemplate;

    public RedisCacheClient(@Qualifier(value = "cacheRedisTemplate") RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public <KEY extends Serializable> void set(KEY key, Object value) {
        redisTemplate.opsForValue().set(key, value, expireTime, expireTimeUnit);
    }

    public <T, KEY extends Serializable> T getOrNull(KEY key) {
        T res = (T) redisTemplate.opsForValue().get(key);
        if (ObjectUtils.isEmpty(res)) {
            redisTemplate.opsForValue().set(key, null, expireTime, expireTimeUnit);
            return null;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <KEY extends Serializable> void deleteKey(KEY key) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    public <KEY extends Serializable> void hSet(KEY key, Serializable hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
    }

    public <T> T hGetOrNull(Serializable key, Serializable hashKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return null;
        }
        T res = (T) redisTemplate.opsForHash().get(key, hashKey);
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public Long hIncrement(Serializable key, Serializable hashKey, Long score) {
        Long res = redisTemplate.opsForHash().increment(key, hashKey, score);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public Long hDecrement(Serializable key, Serializable hashKey, Long score) {
        Long res = redisTemplate.opsForHash().increment(key, hashKey, -score);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <V> void zSet(Serializable key, V value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
    }

    public <V> Double zIncrement(Serializable key, V value, Double score) {
        Double res = redisTemplate.opsForZSet().incrementScore(key, value, score);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <V> Double zDecrement(Serializable key, V value, Double score) {
        Double res = redisTemplate.opsForZSet().incrementScore(key, value, -score);
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <KEY extends Serializable> Double zGetScore(KEY key, String setKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return null;
        }
        Double res = redisTemplate.opsForZSet().score(key, setKey);
        if (ObjectUtils.isEmpty(res)) {
            return 0.0;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <T> Collection<T> zRange(Serializable key, long start, long end) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return null;
        }
        Collection res = redisTemplate.opsForZSet().range(key, start, end);
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <T> Collection<ZSetOperations.TypedTuple<T>> zRangeWithScore(Serializable key, long start, long end) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return null;
        }
        Collection res = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <T> Collection<T> sMembers(Serializable key) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return null;
        }
        Collection res = redisTemplate.opsForSet().members(key);
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }
        redisTemplate.expire(key, expireTime, expireTimeUnit);
        return res;
    }

    public <V> Boolean sAdd(Serializable key, V member) {
        redisTemplate.opsForSet().add(key, member);
        return true;
    }

    public <T> Boolean sAdd(Serializable key, Collection<T> members) {
        redisTemplate.executePipelined(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                members.forEach(member -> {
                    operations.opsForSet().add((K) key, (V) member);
                });
                return null;
            }
        });
        return true;
    }

    public <V> Boolean sRemove(Serializable key, V member) {
        redisTemplate.opsForSet().remove(key, member);
        return true;
    }

    public <T> Boolean sRemove(Serializable key, Collection<T> members) {
        redisTemplate.executePipelined(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                members.forEach(member -> {
                    operations.opsForSet().remove((K) key, (V) member);
                });
                return null;
            }
        });
        return true;
    }

    public <T, ID extends Serializable> List<T> pipelinedGet(RedisCacheEnum cacheEnum, Collection<ID> ids) {
        try {
            return redisTemplate.executePipelined(new SessionCallback<T>() {
                @Override
                public <K, V> T execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                    //获取并刷新过期时间
                    ids.forEach(id -> {
                        if (Boolean.FALSE.equals(operations.hasKey((K) cacheEnum.getKeyById(id)))) {
                            throw new DataAccessResourceFailureException("cache not found");
                        }
                        operations.opsForValue().getAndExpire((K) cacheEnum.getKeyById(id), expireTime, expireTimeUnit);
                    });
                    return null;
                }
            });
        } catch (DataAccessException e) {
            return null;
        }
    }

    public <T, Key extends Serializable> List<T> pipelinedGet(Collection<Key> keys) {
        try {
            return redisTemplate.executePipelined(new SessionCallback<T>() {
                @Override
                public <K, V> T execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                    //获取并刷新过期时间
                    keys.forEach(key -> {
                        if (Boolean.FALSE.equals(operations.hasKey((K) key))) {
                            throw new DataAccessResourceFailureException("cache not found");
                        }
                        operations.opsForValue().getAndExpire((K) key, expireTime, expireTimeUnit);
                    });
                    return null;
                }
            });
        } catch (DataAccessException e) {
            return null;
        }
    }

    public <T, ID extends Serializable> void pipelinedSet(RedisCacheEnum cacheEnum, Collection<ID> ids, Collection<T> items) {
        redisTemplate.executePipelined(new SessionCallback<T>() {
            @Override
            public <K, V> T execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                Iterator<ID> idIterator = ids.iterator();
                Iterator<T> itemIterator = items.iterator();
                while (idIterator.hasNext()) {
                    operations.opsForValue().set((K) cacheEnum.getKeyById(idIterator.next()), (V) itemIterator.next(), expireTime, expireTimeUnit);
                }
                return null;
            }
        });
    }

    public <T, KEY extends Serializable> void pipelinedSet(Collection<KEY> keys, Collection<T> items) {
        redisTemplate.executePipelined(new SessionCallback<T>() {
            @Override
            public <K, V> T execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                Iterator<KEY> keyIterator = keys.iterator();
                Iterator<T> itemIterator = items.iterator();
                while (keyIterator.hasNext()) {
                    operations.opsForValue().set((K) keyIterator.next(), (V) itemIterator.next(), expireTime, expireTimeUnit);
                }
                return null;
            }
        });
    }

    public <ID extends Serializable> void pipelinedDelete(RedisCacheEnum cacheEnum, Collection<ID> ids) {
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                ids.forEach(id -> operations.delete((K) cacheEnum.getKeyById(id)));
                return null;
            }
        });
    }

    public <KEY extends Serializable> void pipelinedDelete(Collection<KEY> keys) {
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(@NotNull RedisOperations<K, V> operations) throws DataAccessException {
                keys.forEach(key -> operations.delete((K) key));
                return null;
            }
        });
    }

    //-----------------------------------------cache part---------------------------------------------------------------
    public <T, ID extends Serializable> T getById(Serializable key, ID id, Function<ID, T> dbQueryFunction) {
        // redis查询是否缓存
        T res = (T) redisTemplate.opsForValue().get(key);
        // 如果redis查询到缓存直接返回
        if (!ObjectUtils.isEmpty(res)) {
            redisTemplate.expire(key, expireTime, expireTimeUnit);
            return res;
        }
        // 不存在缓存则查询数据库
        res = dbQueryFunction.apply(id);
        // 数据库也不存在记录,缓存击穿，设空值避免多次击穿
        if (res == null) {
            redisTemplate.opsForValue().set(key, null, expireTime, expireTimeUnit);
            return null;
        }
        // 查询到记录,同步到redis缓存
        redisTemplate.opsForValue().set(key, res, expireTime, expireTimeUnit);
        return res;
    }

    public <T, ID extends Serializable> T getById(RedisCacheEnum cacheEnum, ID id, Function<ID, T> dbQueryFunction) {
        return getById(cacheEnum.getKeyById(id), id, dbQueryFunction);
    }

    public <T> IPage<T> page(Serializable key, IPage<T> page, Supplier<IPage<T>> dbSupplier) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            page = getOrNull(key);
            return page;
        }
        page = dbSupplier.get();
        redisTemplate.opsForValue().set(key, page, expireTime, expireTimeUnit);
        return page;
    }

    public <T> List<T> listWithWrapper(Serializable key, Supplier<List<T>> dbSupplier) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return getOrNull(key);
        }
        List<T> res = dbSupplier.get();
        set(key,res);
        return res;
    }

    public <T, ID extends Serializable> Collection<T> listBatchIds(RedisCacheEnum cacheEnum, Collection<ID> ids, Function<Collection<ID>, Collection<T>> dbQueryFunction) {
        //使用管道批量执行命令
        Collection<T> res = pipelinedGet(cacheEnum, ids);
        //如果全部命中缓存则直接返回
        if (!ObjectUtils.isEmpty(res) && res.size() == ids.size()) {
            return res;
        }
        //未命中则缓存击穿查询数据库
        res = dbQueryFunction.apply(ids);
        //TODO 异步进行更新缓存操作
        pipelinedSet(cacheEnum, ids, res);
        return res;
    }

    public <T, ID extends Serializable, KEY extends Serializable> Collection<T> listBatchIds(Collection<KEY> keys, Collection<ID> ids, Function<Collection<ID>, Collection<T>> dbQueryFunction) {
        //使用管道批量执行命令
        Collection<T> res = pipelinedGet(keys);
        //如果全部命中缓存则直接返回
        if (!ObjectUtils.isEmpty(res) && res.size() == ids.size()) {
            return res;
        }
        //未命中则缓存击穿查询数据库
        res = dbQueryFunction.apply(ids);
        //TODO 异步进行更新缓存操作
        pipelinedSet(keys, res);
        return res;
    }

    public <T, ID extends Serializable> List<T> getBatchIds(RedisCacheEnum cacheEnum, Collection<ID> ids, Function<Collection<ID>, List<T>> dbQueryFunction) {
        //使用管道批量执行命令
        List<T> res = pipelinedGet(cacheEnum, ids);
        //如果全部命中缓存则直接返回
        if (!ObjectUtils.isEmpty(res) && res.size() == ids.size()) {
            return res;
        }
        //未命中则缓存击穿查询数据库
        res = dbQueryFunction.apply(ids);
        //TODO 异步进行更新缓存操作
        pipelinedSet(cacheEnum, ids, res);
        return res;
    }

    public <T, ID extends Serializable, KEY extends Serializable> List<T> getBatchIds(Collection<KEY> keys, Collection<ID> ids, Function<Collection<ID>, List<T>> dbQueryFunction) {
        //使用管道批量执行命令
        List<T> res = pipelinedGet(keys);
        //如果全部命中缓存则直接返回
        if (!ObjectUtils.isEmpty(res) && res.size() == ids.size()) {
            return res;
        }
        //未命中则缓存击穿查询数据库
        res = dbQueryFunction.apply(ids);
        //TODO 异步进行更新缓存操作
        pipelinedSet(keys, res);
        return res;
    }

    public <T, ID extends Serializable> Boolean updateById(RedisCacheEnum cacheEnum, ID id, T entity, Function<T, Boolean> dbQueryFunction) {
        Serializable key = cacheEnum.getKeyById(id);
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Boolean res = dbQueryFunction.apply(entity);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <T, KEY extends Serializable> Boolean updateById(KEY key, T entity, Function<T, Boolean> dbQueryFunction) {
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Boolean res = dbQueryFunction.apply(entity);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <T, ID extends Serializable> Integer update(RedisCacheEnum cacheEnum, ID id, T entity, Function<T, Integer> dbQueryFunction) {
        Serializable key = cacheEnum.getKeyById(id);
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Integer res = dbQueryFunction.apply(entity);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <T, KEY extends Serializable> Integer update(KEY key, T entity, Function<T, Integer> dbQueryFunction) {
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Integer res = dbQueryFunction.apply(entity);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <T, ID extends Serializable> Boolean updateBatchIds(RedisCacheEnum cacheEnum, Collection<ID> ids, Collection<T> entity, Function<Collection<T>, Boolean> dbQueryFunction) {
        //修改前删除第一次
        pipelinedDelete(cacheEnum, ids);
        //更新数据库
        Boolean res = dbQueryFunction.apply(entity);
        //修改数据后删除第二次
        pipelinedDelete(cacheEnum, ids);
        return res;
    }

    public <T, KEY extends Serializable> Boolean updateBatchIds(Collection<KEY> keys, Collection<T> entities, Function<Collection<T>, Boolean> dbQueryFunction) {
        //修改前删除第一次
        pipelinedDelete(keys);
        //更新数据库
        Boolean res = dbQueryFunction.apply(entities);
        //修改数据后删除第二次
        pipelinedDelete(keys);
        return res;
    }

    public <ID extends Serializable> Integer deleteById(RedisCacheEnum cacheEnum, ID id, Function<ID, Integer> dbQueryFunction) {
        Serializable key = cacheEnum.getKeyById(id);
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Integer res = dbQueryFunction.apply(id);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <KEY extends Serializable, ID extends Serializable> Integer deleteById(KEY key, ID id, Function<ID, Integer> dbQueryFunction) {
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Integer res = dbQueryFunction.apply(id);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <ID extends Serializable> Boolean removeById(RedisCacheEnum cacheEnum, ID id, Function<ID, Boolean> dbQueryFunction) {
        Serializable key = cacheEnum.getKeyById(id);
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Boolean res = dbQueryFunction.apply(id);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public <KEY extends Serializable, ID extends Serializable> Boolean removeById(KEY key, ID id, Function<ID, Boolean> dbQueryFunction) {
        //修改前删除第一次
        deleteKey(key);
        //更新数据库
        Boolean res = dbQueryFunction.apply(id);
        //修改数据后删除第二次
        deleteKey(key);
        return res;
    }

    public Boolean removeBatchIds(RedisCacheEnum cacheEnum, Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Boolean> dbQueryFunction) {
        //第一次删除
        pipelinedDelete(cacheEnum, ids);
        //更新数据
        Boolean res = dbQueryFunction.apply(ids);
        //第二次删除
        pipelinedDelete(cacheEnum, ids);
        return res;
    }

    public Boolean removeBatchIds(Collection<? extends Serializable> keys, Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Boolean> dbQueryFunction) {
        //第一次删除
        pipelinedDelete(keys);
        //更新数据
        Boolean res = dbQueryFunction.apply(ids);
        //第二次删除
        pipelinedDelete(keys);
        return res;
    }

    public Integer deleteBatchIds(RedisCacheEnum cacheEnum, Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Integer> dbQueryFunction) {
        //第一次删除
        pipelinedDelete(cacheEnum, ids);
        //更新数据
        Integer res = dbQueryFunction.apply(ids);
        //第二次删除
        pipelinedDelete(cacheEnum, ids);
        return res;
    }

    public Integer deleteBatchIds(Collection<? extends Serializable> keys, Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Integer> dbQueryFunction) {
        //第一次删除
        pipelinedDelete(keys);
        //更新数据
        Integer res = dbQueryFunction.apply(ids);
        //第二次删除
        pipelinedDelete(keys);
        return res;
    }
    //------------------------------------------------------------------------------------------------------------------
}
