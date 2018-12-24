package com.example.invoke.serviceimpl;

import com.example.invoke.annotation.TargetDataSource;
import com.example.invoke.common.DataSourceKey;
import com.example.invoke.dao.BbbMapper;
import com.example.invoke.model.Bbb;
import com.example.invoke.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service(value = "bookImpl")
@TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE1)
public class BookImpl implements IBookService {
    @Autowired
    @Qualifier(value = "bbbMapper")
    private BbbMapper bbbMapper;


    @Override
    public List<Bbb> getPageBook() {
        List<Integer> collect = Stream.generate(Math::random).limit(12).map(e -> (int) (e * 26594)).collect(Collectors.toList());
        HashMap<Object, Object> paras = Maps.newHashMap();
        paras.put("ids", collect);
        List<Bbb> pageBook = bbbMapper.getPageBook(paras);
        return pageBook;
    }

    @Override
    public List<String> getCategory() {
        return Optional.ofNullable(bbbMapper.getCategory()).orElse(Lists.newArrayList());
    }

    @Override
    public Bbb getBookById(Integer id) {
        Bbb bbb = Optional.ofNullable(bbbMapper.selectByPrimaryKey(id)).orElseGet(() -> new Bbb());
        return bbb;
    }



    @Override
    public PageInfo<Bbb> getCategoryById(String cid, Integer pageNum, Integer size) {
        PageHelper.startPage(pageNum, size);
        HashMap<String, Object> kvHashMap = Maps.newHashMap();
        kvHashMap.put("cid", cid);
        List<Bbb> pageBook = bbbMapper.getPageBook(kvHashMap);
        return new PageInfo<>(pageBook);
    }
}