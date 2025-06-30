package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.requests.EditoraRequestDTO;
import com.github.guiziin227.livraria.dto.responses.EditoraResponseDTO;
import com.github.guiziin227.livraria.model.Editora;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre Editora e seus DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EditoraMapper {

    /**
     * Converte EditoraRequestDTO para Editora
     */
    Editora toEntity(EditoraRequestDTO dto);

    /**
     * Converte Editora para EditoraResponseDTO
     */
    EditoraResponseDTO toResponseDTO(Editora editora);

    /**
     * Converte lista de Editora para lista de EditoraResponseDTO
     */
    List<EditoraResponseDTO> toResponseDTOList(List<Editora> editoras);

    /**
     * Atualiza uma entidade Editora existente com dados do DTO
     */
    void updateEntityFromDTO(EditoraRequestDTO dto, @MappingTarget Editora editora);
}
