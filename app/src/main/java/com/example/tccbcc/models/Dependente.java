package com.example.tccbcc.models;

import java.util.Date;

public class Dependente {
    private String nome;
    private String genero;
    private Date dataNascimento;
    private String necessidadeEspecial;

    public Dependente() {
    }

    public Dependente(String nome, String genero, Date dataNascimento, String necessidadeEspecial) {
        this.nome = nome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.necessidadeEspecial = necessidadeEspecial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNecessidadeEspecial() {
        return necessidadeEspecial;
    }

    public void setNecessidadeEspecial(String necessidadeEspecial) {
        this.necessidadeEspecial = necessidadeEspecial;
    }
}
