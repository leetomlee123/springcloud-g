package com.example.invoke.service;

import com.example.invoke.dto.TopMovie;
import com.example.invoke.dto.TopRateMovie;

import java.util.List;

/**
 * @author lee
 */
public interface IMovieService {
    /**
     * 初始化巨幕
     */
    List<TopMovie> getHead();

    List<TopRateMovie> chart();


}
