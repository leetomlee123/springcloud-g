package com.example.demo.guava;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author lee
 */
@Slf4j
public class PreconditionDemo {
    public static void main(String[] args) {
        try {
            cc("", 19, null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void cc(String name, Integer age, Map students) {
        Preconditions.checkNotNull(name, "name must be assign");
        Preconditions.checkArgument(age > 18, "usr age must beyond 18");
        HashMap<Object, Object> hashMap = Maps.newHashMap();
        hashMap.put("lee", "offline");
        students = Optional.ofNullable(students).orElse(hashMap);
        System.out.println(students.values());
    }
}
