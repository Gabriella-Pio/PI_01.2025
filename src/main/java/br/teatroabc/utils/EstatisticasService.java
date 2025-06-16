package br.teatroabc.utils;

import br.teatroabc.Models.Cliente;
import br.teatroabc.Models.ItemVenda;
import br.teatroabc.Models.Venda;

import java.util.*;
import java.util.stream.Collectors;

public class EstatisticasService {

    private List<Cliente> clientes;
    private List<Venda> vendas;
    private List<ItemVenda> itensVenda;

    private static final Map<String, Double> PRECO_POR_AREA = Map.of(
            "FC", 50.0, "PB", 60.0, "PA", 40.0, "FB", 70.0, "CD", 100.0, "BN", 250.0
    );

    public EstatisticasService(List<Cliente> clientes, List<Venda> vendas, List<ItemVenda> itensVenda) {
        this.clientes = clientes;
        this.vendas = vendas;
        this.itensVenda = itensVenda;
    }

    // Peça com mais ingressos vendidos
    public String getPecaMaisVendida() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("Nenhuma");
    }

    // Peça com menos ingressos vendidos
    public String getPecaMenosVendida() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()))
                .entrySet().stream().min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("Nenhuma");
    }

    // Receita total por peça
    public Map<String, Double> getReceitaPorPeca() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId,
                        Collectors.summingDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))));
    }

    // Receita média por área
    public Map<String, Double> getReceitaPorArea() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getAreaId,
                        Collectors.summingDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))));
    }

    // Ticket médio por cliente
    public double getTicketMedioPorCliente() {
        Map<String, Double> gastoPorCliente = vendas.stream()
                .collect(Collectors.toMap(
                        venda -> String.valueOf(venda.getIdCliente()), // Converte Integer para String
                        venda -> itensVenda.stream()
                                .filter(item -> item.getVendaId() == venda.getId()) // Comparação direta para int
                                .mapToDouble(item -> PRECO_POR_AREA.getOrDefault(item.getAreaId(), 0.0))
                                .sum()));

        return gastoPorCliente.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // Total de ingressos vendidos por peça
    public Map<String, Long> getIngressosPorPeca() {
        return itensVenda.stream()
                .collect(Collectors.groupingBy(ItemVenda::getPecaId, Collectors.counting()));
    }
}
