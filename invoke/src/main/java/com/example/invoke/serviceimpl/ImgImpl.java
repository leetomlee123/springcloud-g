package com.example.invoke.serviceimpl;

import com.example.invoke.annotation.TargetDataSource;
import com.example.invoke.common.DataSourceKey;
import com.example.invoke.dao.ImgMapper;
import com.example.invoke.model.Img;
import com.example.invoke.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "imgImpl")
@TargetDataSource(dataSourceKey = DataSourceKey.DB_SLAVE2)
public class ImgImpl implements IImgService {
    @Qualifier(value = "imgMapper")
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Integer addImg(Img img) {
        return imgMapper.insert(img);
    }
}
