package com.school.interfaces.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作服务
 */
public interface RedisService {

    /**
     * 保存属性,有过期时间
     * @param key
     * @param value
     * @param time
     */
    void set(String key, Object value, Long time);

    /**
     * 保存属性
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 获取
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除属性
     * @param key
     * @return
     */
    Boolean delete(String key);

    /**
     * 批量删除
     * @param keys
     * @return
     */
    Long batchDelete(List<String> keys);

    /**
     * 设置过期时间
     * @param key
     * @param time
     * @return
     */
    Boolean expire(String key, Long time);

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 判断是否存在该key
     * @param key
     * @return
     */
    Boolean hasKey(String key);

    /**
     * 按照delta递增
     * @param key
     * @param delta
     * @return
     */
    Long incr(String key, Long delta);

    /**
     * 按照delta递减
     * @param key
     * @param delta
     * @return
     */
    Long decr(String key, Long delta);

    /**
     * 获取Hash结构中的属性
     * @param key
     * @param hashKey
     * @return
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return
     */
    Boolean hSet(String key, String hashKey, Object value, Long time);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构数据
     * @param key
     * @return
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     * @param key
     * @param map
     * @param time
     * @return
     */
    Boolean hSetAll(String key, Map<Object, Object> map, Long time);

    /**
     * 直接设置整个Hash结构
     * @param key
     * @param map
     */
    void hSetAll(String key, Map<Object, ?> map);

    /**
     * 批量删除整个Hash结构中的属性
     * @param key
     * @param hashKey
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断该Hash结构中是否有该属性
     * @param key
     * @param hashKey
     * @return
     */
    Boolean hHashKey(String key, String hashKey);

    /**
     * hash结构中，属性递增
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hInr(String key, String hashKey, Long delta);

    /**
     * hash结构中，属性递减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hDer(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     * @param key
     * @return
     */
    Set<Object> sMembers(String key);

    /**
     * 往Set结构添加元素
     *
     * @param key
     * @param values
     * @param time
     * @return
     */
    Long sAdd(String key, Long time, Object... values);

    /**
     * 往Set结构添加元素
     * @param key
     * @param values
     * @return
     */
    Long sAdd(String key, Object... values);

    /**
     * 是否为该Set集合中的元素
     * @param key
     * @param value
     * @return
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     * @param key
     * @return
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的元素
     * @param key
     * @param values
     * @return
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lRang(String key, Long start, Long end);

    /**
     * 获取List结构的长度
     * @param key
     * @return
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的元素
     * @param key
     * @param index
     * @return
     */
    Object lIndex(String key, Integer index);

    /**
     * 向List结构中添加元素
     * @param key
     * @param value
     * @return
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加元素
     * @param key
     * @param value
     * @param time
     * @return
     */
    Long lPush(String key, Object value, Long time);

    /**
     * 向List结构中批量添加元素
     * @param key
     * @param value
     * @return
     */
    Long lPushAll(String key, Object... value);

    /**
     * 向List结构中添加元素
     * @param key
     * @param time
     * @param value
     * @return
     */
    Long lPushAll(String key, Long time, Object... value);

    /**
     * 从List结构中移除元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    Long lRemove(String key, Long count, Object value);

}

