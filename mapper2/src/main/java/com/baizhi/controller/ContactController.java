package com.baizhi.controller;

import com.baizhi.entity.Contact;
import com.baizhi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @RequestMapping("queryById/{id}")
    public Contact queryById(@PathVariable Integer id){
        return contactService.queryById(id);
    }
    @RequestMapping("removeById/{id}")
    public void removeById(@PathVariable Integer id){
        contactService.removeById(id);
    }
}
