package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.NullableRequestException;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    Logger logger = LoggerFactory.getLogger(LivroService.class);

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public Livro createLivro(Livro livro) {
        logger.info("Criando um Livro: {}", livro.getTitulo());
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro getLivroById(Long id) {
        logger.info("Buscando Livro com ID: {}", id);
        return livroRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Livro> getAllLivros() {
        logger.info("Buscando todos os Livros");
        return livroRepository.findAll();
    }

    @Transactional
    public Livro updateLivro(Livro livro) {

        logger.info("Atualizando Livro com ID: {}", livro.getId());

        if (livro.getId() == null) {
            throw new NullableRequestException("ID do livro não pode ser nulo para atualização.");
        }


       Livro livroAtual = livroRepository.findById(livro.getId())
               .orElseThrow(
                          () -> new ResourceNotFoundException("Livro não encontrado com ID: " + livro.getId())
               );

        livroAtual.setTitulo(livro.getTitulo());
        livroAtual.setPreco(livro.getPreco());

        return livroRepository.save(livroAtual);
    }

    @Transactional
    public void deleteLivro(Long id) {

        logger.info("Deletando Livro com ID: {}", id);

        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + id);
        }
    }




}
