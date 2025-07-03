package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.requests.VendaRequestDTO;
import com.github.guiziin227.livraria.dto.responses.VendaResponseDTO;
import com.github.guiziin227.livraria.dto.simples.VendaSimpleDTO;
import com.github.guiziin227.livraria.model.Venda;
import com.github.guiziin227.livraria.model.PK.VendaPK;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre Venda e seus DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VendaMapper {

    /**
     * Converte VendaRequestDTO para Venda
     */
    @Mapping(target = "id.clienteId", source = "clienteId")
    @Mapping(target = "id.livroId", source = "livroId")
    Venda toEntity(VendaRequestDTO dto);

    /**
     * Converte Venda para VendaResponseDTO
     */
    @Mapping(target = "clienteId", source = "id.clienteId")
    @Mapping(target = "livroId", source = "id.livroId")
    VendaResponseDTO toResponseDTO(Venda entity);

    /**
     * Converte Venda para VendaSimpleDTO
     */
    @Mapping(target = "clienteId", source = "id.clienteId")
    @Mapping(target = "livroId", source = "id.livroId")
    VendaSimpleDTO toSimpleDTO(Venda entity);

    /**
     * Converte lista de Venda para lista de VendaResponseDTO
     */
    List<VendaResponseDTO> toResponseDTOList(List<Venda> entities);

    /**
     * Converte lista de Venda para lista de VendaSimpleDTO
     */
    List<VendaSimpleDTO> toSimpleDTOList(List<Venda> entities);

    /**
     * Atualiza uma entidade Venda existente com dados do DTO
     */
    @Mapping(target = "id.clienteId", source = "clienteId")
    @Mapping(target = "id.livroId", source = "livroId")
    void updateEntityFromDTO(VendaRequestDTO dto, @MappingTarget Venda entity);

    default VendaPK createVendaPK(VendaRequestDTO dto) {
        return new VendaPK(dto.clienteId(), dto.livroId());
    }
}