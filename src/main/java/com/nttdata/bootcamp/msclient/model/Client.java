package com.nttdata.bootcamp.msclient.model;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Transient
    public static final String SEQUENCE_NAME = "client_sequence";

    @Id
    private Long id;
    @NonNull
    private Integer clientType;
    @NonNull
    private Integer docType;
    @NonNull
    @Indexed(unique = true)
    private String docNumber;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    @Nullable
    private String companyName;
    @Nullable
    private String email;
}
