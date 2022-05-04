package com.example.tccbcc.models;

public class UsuarioBeneficiado extends Usuario {
    private String necessidadeEspecial;
    private Dependente dependente;

    public UsuarioBeneficiado() {
    }

    public UsuarioBeneficiado(String necessidadeEspecial, Dependente dependente) {
        this.necessidadeEspecial = necessidadeEspecial;
        this.dependente = dependente;
    }

    public String getNecessidadeEspecial() {
        return necessidadeEspecial;
    }

    public void setNecessidadeEspecial(String necessidadeEspecial) {
        this.necessidadeEspecial = necessidadeEspecial;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }
}
