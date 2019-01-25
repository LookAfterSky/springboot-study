package com.baizhi;

import com.baizhi.dao.ContactDao;
import com.baizhi.entity.Contact;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Mapper2ApplicationTests {

    @Autowired
    private ContactDao contactDao;

    @Test
    public void contextLoads() {
        List<Contact> contacts = contactDao.selectAll();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    @Test
    public void testExample() {
        Example example = new Example(Contact.class);
        example.createCriteria().andBetween("id", 0, 4)
                .orLike("name", "zs%").andBetween("id",8,8);
        List<Contact> list = contactDao.selectByExample(example);
        for (Contact contact : list) {
            System.out.println(contact);
        }
    }
    @Test
    public void testPage(){
        RowBounds rowBounds = new RowBounds(0,4);
        Contact contact1 = new Contact(null, null, null, null, null);
        List<Contact> list = contactDao.selectByRowBounds(contact1,rowBounds);
        for (Contact contact : list) {
            System.out.println(contact);
        }
    }

}

