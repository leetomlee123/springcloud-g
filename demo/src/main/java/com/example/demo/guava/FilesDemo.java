package com.example.demo.guava;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author lee
 */
public class FilesDemo {
    public static void main(String[] args) {
        try {
            Files.readLines(new File(Resources.getResource("demo.txt").getFile()), Charset.forName("utf-8")).forEach(x -> System.out.println(x));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
