package com.example.cursorestfulspringboot.service;

import java.util.List;
import java.util.Optional;

import com.example.cursorestfulspringboot.dto.ClienteDTO;
import com.example.cursorestfulspringboot.model.Cliente;
import com.example.cursorestfulspringboot.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repositorio;

    public Cliente getClienteById(int id){
        Optional<Cliente> op = repositorio.getClienteById(id);
        return op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente nao cadastrado: " + id));
    }



    public Cliente fromDTO(ClienteDTO dto){
        Cliente cliente = new Cliente();
        cliente.setEndereco(dto.getEndereco());
        cliente.setNome(dto.getNome());
        return cliente;
    }



	public Cliente update(Cliente cliente) {
        getClienteById(cliente.getId());
        return repositorio.update(cliente);
    }



	public List<Cliente> getAllClientes() {
		return repositorio.getAllClientes();
	}



	public Cliente salvar(Cliente cliente) {
		return repositorio.salvar(cliente);
	}



	public void removeById(int id) {
       repositorio.remove(getClienteById(id));
   }




}
