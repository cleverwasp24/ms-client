package com.nttdata.bootcamp.msclient.dto;

import lombok.Data;

@Data
public class PersonalClientDTO {

    private Integer docType;
    private String docNumber;
    private String firstName;
    private String lastName;
    private String email;

}
