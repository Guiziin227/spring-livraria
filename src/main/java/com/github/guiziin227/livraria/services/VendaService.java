package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.VendaRequestDTO;
import com.github.guiziin227.livraria.dto.responses.VendaResponseDTO;
import com.github.guiziin227.livraria.dto.simples.VendaSimpleDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.mapper.VendaMapper;
import com.github.guiziin227.livraria.model.Venda;
import com.github.guiziin227.livraria.model.PK.VendaPK;
import com.github.guiziin227.livraria.repositories.VendaRepository;
import com.github.guiziin227.livraria.repositories.ClienteRepository;
import com.github.guiziin227.livraria.repositories.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VendaService {

    Logger logger = LoggerFactory.getLogger(VendaService.class);

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private VendaMapper vendaMapper;

    @Transactional
    public VendaResponseDTO createVenda(VendaRequestDTO vendaRequestDTO) {
        logger.info("Criando nova venda para cliente: {} e livro: {}",
                   vendaRequestDTO.clienteId(), vendaRequestDTO.livroId());

        if (!clienteRepository.existsById(vendaRequestDTO.clienteId())) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + vendaRequestDTO.clienteId());
        }

        if (!livroRepository.existsById(vendaRequestDTO.livroId())) {
            throw new ResourceNotFoundException("Livro não encontrado com ID: " + vendaRequestDTO.livroId());
        }

        VendaPK vendaPK = vendaMapper.createVendaPK(vendaRequestDTO);
        if (vendaRepository.existsById(vendaPK)) {
            throw new IllegalArgumentException("Venda já existe para este cliente e livro");
        }

        Venda venda = vendaMapper.toEntity(vendaRequestDTO);
        venda.setId(vendaPK);

        Venda savedVenda = vendaRepository.save(venda);
        logger.info("Venda criada com sucesso: {}", savedVenda.getId());

        return vendaMapper.toResponseDTO(savedVenda);
    }

    @Transactional(readOnly = true)
    public List<VendaResponseDTO> findAllVendas() {
        logger.info("Buscando todas as vendas");
        List<Venda> vendas = vendaRepository.findAll();
        return vendaMapper.toResponseDTOList(vendas);
    }

    @Transactional(readOnly = true)
    public VendaResponseDTO findVendaById(Long clienteId, Long livroId) {
        logger.info("Buscando venda por ID: clienteId={}, livroId={}", clienteId, livroId);
        VendaPK vendaPK = new VendaPK(clienteId, livroId);
        Venda venda = vendaRepository.findById(vendaPK)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));
        return vendaMapper.toResponseDTO(venda);
    }

    @Transactional(readOnly = true)
    public List<VendaSimpleDTO> findVendasByClienteId(Long clienteId) {
        logger.info("Buscando vendas por cliente ID: {}", clienteId);
        List<Venda> vendas = vendaRepository.findByClienteId(clienteId);
        return vendaMapper.toSimpleDTOList(vendas);
    }

    @Transactional(readOnly = true)
    public List<VendaSimpleDTO> findVendasByLivroId(Long livroId) {
        logger.info("Buscando vendas por livro ID: {}", livroId);
        List<Venda> vendas = vendaRepository.findByLivroId(livroId);
        return vendaMapper.toSimpleDTOList(vendas);
    }

    @Transactional(readOnly = true)
    public List<VendaSimpleDTO> findVendasByPeriodo(Date dataInicio, Date dataFim) {
        logger.info("Buscando vendas por período: {} a {}", dataInicio, dataFim);
        List<Venda> vendas = vendaRepository.findByDataVendaBetween(dataInicio, dataFim);
        return vendaMapper.toSimpleDTOList(vendas);
    }

    @Transactional(readOnly = true)
    public List<VendaSimpleDTO> findVendasByNotaFiscal(String notaFiscal) {
        logger.info("Buscando vendas por nota fiscal: {}", notaFiscal);
        List<Venda> vendas = vendaRepository.findByNotaFiscal(notaFiscal);
        return vendaMapper.toSimpleDTOList(vendas);
    }

    @Transactional
    public VendaResponseDTO updateVenda(Long clienteId, Long livroId, VendaRequestDTO vendaRequestDTO) {
        logger.info("Atualizando venda: clienteId={}, livroId={}", clienteId, livroId);

        VendaPK vendaPK = new VendaPK(clienteId, livroId);
        Venda existingVenda = vendaRepository.findById(vendaPK)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));

        // Verificar se está tentando alterar os IDs
        if (!clienteId.equals(vendaRequestDTO.clienteId()) || !livroId.equals(vendaRequestDTO.livroId())) {
            throw new IllegalArgumentException("Não é possível alterar os IDs de cliente ou livro em uma venda existente");
        }

        vendaMapper.updateEntityFromDTO(vendaRequestDTO, existingVenda);

        Venda updatedVenda = vendaRepository.save(existingVenda);
        logger.info("Venda atualizada com sucesso: {}", updatedVenda.getId());

        return vendaMapper.toResponseDTO(updatedVenda);
    }

    @Transactional
    public void deleteVenda(Long clienteId, Long livroId) {
        logger.info("Deletando venda: clienteId={}, livroId={}", clienteId, livroId);

        VendaPK vendaPK = new VendaPK(clienteId, livroId);
        if (!vendaRepository.existsById(vendaPK)) {
            throw new ResourceNotFoundException("Venda não encontrada");
        }

        vendaRepository.deleteById(vendaPK);
        logger.info("Venda deletada com sucesso");
    }
}
