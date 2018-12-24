package com.example.invoke.web.controller;

import com.example.invoke.dao.ImgMapper;
import com.example.invoke.model.Img;
import com.example.invoke.model.ImgExample;
import com.example.invoke.serviceimpl.MovieImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author lee
 */
@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    @Qualifier(value = "movieImpl")
    @Autowired
    private MovieImpl movieImpl;
    @Qualifier(value = "imgMapper")
    @Autowired
    private ImgMapper imgMapper;

    @GetMapping
    public ResponseEntity getHead() {
        return ResponseEntity.ok(movieImpl.getHead());
    }

    @GetMapping(value = "/init/{index}/{size}")
    public ResponseEntity initHead(@PathVariable(value = "index") Integer index, @PathVariable(value = "size") Integer size) {
        PageHelper.startPage(index, size);
        return ResponseEntity.ok(PageInfo.of(movieImpl.getHead()));
    }

    @GetMapping(value = "/imgs/{index}/{size}")
    public ResponseEntity imsg(@PathVariable(value = "index") Integer index, @PathVariable(value = "size") Integer size) {
        PageHelper.startPage(index, size);
        List<Img> imgs = imgMapper.selectByExample(new ImgExample());
        PageInfo<Img> of = PageInfo.of(imgs);
        return ResponseEntity.ok(of);
    }

    @GetMapping(value = "/chart")
    public ResponseEntity chart() {
        return ResponseEntity.ok(movieImpl.chart());
    }

    @GetMapping(value = "/insert")
    public void c() {
        BASE64Encoder encoder = new BASE64Encoder();
        File file = new File("C:\\Users\\lee\\Desktop\\file\\images");
        File[] files = file.listFiles();
        int i = 0;
        for (File f : files
        ) {
            Img img = new Img();
            img.setName(f.getName().split("\\.")[0]);
            String s = null;
            try {
                s = encodeBase64File(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            img.setData(s.getBytes());

            imgMapper.insert(img);
            i++;
            if (i % 10 == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public static String encodeBase64File(File file) throws Exception {

        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
}
