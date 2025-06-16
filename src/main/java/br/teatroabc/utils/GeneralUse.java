package br.teatroabc.utils;

import java.util.List;

public class GeneralUse {

    public static int getIdClienteCounter() {
        // Take the last ID from the CSV at data/BD/Cliente.csv
        try {
            List<String[]> rows = CSVUtils.readCSV(0); // Index 0 for Cliente.csv
            if (rows.isEmpty()) {
                return 1; // Return 1 if CSV is empty
            }
            String[] lastRow = rows.get(rows.size() - 1); // Get the last row
            return Integer.parseInt(lastRow[0]) + 1; // Increment last ID by 1
        } catch (Exception e) {
            e.printStackTrace();
            return 1; // Default to 1 in case of an error
        }
    }

    public static int getIdItemVendaCounter() {
        // Take the last ID from the CSV at data/BD/ItemVenda.csv
        try {
            List<String[]> rows = CSVUtils.readCSV(2); // Index 2 for ItemVenda.csv
            if (rows.isEmpty()) {
                return 1; // Return 1 if CSV is empty
            }
            String[] lastRow = rows.get(rows.size() - 1); // Get the last row
            return Integer.parseInt(lastRow[0]) + 1; // Increment last ID by 1
        } catch (Exception e) {
            e.printStackTrace();
            return 1; // Default to 1 in case of an error
        }
    }

    public static int getIdVendaCounter() {
        // Take the last ID from the CSV at data/BD/Venda.csv
        try {
            List<String[]> rows = CSVUtils.readCSV(1); // Index 1 for Venda.csv
            if (rows.isEmpty()) {
                return 1; // Return 1 if CSV is empty
            }
            String[] lastRow = rows.get(rows.size() - 1); // Get the last row
            return Integer.parseInt(lastRow[0]) + 1; // Increment last ID by 1
        } catch (Exception e) {
            e.printStackTrace();
            return 1; // Default to 1 in case of an error
        }
    }

    public static boolean isCPFValid(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Validações básicas
        if (cpf.length() != 11
                || cpf.equals("00000000000")
                || cpf.equals("11111111111")
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            return false;
        }

        try {
            int d1 = 0, d2 = 0, digito1 = 0, digito2 = 0, resto = 0;

            // Calcula o primeiro dígito verificador
            for (int i = 1; i <= 9; i++) {
                d1 += Integer.parseInt(cpf.substring(i - 1, i)) * (11 - i);
            }
            resto = (d1 % 11);
            if (resto < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - resto;
            }

            // Calcula o segundo dígito verificador
            d2 = 0;
            for (int i = 1; i <= 10; i++) {
                d2 += Integer.parseInt(cpf.substring(i - 1, i)) * (12 - i);
            }
            resto = (d2 % 11);
            if (resto < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - resto;
            }

            // Verifica se os dígitos calculados conferem com os dígitos informados no CPF
            String digitos = cpf.substring(9, 11);
            return digitos.equals(Integer.toString(digito1) + Integer.toString(digito2));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
