package com.alex.projeto_correios.controllers;

import com.alex.projeto_correios.domain.Cliente;
import com.alex.projeto_correios.domain.Encomenda;
import com.alex.projeto_correios.domain.enums.Status;
import com.alex.projeto_correios.services.ClienteService;
import com.alex.projeto_correios.services.EncomendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/encomendas")
public class EncomendaController {

    @Autowired
    private EncomendaService service;

    @Autowired
    private ClienteService clienteService;

//    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
//    public ResponseEntity<Encomenda> findByCodigo(@PathVariable String codigo){
//
//        Encomenda obj = service.findByCodigo(codigo);
//        return ResponseEntity.ok().body(obj);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Encomenda>> findAll(){

        List<Encomenda> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Encomenda> findById(@PathVariable Integer id){

        Encomenda obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Encomenda> insert(@RequestBody Encomenda obj){

        obj = service.insert(obj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //arrumar
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestBody String status){

        //continuar daqui
        List<Cliente> clienteList = clienteService.findAll();


        service.updateStatus(id, status);

        return ResponseEntity.noContent().build();
    }
}
