package com.example.demo.netty.chat.client;

public class Student {
    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
