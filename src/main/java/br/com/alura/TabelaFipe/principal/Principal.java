package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();
    private ConsumoApi consumo = new ConsumoApi();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        var menu = """
            *** OPÇÕES ***
            Carro
            Moto
            Caminhão
            
            Digite uma das opções para consulta:
            
            """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String enderenco;

        if (opcao.toLowerCase().contains("carr")){
            enderenco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            enderenco = URL_BASE + "motos/marcas";
        } else {
            enderenco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(enderenco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca para consulta ");
        var codigoMarca = leitura.nextLine();

        enderenco = enderenco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(enderenco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelo dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach((System.out::println));

    }
}

