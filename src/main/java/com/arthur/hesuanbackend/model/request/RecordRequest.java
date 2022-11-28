package com.arthur.hesuanbackend.model.request;


import lombok.Data;

import java.io.Serializable;


@Data
public class RecordRequest implements Serializable {


    private int id;
    private Double temperature;
    private String status;
    private int high;
    private int diagnosis;

}
