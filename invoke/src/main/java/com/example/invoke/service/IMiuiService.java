package com.example.invoke.service;

import com.example.invoke.dto.CommonDto;
import com.example.invoke.model.Miui;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author lee
 */
@Cacheable(keyGenerator = "keyGenerator", value = "miuis")
public interface IMiuiService {
    PageInfo<Miui> getLimitMiui(Integer limit, Integer size);

    List<Miui> allMiui();

    List<CommonDto> topAuthors();

    List<CommonDto> topPhoneTypes();
}
