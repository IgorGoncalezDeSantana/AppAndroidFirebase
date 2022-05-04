package com.example.tccbcc.models;

import java.util.ArrayList;
import java.util.Date;

public class Pet {
    private String nome;
    private ArrayList<Treinamento> treinamentos;
    private ArrayList<Vacina> vacinacoes;
    private Date data;
    private String descricao;
    private String raca;
    private String sexo;

    public Pet() {
        treinamentos = new ArrayList<>();
        vacinacoes = new ArrayList<>();
    }

    public Pet(String nome, ArrayList<Treinamento> treinamentos, ArrayList<Vacina> vacinacoes, Date data, String descricao, String raca, String sexo) {
        this.nome = nome;
        this.treinamentos = treinamentos;
        this.vacinacoes = vacinacoes;
        this.data = data;
        this.descricao = descricao;
        this.raca = raca;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Treinamento> getTreinamentos() {
        return treinamentos;
    }

    public void setTreinamentos(ArrayList<Treinamento> treinamentos) {
        this.treinamentos = treinamentos;
    }

    public ArrayList<Vacina> getVacinacoes() {
        return vacinacoes;
    }

    public void setVacinacoes(ArrayList<Vacina> vacinacoes) {
        this.vacinacoes = vacinacoes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean addVacina(Vacina vacina){
        return vacinacoes.add(vacina);
    }

    public boolean removeVacina(Vacina vacina){
        return vacinacoes.remove(vacina);
    }

    public boolean addTreinamento(Treinamento treinamento){
        return treinamentos.add(treinamento);
    }

    public boolean removeTreinamento(Treinamento treinamento){
        return treinamentos.remove(treinamento);
    }
}
