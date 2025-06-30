package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.requests.ClienteRequestDTO;
import com.github.guiziin227.livraria.dto.responses.ClienteResponseDTO;
import com.github.guiziin227.livraria.mapper.ClienteMapper;
import com.github.guiziin227.livraria.model.Cliente;
import com.github.guiziin227.livraria.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> createCliente(@RequestBody ClienteRequestDTO cliente) {
        Cliente clienteEntity = clienteMapper.toEntity(cliente);
        Cliente savedCliente = clienteService.createCliente(clienteEntity);
        ClienteResponseDTO responseDTO = clienteMapper.toResponseDTO(savedCliente);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        ClienteResponseDTO responseDTO = clienteMapper.toResponseDTO(cliente);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        List<Cliente> autores = clienteService.findAll();
        return ResponseEntity.ok(clienteMapper.toResponseDTOList(autores));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id,@Valid @RequestBody ClienteRequestDTO cliente) {
        Cliente clienteEntity = clienteMapper.toEntity(cliente);
        Cliente updatedCliente = clienteService.updateCliente(id, clienteMapper.toRequestDTO(clienteEntity));
        ClienteResponseDTO responseDTO = clienteMapper.toResponseDTO(updatedCliente);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
