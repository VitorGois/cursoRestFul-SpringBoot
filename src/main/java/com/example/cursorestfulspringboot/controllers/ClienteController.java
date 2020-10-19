package com.example.cursorestfulspringboot.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.example.cursorestfulspringboot.dto.ClienteDTO;
import com.example.cursorestfulspringboot.model.Cliente;
import com.example.cursorestfulspringboot.service.ClienteService;
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
    private ClienteService servico;

    @GetMapping
    public List<Cliente> getClientes() {
        return servico.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteByCodigo(@PathVariable int id) {
        Cliente cli = servico.getClienteById(id);
        return ResponseEntity.ok(cli);	
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ClienteDTO novoCliente, 
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder
                                       ) {
      
        Cliente cli = servico.salvar(servico.fromDTO(novoCliente));
        UriComponents uriComponents = builder.path(request.getRequestURI()+"/"+cli.getId()).build();
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id){
        servico.removeById(id);
        return ResponseEntity.noContent().build();	
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id, @RequestBody ClienteDTO clienteDTO){
    
        Cliente cliente = servico.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = servico.update(cliente);
        return ResponseEntity.ok(cliente);
    } 

}
