import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Leitura {
    public static ArrayList<Medico> leitura_medico()
    {
        String NOME_ARQUIVO = "dados\\Medicos.csv";
        String SEPARADOR = ",";

        List<List<String>> registros = new ArrayList<>();
        ArrayList<Medico> lista_medicos = new ArrayList<>();
        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);
            String linha = scanner_arquivo.nextLine(); // lê o cabeçalho
            while (scanner_arquivo.hasNextLine())
            {
                linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);

                String nome = scanner_linha.next();
                int codigo = scanner_linha.nextInt();


                // System.out.println(nome + " " + codigo);

                lista_medicos.add(new Medico(nome, codigo));

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return lista_medicos;
    }
    public static ArrayList<Paciente> leitura_pacientes()
    {
        String NOME_ARQUIVO = "dados\\Pacientes.csv";
        String SEPARADOR = ",";

        List<List<String>> registros = new ArrayList<>();
        ArrayList<Paciente> lista_pacientes = new ArrayList<>();
        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);
            String linha = scanner_arquivo.nextLine(); // lê o cabeçalho
            while (scanner_arquivo.hasNextLine())
            {
                linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);

                String nome = scanner_linha.next();
                long cpf = scanner_linha.nextLong();


                // System.out.println(nome + " " + cpf);

                lista_pacientes.add(new Paciente(nome, cpf));

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return lista_pacientes;

    }
    static DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    public static ArrayList<Consulta> leitura_consulta()
    {
        String NOME_ARQUIVO = "dados\\Consultas.csv";
        String SEPARADOR = ",";

        List<List<String>> registros = new ArrayList<>();
        ArrayList<Consulta> lista_consultas = new ArrayList<>();
        try
        {
            File arquivo = new File(NOME_ARQUIVO);
            Scanner scanner_arquivo = new Scanner(arquivo);
            String linha = scanner_arquivo.nextLine(); // lê o cabeçalho
            while (scanner_arquivo.hasNextLine())
            {
                linha = scanner_arquivo.nextLine();
                Scanner scanner_linha = new Scanner(linha);
                scanner_linha.useDelimiter(SEPARADOR);



                LocalDate data = LocalDate.parse(scanner_linha.next(), formatoBR);
                LocalTime horario = LocalTime.parse(scanner_linha.next());
                int codigo = scanner_linha.nextInt();
                long cpf = scanner_linha.nextLong();


                // System.out.println(data  + " " + horario + " " + codigo + " " + cpf);

                lista_consultas.add(new Consulta(data, horario, codigo, cpf));

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return lista_consultas;

    }
    static ArrayList<Medico> medicos;
    static ArrayList<Paciente> pacientes;
    static ArrayList<Consulta> consultas;
    public static void leitura_geral(){
        medicos = leitura_medico();
        pacientes = leitura_pacientes();
        consultas = leitura_consulta();

        // System.out.println("===================================");
        for (Consulta consulta : consultas){
            Medico m = buscar_medico(consulta.getCodigo());
            Paciente p = buscar_paciente(consulta.getCpf());

            if (!(m.getPacientes().contains(p))){
                m.adicionaPaciente(p);
            }
            if (!(m.getConsultas().contains(consulta))){
                m.adicionaConsulta(consulta);
            }
            if (!(p.getConsultas().contains(consulta))){
                p.adicionaConsulta(consulta);
            }

            // System.out.println(m.toString());
            // System.out.println(p.toString());
        }

    }

    private static boolean existe_medico(int codigo) {
        Medico m;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < medicos.size()) {
            m = medicos.get(i);
            if (m.isMedico(codigo)) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return encontrado;
    }

    // encontrar médico com base no código
    private static Medico buscar_medico(int codigo){
        Medico m = null;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < medicos.size()){
            m = medicos.get(i);
            if (m.isMedico(codigo)){
                encontrado = true;
            }
            else{
                i++;
            }
        }
        return m;
    }

    private static boolean existe_paciente(long cpf){
        Paciente p;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < pacientes.size()){
            p = pacientes.get(i);
            if (p.isPaciente(cpf)){
                encontrado = true;
            }
            else{
                i++;
            }
        }
        return encontrado;
    }

    private static Paciente buscar_paciente(long cpf){
        Paciente p = null;
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < pacientes.size()){
            p = pacientes.get(i);
            if (p.isPaciente(cpf)){
                encontrado = true;
            }
            else{
                i++;
            }

        }
        return p;
    }

    public static void main(String[] args) throws IOException {
        leitura_geral();
        Scanner teclado = new Scanner(System.in);
        int codigo = 0;
        int codigo_medico;
        long cpf_paciente;
        int intervalo_de_tempo;
        int opcao_impressao;
        String nome_arquivo_usuario = "";
        Medico m;
        Paciente p;

        LocalDate hoje = LocalDate.now();
        LocalTime hora = LocalTime.now();
        LocalDate data_inicial;
        LocalDate data_final;

        System.out.print("Bem-vindo!\n" +
                "Menu de Operações:\n" +
                "1 - Consultar todos os pacientes de um determinado médico\n" +
                "2 - Consultas agendadas de um determinado médico em um período\n" +
                "3 - Consulta de todos os médicos de um determinado paciente\n" +
                "4 - Consultar todas as consultas de um paciente com um determinado médico\n" +
                "5 - Consultar todas as consultas agendadas de um determinado paciente\n" +
                "6 - Consultar todos os pacientes de um médico que não o consultam há mais do que um período em meses\n");

        do{
            System.out.print("\nInsira o código da operação desejada: ");
            codigo = teclado.nextInt();
        } while(codigo < 1 || codigo > 6);

        do{System.out.print("Considerando as opções abaixo:  \n" +
                "0 - imprime no terminal\n" +
                "1 - imprime em arquivo .txt\n" +
                "Digite como gostaria de imprimir a sua resposta: ");

            opcao_impressao = teclado.nextInt();
        }while(opcao_impressao != 0 && opcao_impressao != 1);

        if(opcao_impressao == 1){
            System.out.print("Insira o nome do arquivo desejado: ");
            nome_arquivo_usuario = teclado.next() + ".txt";
            FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
        }

        switch (codigo){
            case 1:
                System.out.print("Digite o código do médico: ");
                codigo_medico = teclado.nextInt();
                // imprimir todos os pacientes de um médico
                if (existe_medico(codigo_medico)){
                    m = buscar_medico(codigo_medico);

                    if (opcao_impressao == 0){
                        for(Paciente paci : m.getPacientes()){
                            System.out.println(paci.toString());
                        }
                    }
                   else if (opcao_impressao == 1){
                        FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
                        for(Paciente paci : m.getPacientes()){
                            arquivo_usuario.append(paci.toString()+"\n");
                        }
                        arquivo_usuario.close();
                    }

                }
                else {
                    System.out.println("Médico nao encontrado");
                }
                break;
            case 2:
                System.out.print("Digite o código do médico: ");
                codigo_medico = teclado.nextInt();
                // imprimir todas as consultas de um determinado medico em ordem crescente com base nos horarios
                if (existe_medico(codigo_medico)) {
                    m = buscar_medico(codigo_medico);

                    System.out.print("Digite a data inicial da pesquisa: ");
                    data_inicial = LocalDate.parse(teclado.next(),formatoBR);
                    System.out.print("Digite a data final da pesquisa: ");
                    data_final = LocalDate.parse(teclado.next(),formatoBR);

                    ArrayList<Consulta> consultas_ordenadas = new ArrayList<>();
                    for(Consulta c: m.getConsultas()){
                        if(c.getData().compareTo(data_inicial)>=0 && c.getData().compareTo(data_final)<=0){
                            consultas_ordenadas.add(c);

                        }
                    }

                    for(int i = 0; i < consultas_ordenadas.size(); i++){
                        for(int j = i + 1; j < consultas_ordenadas.size(); j++){
                            if(consultas_ordenadas.get(i).getDataEHorario().compareTo(consultas_ordenadas.get(j).getDataEHorario()) > 0){
                                Consulta aux = consultas_ordenadas.get(i);
                                consultas_ordenadas.set(i, consultas_ordenadas.get(j));
                                consultas_ordenadas.set(j, aux);
                            }
                        }
                    }

                    if(opcao_impressao == 0){
                        for(Consulta co : consultas_ordenadas){
                            System.out.println(co.toString());
                        }
                    }
                    else if(opcao_impressao == 1){
                        FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
                        for(Consulta co : consultas_ordenadas){
                            arquivo_usuario.append(co.toString()+"\n");
                        }
                        arquivo_usuario.close();
                    }


                }
                else {
                    System.out.println("Médico nao encontrado!");
                }
                break;
            case 3:
                System.out.print("Digite o CPF do paciente: ");
                cpf_paciente = teclado.nextLong();
                // imprimir todos os medicos de um determinado paciente
                if (existe_paciente(cpf_paciente)){
                    p = buscar_paciente(cpf_paciente);

                    ArrayList<String> medicos_do_paciente = new ArrayList<>();
                    if(opcao_impressao == 0){
                        for(Consulta c : p.getConsultas()) {
                            for(Medico med : medicos) {
                                if (med.getCodigo() == c.getCodigo()) {
                                    if (!medicos_do_paciente.contains(med.getNome())){
                                        medicos_do_paciente.add(med.getNome());
                                        System.out.println(c.getCodigo() + " " + med.getNome());
                    }
                                }
                            }
                        }
                    }

                    else if(opcao_impressao == 1){
                        FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
                        for(Consulta c : p.getConsultas()) {
                            for(Medico med : medicos) {
                                if (med.getCodigo() == c.getCodigo()) {
                                    if (!medicos_do_paciente.contains(med.getNome())){
                                        medicos_do_paciente.add(med.getNome());
                                        arquivo_usuario.append(c.getCodigo()+" "+med.getNome()+"\n");
                                    }
                                }
                            }
                        }
                        arquivo_usuario.close();

                    }
                }
                else{
                    System.out.println("Paciente não encontrado!");
                }
                break;
            case 4:
                System.out.print("Digite o CPF do paciente: ");
                cpf_paciente = teclado.nextLong();
                if (existe_paciente(cpf_paciente)) {
                    p = buscar_paciente(cpf_paciente);
                    System.out.print("Digite o código do médico: ");
                    codigo_medico = teclado.nextInt();

                    if (existe_medico(codigo_medico)){
                        m = buscar_medico(codigo_medico);
                        // imprimir as consultas de um determinado paciente com um determinado medico

                        if(opcao_impressao == 0) {
                            for(Consulta c : p.getConsultas()){
                                if(codigo_medico == c.getCodigo()){
                                    if((c.getData().compareTo(hoje) < 0) ){
                                        System.out.println(c.getData() + " " + c.getHorario());
                                    } else if((c.getData().compareTo(hoje) == 0) && (c.getHorario().compareTo(hora) < 0)){
                                        System.out.println(c.getData() + " " + c.getHorario());
                                    }
                                }
                            }
                        } else if (opcao_impressao == 1) {
                            FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);

                            for(Consulta c : p.getConsultas()){
                                if(codigo_medico == c.getCodigo()){
                                    if((c.getData().compareTo(hoje) < 0) ){
                                        arquivo_usuario.append(c.getData() + " " + c.getHorario()+"\n");
                                    } else if((c.getData().compareTo(hoje) == 0) && (c.getHorario().compareTo(hora) < 0)){
                                        arquivo_usuario.append(c.getData() + " " + c.getHorario()+"\n");
                                    }
                                }
                            }
                            arquivo_usuario.close();
                        }

                    }
                    else{
                        System.out.println("Medico nao encontrado!");
                    }
                }
                else{
                    System.out.println("Paciente nao encontrado!");
                }
                break;
            case 5:
                System.out.print("Digite o CPF do paciente: ");
                cpf_paciente = teclado.nextLong();
                if (existe_paciente(cpf_paciente)) {
                    p = buscar_paciente(cpf_paciente);

                    // imprimir as consultas futuras de um determinado paciente
                    if (opcao_impressao == 0){
                        for(Consulta c : p.getConsultas()){
                            if ((c.getData().compareTo(hoje) > 0)){
                                System.out.println(c.getData() + " " + c.getHorario());
                            } else if((c.getData().compareTo(hoje) == 0) && (c.getHorario().compareTo(hora) > 0)){
                                System.out.println(c.getData() + " " + c.getHorario());
                            }
                        }
                    } else if(opcao_impressao == 1){
                        FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
                        for(Consulta c : p.getConsultas()){
                            if ((c.getData().compareTo(hoje) > 0)){
                                arquivo_usuario.append(c.getData() + " " + c.getHorario()+"\n");
                            } else if((c.getData().compareTo(hoje) == 0) && (c.getHorario().compareTo(hora) > 0)){
                                arquivo_usuario.append(c.getData() + " " + c.getHorario()+"\n");
                            }
                        }
                        arquivo_usuario.close();
                    }

                }
                else{
                    System.out.println("Paciente nao encontrado!");
                }
                break;
            case 6:
                System.out.print("Digite o código do médico: ");
                codigo_medico = teclado.nextInt();
                if (existe_medico(codigo_medico)) {
                    m = buscar_medico(codigo_medico);

                    System.out.print("Digite o intervalo de tempo em meses: ");
                    intervalo_de_tempo = teclado.nextInt();

                    // buscar todos os pacientes de um determinado médico que não o consultam desde um determinado período
                    if(opcao_impressao == 0){
                        for(Paciente pac : m.getPacientes()){
                            LocalDate consulta_mais_recente_paciente_atual = LocalDate.of(0, 1, 1);
                            for(Consulta c : m.getConsultas()){
                                if(c.getCpf() == pac.getCpf()){
                                    if (c.getData().compareTo(consulta_mais_recente_paciente_atual) > 0){
                                        if (c.getData().compareTo(hoje) < 0 ){
                                            consulta_mais_recente_paciente_atual = c.getData();
                                        }
                                    }
                                }
                            }

                            if ((consulta_mais_recente_paciente_atual.compareTo(hoje.minusMonths(intervalo_de_tempo)) < 0) && (consulta_mais_recente_paciente_atual.getYear() != 0000)){
                                System.out.println("Paciente " + pac.getNome() + " " + pac.getCpf());
                            }
                        }
                    } else if(opcao_impressao == 1){
                        FileWriter arquivo_usuario = new FileWriter(nome_arquivo_usuario);
                        for(Paciente pac : m.getPacientes()){
                            LocalDate consulta_mais_recente_paciente_atual = LocalDate.of(0, 1, 1);
                            for(Consulta c : m.getConsultas()){
                                if(c.getCpf() == pac.getCpf()){
                                    if (c.getData().compareTo(consulta_mais_recente_paciente_atual) > 0){
                                        if (c.getData().compareTo(hoje) < 0 ){
                                            consulta_mais_recente_paciente_atual = c.getData();
                                        }
                                    }
                                }
                            }

                            if ((consulta_mais_recente_paciente_atual.compareTo(hoje.minusMonths(intervalo_de_tempo)) < 0) && (consulta_mais_recente_paciente_atual.getYear() != 0000)){
                                arquivo_usuario.append("Paciente " + pac.getNome() + " " + pac.getCpf()+"\n");
                            }
                        }
                        arquivo_usuario.close();
                    }

                }
                else{
                    System.out.println("Medico nao encontrado!");
                }

                break;
        }
    }
}
