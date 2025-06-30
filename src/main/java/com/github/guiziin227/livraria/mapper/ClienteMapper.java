package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.requests.AutorRequestDTO;
import com.github.guiziin227.livraria.dto.requests.ClienteRequestDTO;
import com.github.guiziin227.livraria.dto.responses.AutorResponseDTO;
import com.github.guiziin227.livraria.dto.responses.ClienteResponseDTO;
import com.github.guiziin227.livraria.dto.simples.AutorSimpleDTO;
import com.github.guiziin227.livraria.dto.simples.ClienteSimpleDTO;
import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre Cliente e seus DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    /**
     * Converte ClienteRequestDTO para Cliente
     */
    Cliente toEntity(ClienteRequestDTO dto);

    /**
     * Converte Cliente para ClienteResponseDTO
     */
    ClienteResponseDTO toResponseDTO(Cliente cliente);

    /**
     * Converte Cliente para ClienteSimpleDTO
     */
    ClienteRequestDTO toRequestDTO(Cliente cliente);

    /**
     * Converte Cliente para AutorSimpleDTO
     */
    ClienteSimpleDTO toSimpleDTO(Cliente cliente);

    /**
     * Converte ClienteResponseDTO para Cliente
     */
    List<ClienteResponseDTO> toResponseDTOList(List<Cliente> clientes);

    /**
     * Converte ClienteRequestDTO para Cliente
     */
    void updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente cliente);
}
