package br.teatroabc.Models;

import br.teatroabc.utils.GeneralUse;
import br.teatroabc.utils.State;

public class ItemVenda {
    int id;
    String poltronaId;
    String area;
    String dataSessao;  // Changed from 'sessao' to be more specific
    String turnoSessao; // New field for time of day
    String peca;
    float preco;
    int idVenda;

    public ItemVenda(String poltronaId, String areaId, String dataSessao, String turnoSessao, String pecaId, int vendaId) {
        this.id = State.getIvCounter();
        this.poltronaId = poltronaId;
        this.area = areaId;
        this.dataSessao = dataSessao;
        this.turnoSessao = turnoSessao;
        this.peca = pecaId;
        this.idVenda = vendaId;
    }

    public String[] parseToCsv() {
        String[] csvLine = new String[7]; // Increased size to 7
        csvLine[0] = Integer.toString(this.id);
        csvLine[1] = this.poltronaId;
        csvLine[2] = this.area;
        csvLine[3] = this.dataSessao;
        csvLine[4] = this.turnoSessao; // New field
        csvLine[5] = this.peca;
        csvLine[6] = Integer.toString(this.idVenda);
        return csvLine;
    }

    // Updated getters and setters
    public String getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(String dataSessao) {
        this.dataSessao = dataSessao;
    }

    public String getTurnoSessao() {
        return turnoSessao;
    }

    public void setTurnoSessao(String turnoSessao) {
        this.turnoSessao = turnoSessao;
    }

    public String getPoltronaId() {
        return poltronaId;
    }

    public void setPoltronaId(String poltronaId) {
        this.poltronaId = poltronaId;
    }

    public String getAreaId() {
        return area;
    }

    public void setAreaId(String areaId) {
        this.area = areaId;
    }

    public String getPecaId() {
        return peca;
    }

    public void setPecaId(String pecaId) {
        this.peca = pecaId;
    }

    public int getVendaId() {
        return idVenda;
    }

    public void setVendaId(int idVenda) {
        this.idVenda = idVenda;
    }
}
