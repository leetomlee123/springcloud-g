package com.example.invoke.serviceimpl;

import com.example.invoke.dao.MovieMapper;
import com.example.invoke.dto.TopMovie;
import com.example.invoke.dto.TopRateMovie;
import com.example.invoke.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lee
 */
@Service(value = "movieImpl")
public class MovieImpl implements IMovieService {
    private static String TOPMOVIE = "TOPMOVIE";
    @Qualifier(value = "movieMapper")
    @Autowired
    private MovieMapper movieMapper;


    @Override
    public List<TopMovie> getHead() {
        return movieMapper.movies();
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator",cacheManager = "cacheManager",cacheNames = "xxx")
    public List<TopRateMovie> chart() {
        return movieMapper.initHead();
    }


}
