package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.ClienteRequestDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.exceptions.DuplicateResourceException;
import com.github.guiziin227.livraria.exceptions.BusinessRuleException;
import com.github.guiziin227.livraria.mapper.ClienteMapper;
import com.github.guiziin227.livraria.model.Cliente;
import com.github.guiziin227.livraria.repositories.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new DuplicateResourceException("Já existe um cliente cadastrado com o email: " + cliente.getEmail());
        }

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new DuplicateResourceException("Já existe um cliente cadastrado com o CPF: " + cliente.getCpf());
        }

        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Erro ao salvar cliente: dados duplicados detectados");
        }
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        logger.info("Buscando cliente com ID: " + id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do cliente deve ser um número positivo");
        }

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        logger.info("Buscando todos os clientes");
        List<Cliente> clientes = clienteRepository.findAll();

        if (clientes.isEmpty()) {
            logger.warn("Nenhum cliente encontrado");
            throw new ResourceNotFoundException("Nenhum cliente encontrado");
        }

        return clientes;
    }

    @Transactional
    public Cliente updateCliente(Long id, ClienteRequestDTO clienteDto) {
        logger.info("Atualizando cliente com ID: " + id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do cliente deve ser um número positivo");
        }

        Cliente existingCliente = findById(id);

        // Verificar se o email está sendo alterado e se já existe outro cliente com o mesmo email
        if (!existingCliente.getEmail().equals(clienteDto.email()) &&
            clienteRepository.existsByEmail(clienteDto.email())) {
            throw new DuplicateResourceException("Já existe um cliente cadastrado com o email: " + clienteDto.email());
        }

        // Verificar se o CPF está sendo alterado e se já existe outro cliente com o mesmo CPF
        if (!existingCliente.getCpf().equals(clienteDto.cpf()) &&
            clienteRepository.existsByCpf(clienteDto.cpf())) {
            throw new DuplicateResourceException("Já existe um cliente cadastrado com o CPF: " + clienteDto.cpf());
        }

        clienteMapper.updateEntityFromDTO(clienteDto, existingCliente);

        try {
            return clienteRepository.save(existingCliente);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Erro ao atualizar cliente: dados duplicados detectados");
        }
    }

    @Transactional
    public void deleteCliente(Long id) {
        logger.info("Deletando cliente com ID: " + id);

        if (id == null || id <= 0) {
            throw new BusinessRuleException("ID do cliente deve ser um número positivo");
        }

        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }

        try {
            clienteRepository.deleteById(id);
            logger.info("Cliente deletado com sucesso");
        } catch (DataIntegrityViolationException e) {
            throw new BusinessRuleException("Não é possível deletar este cliente pois ele possui vendas associadas");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }
}
