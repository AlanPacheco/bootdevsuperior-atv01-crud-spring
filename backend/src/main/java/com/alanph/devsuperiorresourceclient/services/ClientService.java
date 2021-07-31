package com.alanph.devsuperiorresourceclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alanph.devsuperiorresourceclient.dto.ClientDTO;
import com.alanph.devsuperiorresourceclient.entities.Client;
import com.alanph.devsuperiorresourceclient.repositories.ClientRepository;
import com.alanph.devsuperiorresourceclient.services.exceptions.DatabaseException;
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

	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		Client client = new Client();
		clientDTOToClient(clientDTO, client);
		return new ClientDTO(clientRepository.save(client));
	}

	private void clientDTOToClient(ClientDTO clientDTO, Client client) {
		client.setName(clientDTO.getName());
		client.setCpf(clientDTO.getCpf());
		client.setBirthDate(clientDTO.getBirthDate());
		client.setIncome(clientDTO.getIncome());
		client.setChildren(clientDTO.getChildren());
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		try {
			Client client = clientRepository.getOne(id);
			clientDTOToClient(clientDTO, client);
			client = clientRepository.save(client);
			return new ClientDTO(client);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EmptyResultDataAccessException error) {
			throw new ResourceNotFoundException("Id Not Found " + id);
		} catch (DataIntegrityViolationException error) {
			throw new DatabaseException("Integrity Violation");
		}
	}

}
