package com.xu.movie.common;

import com.xu.movie.cache.CacheConstant;
import com.xu.movie.cache.ICache;
import com.xu.movie.dao.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author tangkc
 * @description
 * @date 2020/11/20 18:14
 */
@Component
@Order(1)
public class MovieApplicationRunner implements ApplicationRunner {
    @Autowired
    ICache iCache;
    @Autowired
    MovieDao movieDao;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        iCache.put(CacheConstant.MOVIE_KEY,movieDao.selectAll());

        iCache.put(CacheConstant.IMG_NAME,movieDao.selectImgNames());
    }
}
