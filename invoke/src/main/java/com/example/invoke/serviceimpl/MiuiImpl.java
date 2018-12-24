package com.example.invoke.serviceimpl;


import com.example.invoke.dao.MiuiMapper;
import com.example.invoke.dto.CommonDto;
import com.example.invoke.model.Miui;
import com.example.invoke.model.MiuiExample;
import com.example.invoke.service.IMiuiService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "miuiImpl")
public class MiuiImpl implements IMiuiService {
    @Autowired
    @Qualifier(value = "miuiMapper")
    private MiuiMapper miuiMapper;

    @Override
    public PageInfo<Miui> getLimitMiui(Integer limit, Integer size) {
        MiuiExample miuiExample = new MiuiExample();
        miuiExample.createCriteria();
        PageHelper.startPage(limit, size);
        List<Miui> miuis = miuiMapper.selectByExample(miuiExample);
        PageInfo<Miui> pageInfo = new PageInfo<>(miuis);
        return pageInfo;
    }
    @Override
    public List<Miui> allMiui() {
        MiuiExample miuiExample = new MiuiExample();
        miuiExample.createCriteria();
        return miuiMapper.selectByExample(miuiExample);
    }

    @Override
    public List<CommonDto> topAuthors() {
        return miuiMapper.topAuthors();
    }

    @Override
    public List<CommonDto> topPhoneTypes() {
        return miuiMapper.topPhoneTypes();
    }
}
