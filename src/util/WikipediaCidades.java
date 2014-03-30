/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Vinícius
 */
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
