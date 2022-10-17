package com.nttdata.bootcamp.msclient.mapper;

import com.nttdata.bootcamp.msclient.dto.BusinessClientDTO;
import com.nttdata.bootcamp.msclient.dto.PersonalClientDTO;
import com.nttdata.bootcamp.msclient.model.Client;
import com.nttdata.bootcamp.msclient.model.enums.ClientTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientDTOMapper {

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    public Object convertToDto(Client client, ClientTypeEnum type) {
        switch (type){
            case PERSONAL:
                return modelMapper.map(client, PersonalClientDTO.class);
            case BUSINESS:
                return modelMapper.map(client, BusinessClientDTO.class);
            default:
                return null;
        }
    }
    public Client convertToEntity(Object clientDTO, ClientTypeEnum type) {
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setClientType(type.ordinal());
        return client;
    }

}
