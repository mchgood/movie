package com.xu.movie.service;

import cn.hutool.core.util.StrUtil;
import com.xu.movie.dao.MovieDao;
import com.xu.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xqq
 * @description
 * @date 2020/11/20 18:21
 */
@Service
public class MovieService {
    private static List<String> yearList = Arrays.asList("2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020");

    @Autowired
    MovieDao movieDao;

    /**
     * 饼图 - 查询每年总票房
     */
    public Map<String,Object> selectPieBoxOfficeSum(){
        List<String> nameList = yearList;
        List<Map<String,Object>> dataList = new ArrayList<>();

        List<MovieEntity> movieEntities = movieDao.selectAll();
        Map<String, List<MovieEntity>> movieEntityGroupByYear = movieEntities.stream().collect(Collectors.groupingBy(MovieEntity::getYear));

        for (int i = 0; i < nameList.size(); i++) {
            String year = nameList.get(i);
            Integer sum = movieEntityGroupByYear.get(year).stream().mapToInt(MovieEntity::getBoxOffice).sum();
            Map<String,Object> map = new HashMap<>();
            map.put("value",sum/1000);
            map.put("name",year+"年");
            dataList.add(map);
        }
        Map<String,Object> result = new HashMap<>();


        result.put("nameList",nameList.stream().map(t->{
            return t+"年";
        }).collect(Collectors.toList()));
        result.put("dataList",dataList);

        return  result;
    }

    /**
     * 饼图 - 查询各类型总票房
     */
    public Map<String, Object> selectTypeBoxOffice() {
        List<String> nameList = new ArrayList<>();
        List<Map<String,Object>> dataList = new ArrayList<>();

        List<MovieEntity> movieEntities = movieDao.selectAll();
        Map<String, List<MovieEntity>> movieEntityGroupByGenreMain = movieEntities.stream()
                .filter(t->{
                    return !StrUtil.isEmpty(t.getGenreMain());
                })
                .collect(Collectors.groupingBy(MovieEntity::getGenreMain));

        for (String genreMain:movieEntityGroupByGenreMain.keySet()) {
            nameList.add(genreMain);
        }
        Map<String,Object> result = new HashMap<>();
        return result;
    }

    /**
     * 柱状图 - 查询每年总票房
     */
    public Map<String, Object> selectBarBoxOfficeSum() {
        List<String> nameList = yearList;
        List<Integer> valueList = new ArrayList<>();

        List<MovieEntity> movieEntities = movieDao.selectAll();
        Map<String, List<MovieEntity>> movieEntityGroupByYear = movieEntities.stream().collect(Collectors.groupingBy(MovieEntity::getYear));
        for (int i = 0; i < nameList.size(); i++) {
            String year = nameList.get(i);
            Integer sum = movieEntityGroupByYear.get(year).stream().mapToInt(MovieEntity::getBoxOffice).sum();
            valueList.add(sum/100);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("valueList",valueList);

        result.put("nameList",nameList.stream().map(t->{
            return t+"年";
        }).collect(Collectors.toList()));

        return result;
    }

    /**
     * 柱状图 - 查询年度票房冠军
     */
    public Map<String, Object> selectBarYearBoxOfficeMax() {
        List<String> nameList = yearList;
        List<Integer> valueList = new ArrayList<>();
        List<String> movieNameList = new ArrayList<>();

        List<MovieEntity> movieEntities = movieDao.selectAll();

        Optional<MovieEntity> any = movieEntities.stream().filter(t -> {
            return "2012世界末日".equals(t.getMovieName());
        }).findAny();
        Map<String, List<MovieEntity>> movieEntityGroupByYear = movieEntities.stream().collect(Collectors.groupingBy(MovieEntity::getYear));
        for (int i = 0; i < nameList.size(); i++) {
            String year = nameList.get(i);
            List<MovieEntity> movieEntityList =  movieEntityGroupByYear.get(year);
            movieEntityList.sort(new Comparator<MovieEntity>() {
                @Override
                public int compare(MovieEntity o1, MovieEntity o2) {
                    return o2.getBoxOffice()-o1.getBoxOffice();
                }
            });
            MovieEntity movieEntity = movieEntityList.get(0);
            valueList.add(movieEntity.getBoxOffice());
            movieNameList.add(movieEntity.getMovieName());
        }

        Map<String,Object> result = new HashMap<>();
        result.put("valueList",valueList);
        result.put("movieNameList",movieNameList);
        result.put("nameList",nameList.stream().map(t->{
            return t+"年";
        }).collect(Collectors.toList()));
        return result;
    }

}
