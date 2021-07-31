package com.alanph.devsuperiorresourceclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alanph.devsuperiorresourceclient.dto.ClientDTO;
import com.alanph.devsuperiorresourceclient.entities.Client;
import com.alanph.devsuperiorresourceclient.repositories.ClientRepository;
import com.alanph.devsuperiorresourceclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		Page<Client> pageClient = clientRepository.findAll(pageRequest);
		Page<ClientDTO> pageClientDTO = pageClient.map(e -> new ClientDTO(e));
		return pageClientDTO;
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> optionalClient = clientRepository.findById(id);
		Client client = optionalClient.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		ClientDTO clientDTO = new ClientDTO(client);
		return clientDTO;
		
	}

}
