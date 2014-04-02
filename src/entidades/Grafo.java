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

package entidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class Grafo {

    private final ArrayList<Vertice> listaDeVertices;
    private final ArrayList<String> listaDeCores;
    private boolean colorido;
    private Long time;

    public Grafo() {
        listaDeCores = new ArrayList<>();
        listaDeVertices = new ArrayList<>();
        colorido = false;
        listaDeCores.add("red");
        listaDeCores.add("blue");
        listaDeCores.add("yellow");
        listaDeCores.add("orange");
        listaDeCores.add("green");
        listaDeCores.add("#8B008B");
        listaDeCores.add("#8B0000");
        listaDeCores.add("#00008B");
    }

    public ArrayList<Vertice> getListaDeVertices() {
        return listaDeVertices;
    }

    public boolean isColorido() {
        return colorido;
    }

    public void setColorido(boolean colorido) {
        this.colorido = colorido;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    
    public Vertice getVertice(String label) {
        for (Vertice v : listaDeVertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    public void addVertices(Vertice v) {
        listaDeVertices.add(v);
    }

    public int getNumeroCromatico() {
        ArrayList<String> contCor = new ArrayList<>();
        if (isColorido()) {
            for (Vertice v : listaDeVertices) {
                if (contCor.indexOf(v.getCor()) == -1) {
                    contCor.add(v.getCor());
                }
            }
            return contCor.size();
        }
        return -1;
    }

    public void addAdjacencia(Vertice x, Vertice y) {
        int i = 0;
        boolean achou = false;
        while (i < listaDeVertices.size() && !achou) {
            if (listaDeVertices.get(i) == x) {
                listaDeVertices.get(i).addAdjacencia(y);
                achou = true;
            }
            i++;
        }
    }

    public void imprimirVertices() {
        for (Vertice v : listaDeVertices) {
            System.out.println(v.getLabel() + " Grau = " + v.getGrau());
        }
    }

    public void imprimirGrafo() {
        for (Vertice v : listaDeVertices) {
            System.out.println("Vertice: " + v.getLabel() + " Grau(v) = " + v.getGrau() + " Cor: " + v.getCor());
            for (Vertice w : v.getListaDeAdjacencia()) {
                System.out.println(v.getLabel() + "->" + w.getLabel());
            }
            System.out.println();
        }
    }

    public void lerArquivo(File arq) {
        ArrayList<String[]> listaDeDados = new ArrayList<>();
        String[] adj;
        try {
            Scanner s = new Scanner(new FileReader(arq));
            try {
                while (s.hasNext()) {
                    listaDeDados.add(s.nextLine().split(":"));
                }
                int id = 1;
                for (String[] str : listaDeDados) {
                    Vertice v = new Vertice(str[0], "N" + id);
                    addVertices(v);
                    id++;
                }
                int w = 0;
                while (w < listaDeDados.size()) {
                    for (int i = 0; i < listaDeVertices.size(); i++) {
                        adj = listaDeDados.get(w)[1].split(",");
                        for (int k = 0; k < adj.length; k++) {
                            for (int j = 0; j < listaDeVertices.size(); j++) {
                                if (listaDeVertices.get(j).getLabel().equals(adj[k])) {
                                    listaDeVertices.get(i).addAdjacencia(listaDeVertices.get(j));

                                }
                            }
                        }
                        w++;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Falha ao ler o arquivo: " + e.getMessage());
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao abrir o arquivo: " + ex.getMessage());
        }
    }

    public Vertice getVerticeMaiorGrau() {
        int maiorGrau = listaDeVertices.get(0).getGrau(), indice = 0;
        for (int i = 0; i < listaDeVertices.size(); i++) {
            if (maiorGrau < listaDeVertices.get(i).getGrau()) {
                maiorGrau = listaDeVertices.get(i).getGrau();
                indice = i;
            }
        }
        return listaDeVertices.get(indice);
    }

    public Graph plotarGrafo() {
        Graph graph = new MultiGraph("embedded");
        graph.addAttribute("ui.antialias");
        for (Vertice v : listaDeVertices) {
            graph.addNode(v.getId()).addAttribute("label", v.getId());
        }
        for (Vertice v : listaDeVertices) {
            for (Vertice w : v.getListaDeAdjacencia()) {
                graph.addEdge(v.getId() + "->" + w.getId(), v.getId(), w.getId());
            }
        }
        return graph;
    }

    public void colorirVertice(Vertice v) {
        ArrayList<String> coresAdj = new ArrayList<>();
        coresAdj.addAll(listaDeCores);

        for (Vertice w : v.getListaDeAdjacencia()) {
            if (w.getCor().length() != 0) {
                for (int i = 0; i < coresAdj.size(); i++) {
                    if (w.getCor().equals(coresAdj.get(i))) {
                        coresAdj.remove(w.getCor());
                    }
                }
            }
        }
        if (coresAdj.size() > 0) {
            v.setCor(coresAdj.get(0));
        }else{
            System.err.println("Poucas Cores...");
            return;
        }
    }

    public void dfsColoring(Graph graph, Vertice v) {
        if (v.getCor().length() == 0) {
            colorirVertice(v);
            pausa();
            graph.addAttribute("ui.stylesheet", "node#" + v.getId() + "{ fill-color: " + v.getCor() + "; size : 15px;}");
            for (Vertice w : v.getListaDeAdjacencia()) {
                dfsColoring(graph, w);
            }
        }
        setColorido(true);
    }

    public void bfsColoring(Graph graph, Vertice v) {
        ArrayList<Vertice> fila = new ArrayList<>();
        colorirVertice(v);
        graph.addAttribute("ui.stylesheet", "node#" + v.getId() + "{ fill-color: " + v.getCor() + "; size : 15px;}");
        fila.add(v);
        while (!fila.isEmpty()) {
            v = fila.remove(0);
            for (Vertice w : v.getListaDeAdjacencia()) {
                if (w.getCor().length() == 0) {
                    colorirVertice(w);
                    graph.addAttribute("ui.stylesheet", "node#" + w.getId() + "{ fill-color: " + w.getCor() + "; size : 15px;}");
                    pausa();
                    fila.add(w);
                }
            }
        }
        setColorido(true);
    }

    public void greddyColoring(Graph graph) {
        String resultdo = "";
        for (Vertice v : listaDeVertices) {
            colorirVertice(v);
            pausa();
            graph.addAttribute("ui.stylesheet", "node#" + v.getId() + "{ fill-color: " + v.getCor() + "; size : 15px;}");
        }
        setColorido(true);
    }

    private void pausa() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
