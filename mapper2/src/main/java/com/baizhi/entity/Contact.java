package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contacts")
public class Contact implements Serializable {
    @Id
    private Integer id;
    private String name;
    private String tel;
    private String address;
    private Date birthday;
}
