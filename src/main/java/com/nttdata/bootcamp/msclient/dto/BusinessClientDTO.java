package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;

@Data
public class BusinessClientDTO {

    private Integer docType;
    private String docNumber;
    private String companyName;
    private String email;

}
