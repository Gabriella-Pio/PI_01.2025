package br.teatroabc.Models;

import br.teatroabc.utils.GeneralUse;

import java.time.LocalDate;
import java.util.Date;

public class Cliente {
    int id;
    String nome;
    String CPF;
    LocalDate DataNasc;
    String telefone;
    String endereco;



    //        String headerCliente = "id, nome, dataNascimento, telefone, endereco";
    public String[] parseToCsv(){
        String[] csvLine = new String[6];
        csvLine[0] = Integer.toString(this.id);
        csvLine[1] = deNullifier(this.nome);
        csvLine[2] = this.CPF;
        csvLine[3] = this.DataNasc.toString();
        csvLine[4] = deNullifier(this.telefone);
        csvLine[5] = deNullifier(this.endereco);
        return csvLine;
    }

    public static String deNullifier(String t){
        if(t==null || t.isEmpty()){
            return "null";
        }
        return t;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public LocalDate getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        DataNasc = dataNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public Cliente(){
        this.id = GeneralUse.getIdClienteCounter()+1;
    }


    public Cliente(String nome, String CPF, LocalDate dataNasc, String telefone, String endereco) {
        this.id = GeneralUse.getIdClienteCounter()+1;
        this.nome = nome;
        this.CPF = CPF;
        this.DataNasc = dataNasc;
        this.telefone = telefone;
        this.endereco = endereco;
    }

}
