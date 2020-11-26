package com.example.cursorestfulspringboot.dto;


import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class ClienteDTO {
    

    @NotBlank(message ="Nome é obrigatório!")
    @Length(min=4, max=40, message = "Nome deve ter mínimo de 4 e máximo de 40 caracteres!")
    private String nome;
    
    
    @NotBlank(message ="Endereço é obrigatório!")
    @Length(min=4, max=40, message = "Endereço deve ter mínimo de 4 e máximo de 40 caracteres!")
    private String endereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    
}
