package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.CategoriaRequestDTO;
import com.github.guiziin227.livraria.dto.CategoriaResponseDTO;
import com.github.guiziin227.livraria.dto.CategoriaSimpleDTO;
import com.github.guiziin227.livraria.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre Categoria e seus DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    /**
     * Converte CategoriaRequestDTO para Categoria
     */
    Categoria toEntity(CategoriaRequestDTO dto);

    /**
     * Converte Categoria para CategoriaResponseDTO
     */
    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    /**
     * Converte Categoria para CategoriaSimpleDTO (usado em relacionamentos)
     */
    CategoriaSimpleDTO toSimpleDTO(Categoria categoria);

    /**
     * Converte lista de Categoria para lista de CategoriaResponseDTO
     */
    List<CategoriaResponseDTO> toResponseDTOList(List<Categoria> categorias);

    /**
     * Atualiza uma entidade Categoria existente com dados do DTO
     */
    void updateEntityFromDTO(CategoriaRequestDTO dto, @MappingTarget Categoria categoria);
}
