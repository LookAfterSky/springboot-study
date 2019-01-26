package com.baizhi.service.impl;

import com.baizhi.dao.ContactDao;
import com.baizhi.entity.Contact;
import com.baizhi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDao contactDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Contact queryById(Integer id) {
        return contactDao.selectByPrimaryKey(id);
    }

    @Override
    public void removeById(Integer id) {
        contactDao.deleteByPrimaryKey(id);
    }
}
