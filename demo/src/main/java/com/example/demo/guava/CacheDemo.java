package com.example.demo.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CacheDemo {
    public static LoadingCache<Long, User> cacheProvider() {
        CacheLoader<Long, User> cacheLoader = new CacheLoader<Long, User>() {
            @Override
            public User load(Long aLong) throws Exception {
                log.info("come here");
                User user = new User();
                user.age = 11;
                user.name = String.valueOf(System.currentTimeMillis());
                return user;
            }
        };
        LoadingCache<Long, User> build = CacheBuilder.newBuilder().refreshAfterWrite(3, TimeUnit.SECONDS).expireAfterAccess(2, TimeUnit.SECONDS).expireAfterWrite(2, TimeUnit.SECONDS).build(cacheLoader);
        return build;
    }

    public static void main(String[] args) {

        Callable<Integer> callable = () -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            return 1;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Data
    static class User {
        private String name;
        private Integer age;
    }
}
