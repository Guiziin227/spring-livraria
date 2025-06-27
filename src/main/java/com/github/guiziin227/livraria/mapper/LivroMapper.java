package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.dto.LivroSimpleDTO;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Mapper para conversões entre Livro e seus DTOs
 */
@Mapper(componentModel = "spring",
        uses = {EditoraMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LivroMapper {

    /**
     * Converte LivroRequestDTO para Livro
     */
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToDate")
    @Mapping(target = "publisher", source = "publisher", qualifiedByName = "publisherDTOToEditora")
    Livro toEntity(LivroRequestDTO dto);

    /**
     * Converte Livro para LivroResponseDTO
     */
    @Mapping(target = "date", source = "date", qualifiedByName = "dateToString")
    LivroResponseDTO toResponseDTO(Livro livro);

    /**
     * Converte lista de Livro para lista de LivroResponseDTO
     */
    List<LivroResponseDTO> toResponseDTOList(List<Livro> livros);

    /**
     * Converte Livro para LivroSimpleDTO (usado em relacionamentos)
     */
    LivroSimpleDTO toSimpleDTO(Livro livro);

    /**
     * Atualiza uma entidade Livro existente com dados do DTO
     */
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToDate")
    @Mapping(target = "publisher", ignore = true)
    void updateEntityFromDTO(LivroRequestDTO dto, @MappingTarget Livro livro);

    /**
     * Converte PublisherSimpleDTO para Editora
     */
    @org.mapstruct.Named("publisherDTOToEditora")
    default Editora publisherDTOToEditora(LivroRequestDTO.PublisherSimpleDTO publisherDTO) {
        if (publisherDTO == null || publisherDTO.id() == null) {
            return null;
        }
        Editora editora = new Editora();
        editora.setId(publisherDTO.id());
        return editora;
    }

    /**
     * Converte String para Date
     */
    @org.mapstruct.Named("stringToDate")
    default Date stringToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data inválido: " + dateStr + ". Use yyyy-MM-dd", e);
        }
    }

    /**
     * Converte Date para String
     */
    @org.mapstruct.Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
