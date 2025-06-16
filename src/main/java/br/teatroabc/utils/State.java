package br.teatroabc.utils;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import br.teatroabc.controllers.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class State {

    Cliente cliente;
    Venda vendaAtual;
    ArrayList<ItemVenda> VendasAtuais;
    String peca;
    String sessao;
    String turno;

    public static int getIvCounter() {
        return ivCounter;
    }

    public static void setIvCounter(int ivCounterArg) {
        ivCounter = ivCounterArg;
    }

    static int ivCounter = GeneralUse.getIdItemVendaCounter();


    public State() {
        this.cliente = new Cliente();
        this.vendaAtual = new Venda(this.cliente.getId());
        this.VendasAtuais = new ArrayList<ItemVenda>();
    }

    public static void Finish() throws IOException {
//        String headerVenda = "Id, IdCliente, IdItemVenda";
//        String headerItemVenda = "id, poltronaId, areaId, sessaoId, pecaId";
//        String headerCliente = "id, nome, dataNascimento, telefone, endereco";
        // Obter o estado atual
        State cs = Controller.getCurrentState();
        File csvCliente = Paths.get("data/BD/Cliente.csv").toFile();
        File csvVenda = Paths.get("data/BD/Venda.csv").toFile();
        File csvItemVenda = Paths.get("data/BD/ItemVenda.csv").toFile();

        // Verificar duplicação de CPF e obter o ID real do cliente
        int realClienteId = getClienteIdByCPF(cs.getCliente().getCPF());
        if (realClienteId == -1) { // Cliente ainda não existe
            CSVUtils.appendCSV(csvCliente, cs.getCliente().parseToCsv(), 0);
            realClienteId = cs.getCliente().getId(); // ID recém-gerado usando o getter
        } else {
            // Atualizar o ID do cliente no estado
            cs.getCliente().setId(realClienteId); // Atualização com um setter criado
        }

        // Salvar venda com o ID real do cliente
        cs.getVenda().setIdCliente(realClienteId); // Ajustar o ID do cliente na venda
        CSVUtils.appendCSV(csvVenda, cs.getVenda().parseToCsv(), 1);

        // Salvar itens da venda
        cs.getVendasAtuais().forEach(itemVenda -> {
            try {
                CSVUtils.appendCSV(csvItemVenda, itemVenda.parseToCsv(), 2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static int getClienteIdByCPF(String cpf) throws IOException {
        List<String[]> clientes = CSVUtils.readCSV(0); // Ler Cliente.csv
        for (String[] cliente : clientes) {
            if (cliente[2].equals(cpf)) { // Índice 2 corresponde ao CPF
                return Integer.parseInt(cliente[0]); // Retornar o ID do cliente
            }
        }
        return -1; // Cliente não encontrado
    }

    private static boolean isCpfDuplicate(String cpf) {
        try {
            List<String[]> existingClients = CSVUtils.readCSV(0); // Index 0 para Cliente.csv
            for (String[] row : existingClients) {
                if (row[2].equals(cpf)) { // Índice 2 é o campo CPF
                    return true; // CPF já existe
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // CPF não encontrado
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
