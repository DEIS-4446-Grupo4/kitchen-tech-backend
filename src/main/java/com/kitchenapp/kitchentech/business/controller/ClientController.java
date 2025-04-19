package com.kitchenapp.kitchentech.business.controller;

import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kitchentech/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    // URL: http://localhost:8080/api/kitchentech/v1/client/restaurant/{restaurantId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Client>> getAllClients(@PathVariable(name = "restaurantId") Long restaurantId){

        if(clientService.getAllClients(restaurantId).isEmpty()){
            return new ResponseEntity<List<Client>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Client>>(clientService.getAllClients(restaurantId),HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/client/{clientId}
    // Method: GET

    @Transactional(readOnly = true)
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable(name = "clientId")Long clientId){
        clientService.existsClientById(clientId);
        return new ResponseEntity<Client>(clientService.getClientById(clientId),HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/client
    // Method: POST
    @Transactional
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        clientService.existsClientByDocument(client);
        return new ResponseEntity<Client>(clientService.createClient(client),HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/client/{clientId}
    // Method: PUT
    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable(name = "clientId") Long clientId, @RequestBody Client client){
        clientService.existsClientById(clientId);

        Client currentClient = clientService.getClientById(clientId);
        if (!currentClient.getDocument().equals(client.getDocument())) {
            clientService.existsClientByDocument(client);
        }

        client.setId(clientId);
        return new ResponseEntity<>(clientService.updateClient(client), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/kitchentech/v1/client/{clientId}
    // Method: DELETE
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable(name="clientId") Long clientId){
        clientService.existsClientById(clientId);
        clientService.deleteClient(clientId);

        return new ResponseEntity<String>("Client deleted successfully",HttpStatus.OK);
    }

}
