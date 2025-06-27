package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.exceptions.NullableRequestException;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.model.Livro;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import com.github.guiziin227.livraria.model.Editora;
import com.github.guiziin227.livraria.repositories.EditoraRepositoy;
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

    @Autowired
    private EditoraRepositoy editoraRepository;

    @Transactional
    public Livro createLivro(Livro livro) {
        logger.info("Criando um Livro: {}", livro.getTitulo());

        if (livro.getPublisher() != null && livro.getPublisher().getId() != null) {
            Editora editora = editoraRepository.findById(livro.getPublisher().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com ID: " + livro.getPublisher().getId()));
            livro.setPublisher(editora);
        }
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public Livro getLivroById(Long id) {
        logger.info("Buscando Livro com ID: {}", id);
        var entity = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));

        logger.info("Livro encontrado: {}", entity.getTitulo());
        return entity;
    }

    @Transactional(readOnly = true)
    public List<Livro> getAllLivros() {
        logger.info("Buscando todos os Livros");
        return livroRepository.findAll();
    }

    @Transactional
    public Livro updateLivro(Long id, Livro livro) {

        logger.info("Atualizando Livro com ID: {}", livro.getId());

        if (id == null) {
            throw new NullableRequestException("ID do livro não pode ser nulo para atualização.");
        }

       Livro livroAtual = livroRepository.findById(id)
               .orElseThrow(
                          () -> new ResourceNotFoundException("Livro não encontrado com ID: " + id)
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
