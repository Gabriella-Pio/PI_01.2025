package br.teatroabc.utils;

public class GeneralUse {

    public static int getIdClienteCounter() {
        //todo Pegar O ultimo ID do CSV
        return 0;
    }

    public static int getIdItemVendaCounter() {
        return 0;
    }

    public static int getIdVendaCounter() {
        return 0;
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
