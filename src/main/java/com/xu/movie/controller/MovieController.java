package com.xu.movie.controller;

import com.xu.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xqq
 * @description
 * @date 2020/11/20 18:21
 */
@RequestMapping("/movie")
@RestController
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("/pie")
    public Map<String,Object> selectPie(@RequestParam(name = "pieType",required = true) String pieType){
        if("pieBoxOffice".equals(pieType)){
            return movieService.selectPieBoxOfficeSum();
        }
        if("typeBoxOffice".equals(pieType)){
            return movieService.selectTypeBoxOffice();
        }
        if("countryBoxOffice".equals(pieType)){
            return movieService.selectCountryBoxOffice();
        }
        return null;
    }

    @GetMapping("/bar")
    public Map<String,Object> selectBar(@RequestParam(name = "barType",required = true) String barType){
        if("barBoxOffice".equals(barType)){
            return movieService.selectBarBoxOfficeSum();
        }
        if("barYearBoxOffice".equals(barType)){
            // 年度票房冠军
            return movieService.selectBarYearBoxOfficeMax();
        }
        return null;
    }

    @GetMapping("/images")
    public Map<String,Object> selectImages(){
        return movieService.selectImages();
    }
}
