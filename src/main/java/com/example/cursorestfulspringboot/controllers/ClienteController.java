package com.example.cursorestfulspringboot.controllers;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    // Atributo
    List<Cliente> clientes;  // vetor para armazenar os clientes em uma "list"

    @PostConstruct  // Assim que o Spring contruir o objeto, executar o método init
    public void init() {
        Cliente c1 = new Cliente(); // Variável
        c1.id = 1;
        c1.nome = "José";
        c1.endereco = "Rua X, 99";
        c1.saldo = 202.0;
        
        Cliente c2 = new Cliente();
        c2.id = 2;
        c2.nome = "Maria";
        c2.endereco = "Rua Y, 59";
        c2.saldo = 444.0;
        
        Cliente c3 = new Cliente();
        c3.id = 3;
        c3.nome = "Fernanda";
        c3.endereco = "Rua W, 33";
        c3.saldo = 332.0;

        // Classe utilitária
        clientes = Arrays.asList(c1,c2,c3);
    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return clientes;
    }

    @GetMapping("/clientes/{id}")
    public Cliente getClienteById(@PathVariable int id) {
        Cliente cliente = null;

        // Percorre o laço até que seja encontrado o compátivel, criando uma váriavel auxiliar(objeto)
        for (Cliente aux : clientes) {
            // Comparando todos os objetos clientes com a id passado
            if (aux.id == id) { 
                cliente = aux;
                break;
            }
        }

        return cliente;
    }
}