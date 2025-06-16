package br.teatroabc.utils;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EstatisticasService {

    private List<Cliente> clientes;
    private List<Venda> vendas;
    private List<ItemVenda> itensVenda;

    public static final Map<String, Double> PRECO_POR_AREA = new HashMap<>();

    static {
        PRECO_POR_AREA.put("PA", 40.0);
        PRECO_POR_AREA.put("PB", 60.0);
        PRECO_POR_AREA.put("CA", 80.0);
        PRECO_POR_AREA.put("CB", 80.0);
        PRECO_POR_AREA.put("CC", 80.0);
        PRECO_POR_AREA.put("CD", 80.0);
        PRECO_POR_AREA.put("CE", 80.0);
        PRECO_POR_AREA.put("FA", 120.0);
        PRECO_POR_AREA.put("FB", 120.0);
        PRECO_POR_AREA.put("FC", 120.0);
        PRECO_POR_AREA.put("FD", 120.0);
        PRECO_POR_AREA.put("FE", 120.0);
        PRECO_POR_AREA.put("FF", 120.0);
        PRECO_POR_AREA.put("BN", 250.0);
    }
    public EstatisticasService(String clienteCSVPath, String vendaCSVPath, String itemVendaCSVPath) throws IOException, CsvException {
        this.clientes = loadClientes(clienteCSVPath);
        this.vendas = loadVendas(vendaCSVPath);
        this.itensVenda = loadItensVenda(itemVendaCSVPath);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    // Helper Methods to Load Data from CSV Files
    private List<Cliente> loadClientes(String csvPath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // Remove header
            return rows.stream()
                    .map(row -> new Cliente(
                            Integer.parseInt(row[0]), // ID
                            row[1],                   // Nome
                            row[2],                   // CPF
                            row[3],                   //Data Nascimento
                            row[4],                   //Telefone
                            row[5]                    // Endereço
                    ))
                    .toList();
        }
    }

    private List<Venda> loadVendas(String csvPath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // Remove header
            return rows.stream()
                    .map(row -> new Venda(
                            Integer.parseInt(row[0]), // ID
                            Integer.parseInt(row[1])  // IDCliente
                    ))
                    .toList();
        }
    }

    private List<ItemVenda> loadItensVenda(String csvPath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // Remove header
            return rows.stream()
                    .map(row -> new ItemVenda(
                            row[1],                   // poltronaId
                            row[2],                   // areaId
                            row[3],                   // dataSessao
                            row[4],                   // turnoSessao (new field)
                            row[5],                   // pecaId
                            Integer.parseInt(row[6])   // vendaId
                    ))
                            .toList();
        }
    }

    // Existing Methods (No changes needed)
    public String getPecaMaisVendida() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("Nenhuma");
    }

    public String getPecaMenosVendida() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()))
                .entrySet().stream().min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("Nenhuma");
    }

    public Map<String, Double> getReceitaPorPeca() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId,
                        Collectors.summingDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))));
    }

    public Map<String, Double> getReceitaPorArea() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getAreaId,
                        Collectors.summingDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))));
    }

    public Map<String, Double> getTicketMedioPorCliente() {
        // Map para armazenar o gasto total e o número de compras por cliente
        Map<Integer, double[]> clienteData = vendas.stream()
                .collect(Collectors.toMap(
                        Venda::getIdCliente,
                        venda -> {
                            double totalSpent = itensVenda.stream()
                                    .filter(item -> item.getVendaId() == venda.getId())
                                    .mapToDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))
                                    .sum();
                            return new double[]{totalSpent, 1}; // [totalSpent, purchaseCount]
                        },
                        (existing, replacement) -> { // Merge function para chaves duplicadas
                            existing[0] += replacement[0]; // Soma o gasto total
                            existing[1] += replacement[1]; // Soma o número de compras
                            return existing;
                        }
                ));

        // Converte os dados do cliente para um mapa de CPF e valor médio
        return clienteData.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> clientes.stream()
                                .filter(cliente -> cliente.getId() == entry.getKey())
                                .map(Cliente::getCPF) // Usa o CPF do cliente
                                .findFirst()
                                .orElse("CPF Desconhecido"), // Trata clientes ausentes
                        entry -> entry.getValue()[0] / entry.getValue()[1], // Calcula o ticket médio
                        (existing, replacement) -> existing // Mantém entradas duplicadas como estão
                ));
    }


    public Map<String, Long> getIngressosPorPeca() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()));
    }

    public Map.Entry<String, Long> getSessaoMaiorOcupacao() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getTurnoSessao, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    public Map.Entry<String, Long> getSessaoMenorOcupacao() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getTurnoSessao, Collectors.counting()))
                .entrySet().stream().min(Map.Entry.comparingByValue())
                .orElse(null);
    }

    public Map<String, Double> getReceitaMediaPorArea() {
        Map<String, Double> receitaTotal = getReceitaPorArea();
        Map<String, Long> ingressosPorArea = itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getAreaId, Collectors.counting()));

        return receitaTotal.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() / ingressosPorArea.getOrDefault(entry.getKey(), 1L)
                ));
    }

    public Map<String, Double> getLucroMedioPorPeca() {
        Map<String, Double> receitaTotal = getReceitaPorPeca();
        Map<String, Long> ingressosPorPeca = getIngressosPorPeca();

        return receitaTotal.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() / ingressosPorPeca.getOrDefault(entry.getKey(), 1L)
                ));
    }
}
