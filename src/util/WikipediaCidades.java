/**********************************************************************************************
** A aplicação consiste em um simulador onde foram implementados métodos heurísticos para a 
** coloração de Mapas usando a modelagem em Grafos, foi utilizada como critério de avaliação da 
** disciplina de Algoritmo e Teoria dos Grafos.
**
** Ciência da Computação
** Universidade Federal do Tocantins - UFT/Palmas
**
** Coloring Map foi desenvolvido com o intuito de demonstrar algoritmos heurísticos para resolver 
** o problema de coloração de de Grafos. A aplicação foi desenvolvida na linguagem de 
** programação Java em conjunto com a biblioteca de visualização de Grafos Graph Stream que 
** pode ser acessada a partir desse link:
** http://graphstream-project.org/
**
** Peço encarecidamente se você for utilizar o software que mantenha o nome dos Autores, se for 
** alterá-lo seja para corrigir um bug ou adequar a sua necessidade favor mande um e-mail para, 
** viniciusaires7@gmail.com descrevendo suas alterações e em anexo o código com tais mudanças.
**
**
** Desenvolvido por:
**                   Vinicius Aires Barros
**                   Cézanne Alves
**
** Contato: Vinícius Aires Barros (viniciusaires7@gmail.com)
**          Cézanne Alves (cezannealves@gmail.com)
***********************************************************************************************/

package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikipediaCidades {

    public void GerarTxt(String link) {
        try {
            URL url = new URL(link);
            Scanner in = new Scanner(url.openStream());
            ArrayList<String> listaCidades = new ArrayList<>();
            ArrayList<String> listaLimintrofes = new ArrayList<>();
            ArrayList<URL> subLinks = new ArrayList<>();
            String site;
            System.out.println("Iniciando....");
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.contains("<td><a href=\"/wiki/Ficheiro:") || (line.contains("<td><a href=\"/wiki/") && line.contains("</a></td>"))) {
                    line = line.split("title=")[1];
                    line = line.split(">")[0];
                    line = line.replaceAll("\"", "");
                    line = line.replace(" class=mw-redirect", "");
                    site = "http://pt.wikipedia.org/wiki/" + line.replace(" ", "_");
                    line = line.replace(" (São Paulo)", "");
                    line = line.replace(" ", "");
                    subLinks.add(new URL(site));
                    listaCidades.add(line);
                }
            }
            int cont = 1;
            for (URL u : subLinks) {
                Scanner scanner = new Scanner(u.openStream());
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.contains("<th>Municípios limítrofes</th>")) {
                        line = scanner.nextLine();
                        while (!line.endsWith("</td>")) {
                            line += scanner.nextLine();
                        }
                        String[] temp = line.split("</a>");
                        String adj = "";
                        for (String args : temp) {
                            /// >=4
                            if (args.length() >= 4 && !args.contains("</td>")) {
                                for (int i = args.length() - 1; i >= 0; i--) {
                                    if (args.charAt(i) == '>') {
                                        adj += args.substring(i + 1, args.length()) + ",";
                                        break;
                                    }
                                }
                            }
                        }
                        if (adj.length() == 0) {
                            System.out.println("------------------------------------ " + (cont));
                        } else {
                            System.out.println((cont++) + " " + adj);
                        }
                        adj = Normalizer.normalize(adj, Normalizer.Form.NFD);
                        adj = adj.replaceAll("[^\\p{ASCII}]", "");
                        adj = adj.replaceAll(" ", "");
                        adj = adj.replaceAll("'", "");
                        //adj = adj.substring(0, adj.length() - 1);
                        listaLimintrofes.add(adj);
                        //System.out.println(cont++);
                    }
                }
            }
            System.out.println(listaCidades.size() + " = " + listaLimintrofes.size());
            System.out.println("Começando a escrever o arquivo.....");
            File f = new File("saida.txt");
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < listaCidades.size(); i++) {
                bw.write(listaCidades.get(i) + ";" + listaLimintrofes.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();
            System.out.println("Terminado...");
        } catch (MalformedURLException ex) {
            System.err.println("Erro ao conectar com o link: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(WikipediaCidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
