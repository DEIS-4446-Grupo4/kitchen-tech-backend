package com.kitchenapp.kitchentech.business.service.iml;

import com.kitchenapp.kitchentech.business.model.Client;
import com.kitchenapp.kitchentech.business.repository.ClientRepository;
import com.kitchenapp.kitchentech.business.service.ClientService;
import com.kitchenapp.kitchentech.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client updateClient(Client client) {
        Client clientToUpdate = getClientById(client.getId());
        if (clientToUpdate != null) {
            clientToUpdate.setFullName(client.getFullName());
            clientToUpdate.setDocument(client.getDocument());
            clientToUpdate.setDocumentType(client.getDocumentType());
            return clientRepository.save(clientToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAllClients(Long restaurantId) {
        return clientRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void existsClientById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ValidationException("No existe ningún cliente");
        }
    }

    @Override
    public void existsClientByDocument(Client client) {
        if (clientRepository.existsByDocumentAndRestaurantId(client.getDocument(), client.getRestaurantId())
                && !clientRepository.findById(client.getId()).map(existingClient -> existingClient.getDocument().equals(client.getDocument())).orElse(false)) {
            throw new ValidationException("Ya existe un cliente con este numero de documento en el restaurante");
        }
    }
}