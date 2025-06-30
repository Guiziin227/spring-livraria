package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.LivroAutorResponseDTO;
import com.github.guiziin227.livraria.dto.LivroCategoriaResponseDTO;
import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper para conversões entre LivroAutor e seus DTOs
 */
@Mapper(componentModel = "spring",
        uses = {LivroMapper.class, AutorMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LivroAutorMapper {

    /**
     * Converte LivroAutor para LivroAutorResponseDTO
     */
    @Mapping(target = "livro", source = "livro")
    @Mapping(target = "autor", source = "autor")
    LivroAutorResponseDTO toResponseDTO(LivroAutor livroAutor);

    /**
     * Converte lista de LivroAutor para lista de LivroAutorResponseDTO
     */
    List<LivroAutorResponseDTO> toResponseDTOList(List<LivroAutor> livroAutor);
}
