package br.teatroabc.Models;

import br.teatroabc.utils.GeneralUse;

public class ItemVenda {
    int id;
    String poltronaId;
    String area;
    String sessao;
    String peca;
    int idVenda;

    public ItemVenda(String poltronaId, String areaId, String sessaoId, String pecaId, int vendaId) {
        this.id = GeneralUse.getIdItemVendaCounter() + 1;
        this.poltronaId = poltronaId;
        this.area = areaId;
        this.sessao = sessaoId;
        this.peca = pecaId;
        this.idVenda = vendaId;
    }

    public String[] parseToCsv() {
        String[] csvLine = new String[6];
        csvLine[0] = Integer.toString(this.id);
        csvLine[1] = this.poltronaId;
        csvLine[2] = this.area;
        csvLine[3] = this.sessao;
        csvLine[4] = this.peca;
        csvLine[5] = Integer.toString(this.idVenda);
        return csvLine;
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

    public String getSessaoId() {
        return sessao;
    }

    public void setSessaoId(String sessaoId) {
        this.sessao = sessaoId;
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
