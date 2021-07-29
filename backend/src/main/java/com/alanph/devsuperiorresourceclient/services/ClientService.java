package com.alanph.devsuperiorresourceclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alanph.devsuperiorresourceclient.dto.ClientDTO;
import com.alanph.devsuperiorresourceclient.entities.Client;
import com.alanph.devsuperiorresourceclient.repositories.ClientRepository;

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

}
