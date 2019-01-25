package com.baizhi.service;

import com.baizhi.entity.Contact;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface ContactService {
    @Cacheable(key = "'user_'+#id",value = "'user_'+#id")
    Contact queryById(Integer id);

    @CacheEvict(key ="'user_'+#id",value = "users",condition = "#id!=1")
    void removeById(Integer id);
}
