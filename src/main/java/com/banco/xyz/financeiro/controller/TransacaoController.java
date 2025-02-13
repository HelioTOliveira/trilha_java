package com.banco.xyz.financeiro.controller;

import com.banco.xyz.financeiro.constant.PerfisUsuarios;
import com.banco.xyz.financeiro.dto.TransacaoAtualizarDTO;
import com.banco.xyz.financeiro.dto.TransacaoDTO;
import com.banco.xyz.financeiro.recod.TransacaoRecord;
import com.banco.xyz.financeiro.service.TransacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@SecurityRequirement(name = "bearerAPI")
public class TransacaoController {


    @Autowired
    private TransacaoService transacaoService;

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoRecord> consultatransacao(@PathVariable("id") Long id){

        return ResponseEntity.ok(transacaoService.getTransacao(id));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA_GERENTE)
    @GetMapping
    public ResponseEntity<Page<TransacaoRecord>> listatransacao(@PageableDefault(page = 0, size = 10, sort = {"idConta"}) Pageable paginacao){

        return ResponseEntity.ok(transacaoService.listaTransacao(paginacao));
    }

    @PreAuthorize(PerfisUsuarios.CORRENTISTA)
    @PostMapping
    public ResponseEntity<String> salvartransacao(@RequestBody TransacaoDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.salvarTransacao(transacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @PutMapping
    public ResponseEntity<String> atualizartransacao(@RequestBody TransacaoAtualizarDTO transacaoDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.atualizarTransacao(transacaoDTO));
    }

    @PreAuthorize(PerfisUsuarios.GERENTE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirtransacao(@PathVariable("id") Long id){

        return transacaoService.excluirTransacao(id);
    }

}
