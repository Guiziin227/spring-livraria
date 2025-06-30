package com.github.guiziin227.livraria.mapper;

import com.github.guiziin227.livraria.dto.AutorSimpleDTO;
import com.github.guiziin227.livraria.dto.CategoriaSimpleDTO;
import com.github.guiziin227.livraria.dto.LivroRequestDTO;
import com.github.guiziin227.livraria.dto.LivroResponseDTO;
import com.github.guiziin227.livraria.dto.LivroSimpleDTO;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Mapper para conversões entre Livro e seus DTOs
 */
@Mapper(componentModel = "spring",
        uses = {EditoraMapper.class, CategoriaMapper.class},
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
    @Mapping(target = "category", source = "livroCategories", qualifiedByName = "mapCategories")
    @Mapping(target = "authors", source = "livroAutores", qualifiedByName = "mapAuthors")
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

    /**
     * Mapeia Set<LivroCategoria> para List<CategoriaSimpleDTO>
     */
    @org.mapstruct.Named("mapCategories")
    default List<CategoriaSimpleDTO> mapCategories(Set<LivroCategoria> livroCategories) {
        if (livroCategories == null || livroCategories.isEmpty()) {
            return null;
        }

        return livroCategories.stream()
                .map(livroCategoria -> {
                    var categoria = livroCategoria.getCategoria();
                    return new CategoriaSimpleDTO(
                            categoria.getId(),
                            categoria.getName()
                    );
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @org.mapstruct.Named("mapAuthors")
    default List<AutorSimpleDTO> mapAuthors(Set<LivroAutor> livroAutors) {
        if (livroAutors == null || livroAutors.isEmpty()) {
            return null;
        }

        return livroAutors.stream()
                .flatMap(livroCategoria -> livroCategoria.getLivro().getAutores().stream())
                .map(autor -> new AutorSimpleDTO(
                        autor.getId(),
                        autor.getName()
                ))
                .distinct()
                .collect(java.util.stream.Collectors.toList());
    }
}
