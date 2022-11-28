package com.arthur.hesuanbackend.model.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class CitizenRequest implements Serializable {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String card;
    private String phone;
    private String region;
    private String address;
}
