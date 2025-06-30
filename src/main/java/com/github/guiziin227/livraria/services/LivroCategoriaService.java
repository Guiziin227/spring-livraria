package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.DbIntegrityException;
import com.github.guiziin227.livraria.model.Categoria;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.model.JOIN.LivroCategoria;
import com.github.guiziin227.livraria.model.PK.LivroCategoriaPK;
import com.github.guiziin227.livraria.repositories.LivroCategoriaRepository;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import com.github.guiziin227.livraria.repositories.CategoriaRepository;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroCategoriaService {

    Logger logger = LoggerFactory.getLogger(LivroCategoriaService.class);

    @Autowired
    private LivroCategoriaRepository livroCategoriaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public LivroCategoria associarLivroCategoria(Long livroId, Long categoriaId) {
        logger.info("Associando Livro ID: {} com Categoria ID: {}", livroId, categoriaId);
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + livroId));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + categoriaId));

        // Verificar se a associação já existe
        if (livroCategoriaRepository.existsByLivroIdAndCategoriaId(livroId, categoriaId)) {
            logger.warn("Associação entre Livro ID: {} e Categoria ID: {} já existe", livroId, categoriaId);
            throw new DbIntegrityException("Associação entre livro e categoria já existe");
        }

        LivroCategoriaPK pk = new LivroCategoriaPK(livroId, categoriaId);
        LivroCategoria livroCategoria = new LivroCategoria(pk, livro, categoria);

        return livroCategoriaRepository.save(livroCategoria);
    }

    @Transactional
    public void desassociarLivroCategoria(Long livroId, Long categoriaId) {
        logger.info("Desassociando Livro ID: {} de Categoria ID: {}", livroId, categoriaId);
        LivroCategoriaPK pk = new LivroCategoriaPK(livroId, categoriaId);

        if (!livroCategoriaRepository.existsById(pk)) {
            throw new ResourceNotFoundException("Associação não encontrada entre livro ID: " + livroId + " e categoria ID: " + categoriaId);
        }

        livroCategoriaRepository.deleteById(pk);
    }

    public List<Categoria> buscarCategoriasPorLivro(Long livroId) {
        logger.info("Buscando categorias para Livro ID: {}", livroId);
        if (!categoriaRepository.existsById(livroId)) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + livroId);
        }

        return livroCategoriaRepository.findByLivroId(livroId)
                .stream()
                .map(LivroCategoria::getCategoria)
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorCategoria(Long categoriaId) {
        logger.info("Buscando livros para Categoria ID: {}", categoriaId);
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + categoriaId);
        }

        return livroCategoriaRepository.findByCategoriaIdWithAutores(categoriaId)
                .stream()
                .map(LivroCategoria::getLivro)
                .collect(Collectors.toList());
    }

    public boolean existeAssociacao(Long livroId, Long categoriaId) {
        return livroCategoriaRepository.existsByLivroIdAndCategoriaId(livroId, categoriaId);
    }
}
