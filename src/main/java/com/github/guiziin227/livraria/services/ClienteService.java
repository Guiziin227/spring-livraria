package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.ClienteRequestDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.mapper.ClienteMapper;
import com.github.guiziin227.livraria.model.Cliente;
import com.github.guiziin227.livraria.repositories.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Transactional
    public Cliente createCliente(Cliente cliente) {
        logger.info("Criando novo cliente: " + cliente.getName());
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
    logger.info("Buscando cliente com ID: " + id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        logger.info("Buscando todos os clientes");
        if (clienteRepository.findAll().isEmpty()) {
            logger.warn("Nenhum cliente encontrado");
            throw new ResourceNotFoundException("Nenhum cliente encontrado");
        }
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente updateCliente(Long id, ClienteRequestDTO cliente) {
        logger.info("Atualizando cliente com ID: " + id);
        Cliente existingCliente = findById(id);
        clienteMapper.updateEntityFromDTO(cliente, existingCliente);
        return clienteRepository.save(existingCliente);
    }

    @Transactional
    public void deleteCliente(Long id) {
        logger.info("Deletando cliente com ID: " + id);
        if(!clienteRepository.existsById(id)) {
            logger.warn("Cliente não encontrado para deletar com ID: " + id);
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }


}
