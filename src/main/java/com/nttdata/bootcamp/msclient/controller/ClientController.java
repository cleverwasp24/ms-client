package com.nttdata.bootcamp.msclient.controller;

import com.nttdata.bootcamp.msclient.dto.BusinessClientDTO;
import com.nttdata.bootcamp.msclient.dto.PersonalClientDTO;
import com.nttdata.bootcamp.msclient.dto.ProductDTO;
import com.nttdata.bootcamp.msclient.model.Client;
import com.nttdata.bootcamp.msclient.service.impl.ClientServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/bootcamp/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping(value = "/findAllClients")
    @ResponseBody
    public Flux<Client> findAllClients() {
        return clientService.findAll();
    }

    @PostMapping(value = "/createPersonalClient")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> createPersonalClient(@RequestBody PersonalClientDTO personalClientDTO) {
        return clientService.createPersonalClient(personalClientDTO);
    }

    @PostMapping(value = "/createBusinessClient")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> createBusinessClient(@RequestBody BusinessClientDTO businessClientDTO) {
        return clientService.createBusinessClient(businessClientDTO);
    }

    @GetMapping(value = "/find/{id}")
    @ResponseBody
    public Mono<ResponseEntity<Client>> findClientById(@PathVariable Integer id) {
        return clientService.findById(id)
                .map(client -> ResponseEntity.ok().body(client))
                .onErrorResume(e -> {
                    log.info("Client not found " + id, e);
                    return Mono.just(ResponseEntity.badRequest().build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{id}")
    @ResponseBody
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.update(id, client)
                .map(c -> new ResponseEntity<>(c, HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public Mono<Void> deleteByIdClient(@PathVariable Integer id) {
        return clientService.delete(id);
    }

    @GetMapping(value = "/findAllProductsById/{id}")
    @ResponseBody
    public Mono<ProductDTO> getAllProductsById(@PathVariable Integer id) {
        return clientService.findAllProductsById(id);
    }/*public Mono<ResponseEntity<ProductDTO>> getAllProductsById(@PathVariable Integer id) {
        return clientService.findAllProductsById(id)
                .map(client -> ResponseEntity.ok().body(client))
                .onErrorResume(e -> {
                    log.info("Products not found " + id, e);
                    return Mono.just(ResponseEntity.badRequest().build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }*/

}
