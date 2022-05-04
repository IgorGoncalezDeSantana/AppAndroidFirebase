package com.example.tccbcc.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Usuario implements Parcelable {
    private String uuid;
    private String username;
    private String email;
    private String cpf;
    private Date dataNascimento;
    private String telefone;
    private String genero;
    private Endereco endereco;
    private ArrayList<Foto> fotos;
    private boolean beneficiador;

    public Usuario() {
    }

    public Usuario(String uuid, String username, ArrayList<Foto> fotos) {
        this.uuid = uuid;
        this.username = username;
        this.fotos = fotos;
    }

    protected Usuario(Parcel in) {
        uuid = in.readString();
        username = in.readString();

        fotos = new ArrayList<>();
        fotos.add(new Foto(in.readString()));
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ArrayList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }

    public boolean addFoto(Foto foto) {
        return fotos.add(foto);
    }

    public boolean removeFoto(Foto foto) {
        return fotos.remove(foto);
    }

    public boolean isBeneficiador() {
        return beneficiador;
    }

    public void setBeneficiador(boolean beneficiador) {
        this.beneficiador = beneficiador;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(username);

        parcel.writeString(
                (
                        getFotos() != null && getFotos().get(0) != null
                ) ? getFotos().get(0).getUri() : ""
        );
    }
}
