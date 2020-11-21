package com.xu.movie.entity;

import java.time.LocalDateTime;

/**
 * @author xqq
 * @description
 * @date 2020/11/20 16:29
 */
public class MovieEntity {

    /**
     * 电影ID
     */
    private Integer movieid;

    /**
     * 电影名称
     */
    private String movieName;

    /**
     * 电影类型
     */
    private String genreMain;

    /**
     * 总票房（单位万）
     */
    private Integer boxOffice;

    /**
     * 平均票价
     */
    private Integer avgPrice;

    /**
     * 场均人次
     */
    private Integer avgPeoPle;

    /**
     * 国家地区
     */
    private String area;

    /**
     * 上映日期
     */
    private LocalDateTime releaseTime;

    /**
     * 年份
     */
    private String year;

    /**
     * 海报地址
     */
    private String defaultImage;

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenreMain() {
        return genreMain;
    }

    public void setGenreMain(String genreMain) {
        this.genreMain = genreMain;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getAvgPeoPle() {
        return avgPeoPle;
    }

    public void setAvgPeoPle(Integer avgPeoPle) {
        this.avgPeoPle = avgPeoPle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movieid=" + movieid +
                ", movieName='" + movieName + '\'' +
                ", genreMain='" + genreMain + '\'' +
                ", boxOffice=" + boxOffice +
                ", avgPrice=" + avgPrice +
                ", avgPeoPle=" + avgPeoPle +
                ", area='" + area + '\'' +
                ", releaseTime=" + releaseTime +
                ", year='" + year + '\'' +
                ", defaultImage='" + defaultImage + '\'' +
                '}';
    }
}
