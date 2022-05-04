package com.example.tccbcc.models;

public class UsuarioBeneficiador extends Usuario {
    private Pet pet;

    public UsuarioBeneficiador() {
    }

    public UsuarioBeneficiador(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
