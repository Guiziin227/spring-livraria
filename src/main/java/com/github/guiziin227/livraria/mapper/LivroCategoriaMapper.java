package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.LivroCategoriaResponseDTO;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre LivroCategoria e seus DTOs
 */
@Mapper(componentModel = "spring",
        uses = {LivroMapper.class, CategoriaMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LivroCategoriaMapper {

    /**
     * Converte LivroCategoria para LivroCategoriaResponseDTO
     */
    @Mapping(target = "livro", source = "livro")
    @Mapping(target = "categoria", source = "categoria")
    LivroCategoriaResponseDTO toResponseDTO(LivroCategoria livroCategoria);

    /**
     * Converte lista de LivroCategoria para lista de LivroCategoriaResponseDTO
     */
    List<LivroCategoriaResponseDTO> toResponseDTOList(List<LivroCategoria> livroCategoria);
}
