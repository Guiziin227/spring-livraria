package com.github.guiziin227.livraria.services;

import com.github.guiziin227.livraria.dto.requests.VendaRequestDTO;
import com.github.guiziin227.livraria.dto.responses.VendaResponseDTO;
import com.github.guiziin227.livraria.dto.simples.VendaSimpleDTO;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.exceptions.DuplicateResourceException;
import com.github.guiziin227.livraria.exceptions.BusinessRuleException;
import com.github.guiziin227.livraria.exceptions.InvalidOperationException;
import com.github.guiziin227.livraria.exceptions.InvalidDateRangeException;
import com.github.guiziin227.livraria.exceptions.RelationshipException;
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

        // Validações de regras de negócio
        validateVendaRequest(vendaRequestDTO);

        // Verificar se cliente existe
        if (!clienteRepository.existsById(vendaRequestDTO.clienteId())) {
            throw new RelationshipException("Cliente não encontrado com ID: " + vendaRequestDTO.clienteId());
        }

        // Verificar se livro existe
        if (!livroRepository.existsById(vendaRequestDTO.livroId())) {
            throw new RelationshipException("Livro não encontrado com ID: " + vendaRequestDTO.livroId());
        }

        // Verificar se a venda já existe
        VendaPK vendaPK = vendaMapper.createVendaPK(vendaRequestDTO);
        if (vendaRepository.existsById(vendaPK)) {
            throw new DuplicateResourceException("Venda já existe para o cliente ID: " +
                vendaRequestDTO.clienteId() + " e livro ID: " + vendaRequestDTO.livroId());
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

        // Validar período
        validateDateRange(dataInicio, dataFim);

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

        // Validações de regras de negócio
        validateVendaRequest(vendaRequestDTO);

        VendaPK vendaPK = new VendaPK(clienteId, livroId);
        Venda existingVenda = vendaRepository.findById(vendaPK)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada para cliente ID: " +
                    clienteId + " e livro ID: " + livroId));

        // Verificar se está tentando alterar os IDs
        if (!clienteId.equals(vendaRequestDTO.clienteId()) || !livroId.equals(vendaRequestDTO.livroId())) {
            throw new InvalidOperationException("Não é possível alterar os IDs de cliente ou livro em uma venda existente");
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
            throw new ResourceNotFoundException("Venda não encontrada para cliente ID: " +
                clienteId + " e livro ID: " + livroId);
        }

        vendaRepository.deleteById(vendaPK);
        logger.info("Venda deletada com sucesso");
    }

    /**
     * Valida as regras de negócio para uma venda
     */
    private void validateVendaRequest(VendaRequestDTO vendaRequestDTO) {
        // Validar valor total
        if (vendaRequestDTO.valorTotal() <= 0) {
            throw new BusinessRuleException("O valor total da venda deve ser maior que zero");
        }

        // Validar data da venda (não pode ser futura)
        Date hoje = new Date();
        if (vendaRequestDTO.dataVenda().after(hoje)) {
            throw new BusinessRuleException("A data da venda não pode ser uma data futura");
        }

        // Validar nota fiscal (deve ser única)
        List<Venda> vendasComMesmaNota = vendaRepository.findByNotaFiscal(vendaRequestDTO.notaFiscal());
        if (!vendasComMesmaNota.isEmpty()) {
            throw new DuplicateResourceException("Já existe uma venda com a nota fiscal: " + vendaRequestDTO.notaFiscal());
        }
    }

    /**
     * Valida um intervalo de datas
     */
    private void validateDateRange(Date dataInicio, Date dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new BusinessRuleException("Data de início e data fim são obrigatórias");
        }

        if (dataInicio.after(dataFim)) {
            throw new InvalidDateRangeException("A data de início não pode ser posterior à data fim");
        }

        // Validar se o período não é muito extenso (mais de 1 ano)
        long diffInMillies = Math.abs(dataFim.getTime() - dataInicio.getTime());
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);

        if (diffInDays > 365) {
            throw new BusinessRuleException("O período de consulta não pode exceder 1 ano");
        }
    }
}
