package br.teatroabc.utils;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.controllers.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class State {

    Cliente cliente;
    Venda vendaAtual;
    ArrayList<ItemVenda> VendasAtuais;
    String peca;
    String sessao;
    String turno;


    public State() {
        this.cliente = new Cliente();
        this.vendaAtual = new Venda(this.cliente.getId());
        this.VendasAtuais = new ArrayList<ItemVenda>();
    }

    public static void Finish() throws IOException {
//        String headerVenda = "Id, IdCliente, IdItemVenda";
//        String headerItemVenda = "id, poltronaId, areaId, sessaoId, pecaId";
//        String headerCliente = "id, nome, dataNascimento, telefone, endereco";
        State cs = Controller.getCurrentState();
        File csvCliente = Paths.get("data/BD/Cliente.csv").toFile();
        File csvVenda = Paths.get("data/BD/Venda.csv").toFile();
        File csvItemVenda = Paths.get("data/BD/ItemVenda.csv").toFile();
        CSVUtils.appendCSV(csvCliente, cs.getCliente().parseToCsv(),0);
        CSVUtils.appendCSV(csvVenda, cs.getVenda().parseToCsv(),1);
        cs.getVendasAtuais().forEach(itemVenda -> {
            try {
                CSVUtils.appendCSV(csvItemVenda, itemVenda.parseToCsv(),2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String getPeca() {
        return peca;
    }

    public void setPeca(String peca) {
        this.peca = peca;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venda getVenda() {
        return vendaAtual;
    }

    public ArrayList<ItemVenda> getVendasAtuais() {
        return VendasAtuais;
    }

}
