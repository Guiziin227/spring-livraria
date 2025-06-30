package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.DbIntegrityException;
import com.github.guiziin227.livraria.model.Autor;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.model.JOIN.LivroAutor;
import com.github.guiziin227.livraria.model.PK.LivroAutorPK;
import com.github.guiziin227.livraria.repositories.LivroAutorRepository;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import com.github.guiziin227.livraria.repositories.AutorRepository;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroAutorService {

    Logger logger = LoggerFactory.getLogger(LivroAutorService.class);

    @Autowired
    private LivroAutorRepository livroAutorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public LivroAutor associarLivroAutor(Long livroId, Long autorId) {
        logger.info("Associando Livro ID: {} com Autor ID: {}", livroId, autorId);
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + livroId));

        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com ID: " + autorId));

        // Verificar se a associação já existe
        if (livroAutorRepository.existsByLivroIdAndAutorId(livroId, autorId)) {
            logger.warn("Associação entre Livro ID: {} e Autor ID: {} já existe", livroId, autorId);
            throw new DbIntegrityException("Associação entre livro e autor já existe");
        }

        LivroAutorPK pk = new LivroAutorPK(livroId, autorId);
        LivroAutor livroAutor = new LivroAutor(pk, livro, autor);

        return livroAutorRepository.save(livroAutor);
    }

    @Transactional
    public void desassociarLivroAutor(Long livroId, Long autorId) {
        logger.info("Desassociando Livro ID: {} de Autor ID: {}", livroId, autorId);
        LivroAutorPK pk = new LivroAutorPK(livroId, autorId);
        if (!livroAutorRepository.existsById(pk)) {
            throw new ResourceNotFoundException("Associação não encontrada entre Livro ID: " + livroId + " e Autor ID: " + autorId);
        }
        livroAutorRepository.deleteById(pk);
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarAutoresPorLivro(Long livroId) {
        logger.info("Buscando autores para Livro ID: {}", livroId);
        if (!livroRepository.existsById(livroId)) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + livroId);
        }

        return livroAutorRepository.findByLivroId(livroId)
                .stream()
                .map(LivroAutor::getAutor)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarLivrosPorAutor(Long autorId) {
        logger.info("Buscando livros para Autor ID: {}", autorId);
        if (!autorRepository.existsById(autorId)) {
            throw new ResourceNotFoundException("Autor não encontrado com ID: " + autorId);
        }

        return livroAutorRepository.findByAutorIdWithCategories(autorId)
                .stream()
                .map(LivroAutor::getLivro)
                .collect(Collectors.toList());
    }

    public boolean existeAssociacao(Long livroId, Long autorId) {
        return livroAutorRepository.existsByLivroIdAndAutorId(livroId, autorId);
    }
}
