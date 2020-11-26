package com.example.cursorestfulspringboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.cursorestfulspringboot.dto.PedidoDTO;
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

    public PedidoDTO toDTO(Pedido pedido) {

        PedidoDTO dto = new PedidoDTO();

        dto.setDataPedido(pedido.getDataPedido());
        dto.setDescricao(pedido.getDescricao());
        dto.setItens(pedido.getItens());
        dto.setNumero(pedido.getNumero());
        dto.setPedidoFechado(pedido.isPedidoFechado());
        dto.setTotalPedido(pedido.totalPedido());
        return dto;
    }

    public List<PedidoDTO> toListDTO(List<Pedido> pedidos) {
        ArrayList<PedidoDTO> dtoList = new ArrayList<PedidoDTO>();

        for (Pedido pedido : pedidos) {
            dtoList.add(toDTO(pedido));
        }

        return dtoList;
    }

    public List<Pedido> getAllPedidos() {
        return repositorio.getAllPedidos();
    }

    public Pedido getPedidoByNumero(long numero) {
        Optional<Pedido> op = repositorio.getPedidoByNumero(numero);
        return op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao cadastrado"));
    }

    public Pedido salvar(Pedido pedido, int idCliente) {

        // Verificar se existe o idCliente, senao existir 404 NotFound
        // Se lancar o 404, finaliza o metodo salvar Pedido
        Cliente cliente = clienteService.getClienteById(idCliente);

        pedido.setDataPedido(LocalDateTime.now());

        // Associar um pedido ao cliente e um cliente ao pedido
        pedido.setCliente(cliente);
        cliente.addPedido(pedido);

        return repositorio.salvar(pedido);
    }

}
