package com.example.cursorestfulspringboot.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.example.cursorestfulspringboot.dto.ClienteDTO;
import com.example.cursorestfulspringboot.dto.PedidoDTO;
import com.example.cursorestfulspringboot.model.Cliente;
import com.example.cursorestfulspringboot.model.Pedido;
import com.example.cursorestfulspringboot.service.ClienteService;
import com.example.cursorestfulspringboot.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService servicoCliente;

    @Autowired
    private PedidoService servicoPedido;


    @GetMapping
    public List<Cliente> getClientes() {
        return servicoCliente.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        Cliente cli = servicoCliente.getClienteById(id);
        return ResponseEntity.ok(cli);
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid  @RequestBody ClienteDTO novoCliente,
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder
                                     )
   {

        Cliente cli = servicoCliente.salvar(servicoCliente.fromDTO(novoCliente));
        UriComponents uriComponents = builder.path(request.getRequestURI() + "/" + cli.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id) {
        servicoCliente.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = servicoCliente.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = servicoCliente.update(cliente);
        return ResponseEntity.ok(cliente);

    }

    @PostMapping("{id}/pedidos")
    public ResponseEntity<Void> salvar(@PathVariable int id,
                                       @RequestBody Pedido pedido, 
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder) {

        pedido = servicoPedido.salvar(pedido, id);
        UriComponents uriComponents = builder.path(request.getRequestURI() + "/" + pedido.getNumero()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }


    @GetMapping("{id}/pedidos")
    public List<PedidoDTO> getPedidosCliente(@PathVariable int id) {
        Cliente cliente = servicoCliente.getClienteById(id);
        return servicoPedido.toListDTO(cliente.getPedidos());
    }

}
