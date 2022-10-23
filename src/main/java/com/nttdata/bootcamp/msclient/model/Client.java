package com.nttdata.bootcamp.msclient.model;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

    @Id
    private Integer id;
    @NonNull
    private Integer clientType;//0 - Personal / 1 - Empresarial
    @NonNull
    private Integer docType;//0 -  DNI / 1 - RUC / 2 - Carnet Ext
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
