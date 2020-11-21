package com.xu.movie.dao;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xu.movie.cache.CacheConstant;
import com.xu.movie.cache.ICache;
import com.xu.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xqq
 * @description
 * @date 2020/11/20 16:38
 */
@Component
public class MovieDao {
    @Autowired
    ICache iCache;

    /**
     * 查询所有
     */
    public List<MovieEntity> selectAll(){
        Object o = iCache.get(CacheConstant.MOVIE_KEY);
        if(o==null){
            return selectAllByDisk();
        }

        List<MovieEntity> result = new ArrayList<>();
        String jsonStr = JSONUtil.toJsonStr(o);
        JSONArray jSONArray = JSONUtil.parseArray(jsonStr);
        for (int i = 0; i < jSONArray.size();i++) {
            JSONObject entity = (JSONObject)jSONArray.get(i);
            result.add(JSONUtil.toBean(entity,MovieEntity.class));
        }
        return result;
    }

    /**
     * 从硬盘查询
     */
    private List<MovieEntity> selectAllByDisk(){
        List<MovieEntity> result = new ArrayList<>();
        File file = null;
        try {
            String templatePath = ResourceUtils.CLASSPATH_URL_PREFIX + "static/movie-data.log";
            file = ResourceUtils.getFile(templatePath);
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(file == null){
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> jsonArrDataList = FileUtil.readUtf8Lines(file);

        Integer year = 2020;
        for (String jsonArrData : jsonArrDataList) {
            JSONArray jSONArray = JSONUtil.parseArray(jsonArrData);
            for (int i = 0; i < jSONArray.size();i++) {
                MovieEntity movieEntity = new MovieEntity();
                Map<String,Object> map = (Map)jSONArray.get(i);
                Integer movieid =  (Integer)map.get("Movieid");
                String movieName =  (String)map.get("MovieName");
                String genreMain =  (String)map.get("Genre_Main");
                Integer boxOffice =  (Integer)map.get("BoxOffice");
                Integer avgPrice =  (Integer)map.get("AvgPrice");
                Integer agPeoPle =  (Integer)map.get("AvgPeoPle");
                String area =  (String)map.get("Area");
                String defaultImage =  (String)map.get("defaultImage");

                String releaseTimeStr = (String) map.get("ReleaseTime") +" 00:00:00";
                try{
                    LocalDateTime releaseTime = LocalDateTimeUtil.parse(releaseTimeStr,"yyyy-MM-dd HH:mm:ss");
                    movieEntity.setReleaseTime(releaseTime);
                }catch (DateTimeParseException e){

                }finally {
                    movieEntity.setYear(StrUtil.toString(year));
                }
//                if(movieEntity.getReleaseTime() == null){
//                    continue;
//                }
                movieEntity.setArea(area);
                movieEntity.setAvgPeoPle(agPeoPle);
                movieEntity.setAvgPrice(avgPrice);
                movieEntity.setBoxOffice(boxOffice);
                movieEntity.setDefaultImage(defaultImage);
                movieEntity.setGenreMain(genreMain);
                movieEntity.setMovieid(movieid);
                movieEntity.setMovieName(movieName);
                result.add(movieEntity);
            }
            year--;
        }
        return result;
    }

}
