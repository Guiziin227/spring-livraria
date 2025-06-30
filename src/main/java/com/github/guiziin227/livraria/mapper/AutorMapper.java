package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.AutorRequestDTO;
import com.github.guiziin227.livraria.dto.AutorResponseDTO;
import com.github.guiziin227.livraria.dto.AutorSimpleDTO;
import com.github.guiziin227.livraria.model.Autor;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre Autor e seus DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AutorMapper {

    /**
     * Converte AutorRequestDTO para Autor

     */
    Autor toEntity(AutorRequestDTO dto);

    /**
     * Converte Autor para AutorResponseDTO
     */
    AutorResponseDTO toResponseDTO(Autor autor);


    /**
     * Converte Autor para AutorResponseDTO
     */
    AutorRequestDTO toRequestDTO(Autor autor);


    /**
     * Converte Autor para AutorResponseDTO
     */
    AutorSimpleDTO toSimpleDTO(Autor autor);


    /**
     * Converte lista de Autor para lista de AutorResponseDTO
     */
    List<AutorResponseDTO> toResponseDTOList(List<Autor> autores);

    /**
     * Atualiza uma entidade Autor existente com dados do DTO
     */
    void updateEntityFromDTO(AutorRequestDTO dto, @MappingTarget Autor autor);
}

