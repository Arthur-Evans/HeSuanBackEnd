package com.arthur.hesuanbackend.model.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ApplyRequest implements Serializable {

    private String id;

    private String reason;

    private Date leaveAddress;

    private Date backTime;

}
