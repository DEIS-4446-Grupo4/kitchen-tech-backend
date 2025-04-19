package com.kitchenapp.kitchentech.business.service;

import com.kitchenapp.kitchentech.business.model.Client;

import java.util.List;

public interface ClientService {
    public abstract Client createClient(Client client);
    public abstract Client getClientById(Long id);
    public abstract Client updateClient(Client client);
    public abstract void deleteClient(Long id);
    public abstract List<Client> getAllClients(Long restaurantId);
    public void existsClientById(Long id);
    public void existsClientByDocument(Client client);
}
