package com.example.cursorestfulspringboot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.cursorestfulspringboot.model.Cliente;
import com.example.cursorestfulspringboot.model.Pedido;
import com.example.cursorestfulspringboot.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repositorio;

    @Autowired
    private ClienteService clienteService;

    public Pedido  getPedidoByNumero(long numero){
        Optional<Pedido> op = repositorio.getPedidoByNumero(numero);
        return op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido nao cadastrado: " + numero));
    }

    public List<Pedido>  getAllPedidos(){
        return repositorio.getAllPedidos();
    }

	public Pedido salvar(int idCliente, Pedido pedido) {

        //Se nao existir lanca o 404 e finaliza!
        Cliente cliente = clienteService.getClienteById(idCliente);

        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente);
        cliente.addPedido(pedido);

        return  repositorio.salvar(pedido);
    }
}
