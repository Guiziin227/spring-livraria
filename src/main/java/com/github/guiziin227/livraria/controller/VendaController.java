package com.github.guiziin227.livraria.controller;

import com.github.guiziin227.livraria.dto.requests.VendaRequestDTO;
import com.github.guiziin227.livraria.dto.responses.VendaResponseDTO;
import com.github.guiziin227.livraria.dto.simples.VendaSimpleDTO;
import com.github.guiziin227.livraria.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> createVenda(@Valid @RequestBody VendaRequestDTO vendaRequestDTO) {
        VendaResponseDTO response = vendaService.createVenda(vendaRequestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> findAllVendas() {
        List<VendaResponseDTO> vendas = vendaService.findAllVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{clienteId}/{livroId}")
    public ResponseEntity<VendaResponseDTO> findVendaById(
            @PathVariable Long clienteId,
            @PathVariable Long livroId) {
        VendaResponseDTO venda = vendaService.findVendaById(clienteId, livroId);
        return ResponseEntity.ok(venda);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VendaSimpleDTO>> findVendasByClienteId(@PathVariable Long clienteId) {
        List<VendaSimpleDTO> vendas = vendaService.findVendasByClienteId(clienteId);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<VendaSimpleDTO>> findVendasByLivroId(@PathVariable Long livroId) {
        List<VendaSimpleDTO> vendas = vendaService.findVendasByLivroId(livroId);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<VendaSimpleDTO>> findVendasByPeriodo(
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dataFim) {
        List<VendaSimpleDTO> vendas = vendaService.findVendasByPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/nota-fiscal/{notaFiscal}")
    public ResponseEntity<List<VendaSimpleDTO>> findVendasByNotaFiscal(@PathVariable String notaFiscal) {
        List<VendaSimpleDTO> vendas = vendaService.findVendasByNotaFiscal(notaFiscal);
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{clienteId}/{livroId}")
    public ResponseEntity<VendaResponseDTO> updateVenda(
            @PathVariable Long clienteId,
            @PathVariable Long livroId,
            @Valid @RequestBody VendaRequestDTO vendaRequestDTO) {
        VendaResponseDTO response = vendaService.updateVenda(clienteId, livroId, vendaRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clienteId}/{livroId}")
    public ResponseEntity<Void> deleteVenda(
            @PathVariable Long clienteId,
            @PathVariable Long livroId) {
        vendaService.deleteVenda(clienteId, livroId);
        return ResponseEntity.noContent().build();
    }
}
