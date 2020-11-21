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
        nameList = movieEntities.stream()
                        .map(MovieEntity::getGenreMain)
                        .distinct()
                        .filter(t->{
                            return StrUtil.isNotEmpty(t) && !t.contains("/");
                        })
                        .collect(Collectors.toList());

        Map<String, List<MovieEntity>> movieEntityGroupByGenreMain = movieEntities.stream()
                .filter(t->{
                    return !StrUtil.isEmpty(t.getGenreMain());
                })
                .collect(Collectors.groupingBy(MovieEntity::getGenreMain));

        for (int i = 0; i < nameList.size(); i++) {
            String genreMain = nameList.get(i);
            Integer sum = movieEntityGroupByGenreMain.get(genreMain).stream().mapToInt(MovieEntity::getBoxOffice).sum();
            Map<String,Object> map = new HashMap<>();

            map.put("value",sum/1000);
            map.put("name",genreMain);
            dataList.add(map);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("dataList",dataList);
        result.put("nameList",nameList);
        return result;
    }

    /**
     * 饼图 - 查询各国产片和外国片总票房
     */
    public Map<String, Object> selectCountryBoxOffice() {
        List<String> nameList = Arrays.asList("国产片","外国片");
        List<Map<String,Object>> dataList = new ArrayList<>();

        List<MovieEntity> movieEntities = movieDao.selectAll();

        Integer china = 0;
        Integer other = 0;
        for (MovieEntity movieEntity : movieEntities) {
            String area = movieEntity.getArea();
            Integer boxOffice = movieEntity.getBoxOffice();
            if(StrUtil.isEmpty(area) || boxOffice==null){
                continue;
            }
            if(area.contains("中国") || area.contains("香港")){
                china = china + boxOffice;
            }else {
                other = other + boxOffice;
            }
        }

        Map<String,Object> chinaMap = new HashMap<>();
        chinaMap.put("value",china/1000);
        chinaMap.put("name","国产片");
        Map<String,Object> otherMap = new HashMap<>();
        otherMap.put("value",other/1000);
        otherMap.put("name","外国片");

        dataList.add(chinaMap);
        dataList.add(otherMap);

        Map<String,Object> result = new HashMap<>();
        result.put("dataList",dataList);
        result.put("nameList",nameList);
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
