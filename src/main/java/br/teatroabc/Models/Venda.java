package br.teatroabc.Models;

import br.teatroabc.utils.GeneralUse;

public class Venda {
    int id;
    int idCliente;

    public Venda(int idCliente) {
        this.id = GeneralUse.getIdVendaCounter()+1;
        this.idCliente = idCliente;
    }

    public String[] parseToCsv(){
        String[] csvLine = new String[2];
        csvLine[0] = Integer.toString(this.idCliente);
        csvLine[1] = Integer.toString(this.id);
        return csvLine;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }
}
