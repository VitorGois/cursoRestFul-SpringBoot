package com.example.cursorestfulspringboot.controllers;

import java.util.List;
import com.example.cursorestfulspringboot.model.Pedido;
import com.example.cursorestfulspringboot.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pedidos")

public class PedidoController {

    
    @Autowired
    private PedidoService servico;

    @GetMapping
    public List<Pedido> getPedidos() {
        return servico.getAllPedidos();
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Pedido> getPedidoByNumero(@PathVariable long numero) {
        Pedido pedido= servico.getPedidoByNumero(numero);
        return ResponseEntity.ok(pedido);	
    }

    

}
