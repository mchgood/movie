package com.xu.movie.cache.impl;

import com.xu.movie.cache.ICache;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xqq
 * @description
 * @date 2020/11/20 18:11
 */
@Component
public class SimpleMemoryCache implements ICache {

    private static final ConcurrentHashMap<String,Value> CACHE = new ConcurrentHashMap<>();
    @Override
    public Object get(String key) {
        Object value = null;
        if(containsKey(key)){
            value =  CACHE.get(key).getValue();
        }
        return value;
    }

    @Override
    public boolean containsKey(String key) {
        return CACHE.containsKey(key) && !deleteExpired(key);
    }

    @Override
    public void put(String key, Object value) {
        put(key,value,null);
    }

    @Override
    public void put(String key, Object value, Long expireTime) {
        CACHE.put(key,new Value(expireTime,value));
    }


    @Override
    public void delete(String key) {CACHE.remove(key);}

    /**
     * 如果值过期，就删除
     * @param key
     */
    public boolean deleteExpired(String key){
        boolean deleted = false;
        if(CACHE.containsKey(key)){
            Value value = CACHE.get(key);
            if(value.expired()){
                delete(key);
                deleted = true;
            }
        }
        return deleted;
    }



    class Value{
        /**
         * 有效时间，单位毫秒
         */
        private Long expireTime;
        /**
         * 存放的值
         */
        private Object value;

        public Long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Value(Long expireTime, Object value) {
            this.expireTime = calcuExpire(expireTime);
            this.value = value;
        }

        /**
         * 值是否过期
         * @return
         */
        public boolean expired(){
            return getExpireTime() != null && getExpireTime().longValue()< new Date().getTime();
        }

        /**
         * 计算过期时间
         * @param expireTime
         * @return
         */
        public Long calcuExpire(Long expireTime){
            Long expire = null;
            if(expireTime !=null){
                expire = expireTime.longValue()+new Date().getTime();
            }
            return expire;
        }
    }
}
