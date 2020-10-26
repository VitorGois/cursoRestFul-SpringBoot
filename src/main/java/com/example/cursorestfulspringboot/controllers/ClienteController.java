package com.example.cursorestfulspringboot.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.example.cursorestfulspringboot.dto.ClienteDTO;
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
    private ClienteService clienteServico;

    @Autowired
    private PedidoService pedidoServico;


    
    @GetMapping
    public List<Cliente> getClientes() {
        return clienteServico.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteByCodigo(@PathVariable int id) {
        Cliente cli = clienteServico.getClienteById(id);
        return ResponseEntity.ok(cli);	
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ClienteDTO novoCliente, 
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder
                                       ) {
      
        Cliente cli = clienteServico.salvar(clienteServico.fromDTO(novoCliente));
        UriComponents uriComponents = builder.path(request.getRequestURI()+"/"+cli.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id){
        clienteServico.removeById(id);
        return ResponseEntity.noContent().build();	
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id, @RequestBody ClienteDTO clienteDTO){
    
        Cliente cliente = clienteServico.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteServico.update(cliente);
        return ResponseEntity.ok(cliente);
        
    }
    
    @GetMapping("/{id}/pedidos")
    public List<Pedido> getPedidosCliente(@PathVariable int id) {
        Cliente cli = clienteServico.getClienteById(id);
        return cli.getPedidos();	
    }

    @PostMapping("/{id}/pedidos")
    public ResponseEntity<Void> salvar(@PathVariable int id,
                                       @RequestBody Pedido pedido, 
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder
                                       ) {
      
        pedido = pedidoServico.salvar(id, pedido);
        UriComponents uriComponents = builder.path(request.getRequestURI()+"/"+pedido.getNumero()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }


}








