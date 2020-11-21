package com.xu.movie;

import com.xu.movie.dao.MovieDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieApplicationTests {
    
    @Autowired
    MovieDao movieDao;

    @Test
    void contextLoads() {

    }
}
