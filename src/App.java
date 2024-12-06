import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class App {
    private TreeSet<String> palavras;

    public App() {
        palavras = new TreeSet<>();
    }

    public void executar() {
        carregarPalavras();
        String palavra = escolherPalavra();
        jogar(palavra);
    }

    private void carregarPalavras() {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("br-sem-acentos.txt"))) {

            br.readLine();
            while ((line = br.readLine()) != null) {

                if (line.trim().length() == 5) {
                    if (!line.matches("^[A-Z].*")) {
                        palavras.add(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escolherPalavra() {
        Random r = new Random();
        int index = r.nextInt(palavras.size());

        String palavraEscolhida = palavras.stream()
                .skip(index)
                .findFirst()
                .orElse(null);

        return palavraEscolhida;
    }

    private String mudarLetrasCorretas(String palavra, String tentativa) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < tentativa.length(); i++) {
            char letraTentativa = tentativa.charAt(i);
            if (palavra.charAt(i) == letraTentativa) {
                resultado.append(Character.toUpperCase(letraTentativa));
            } else if (palavra.toUpperCase().contains(String.valueOf(letraTentativa).toUpperCase())) {

                resultado.append(letraTentativa);
            } else {
                resultado.append("-");
            }
        }

        return resultado.toString();
    }

    private void jogar(String palavra) {
        if (palavras.isEmpty()) {
            return;
        }

        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("| REGRAS: PARA JOGAR VOCE DEVE ESCREVER UMA PALAVRA DE 5 letras!");
        System.out.println("| VOCE TEM 6 TENTATIVAS PARA ACERTAR!");
        System.out.println("| QUANDO A LETRA ESTIVER CONTIDA NA PALAVRA SORTEADA, ELA APARECE EM MINUSCULO");
        System.out.println("| SE A LETRA ESTIVER NA POSICAO CERTA APARECE EM MAIUSCULO");
        System.out.println("-----------------------------------------------------------------------------------");

        int rodada = 1;
        Scanner scanner = new Scanner(System.in);
        while (rodada != 7) {
            System.out.println("tentativa " + rodada);
            String tentativa1 = scanner.nextLine();
            if (tentativa1.trim().length() != 5) {
                System.out.println("Sua palavra deve ter 5 letras");
                continue;
            }
            if (palavra.equalsIgnoreCase(tentativa1)) {
                System.out.println("Voce Acertou na tentativa: " + rodada);
                break;
            }

            if (palavras.contains(tentativa1)) {
                System.out.println(mudarLetrasCorretas(palavra, tentativa1));
                rodada++;
            } else {
                System.out.println("Essa palavra nao esta na lista de palavras!");
            }
        }
        if (rodada == 7) {
            System.out.println("Voce perdeu!!");
            System.out.println("A palavra era: " + palavra);
        }
    }
}

