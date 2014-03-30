/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;
import screen.TelaPrincipal;

/**
 *
 * @author Vinícius
 */
public class Processo extends Thread {

    TelaPrincipal frame;
    Grafo g;
    Graph graph;
    int opcao;

    public Processo(TelaPrincipal frame, Grafo g, Graph graph, int opcao) {
        this.frame = frame;
        this.g = g;
        this.graph = graph;
        this.opcao = opcao;
    }

    @Override
    public void run() {
        frame.desabilitarBotoes();
        long i = System.nanoTime();
        long time = 0;
        String str, label = null;
        str = frame.getTex1();

        if (str.contains("-")) {
            time = Long.parseLong(str.split("-")[1]);
            label = str.split("-")[0];
        } else {
            label = str;
        }
        g.setTime(time);
        Vertice v;
        switch (opcao) {
            case 0:
                g.greddyColoring(graph);
                break;
            case 1:
                if (str.length() == 0) {
                    g.dfsColoring(graph, g.getVerticeMaiorGrau());
                } else {
                    v = g.getVertice(label);
                    if (v != null) {
                        g.dfsColoring(graph, v);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vertice não encontrado!");
                    }
                }
                break;

            case 2:
                if (str.length() == 0) {
                    g.bfsColoring(graph, g.getVerticeMaiorGrau());
                } else {
                    v = g.getVertice(label);
                    if (v != null) {
                        g.dfsColoring(graph, v);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vertice não encontrado!");
                    }
                }
                break;
        }
        long f = System.nanoTime();
        long t = (f - i);
        if (g.isColorido()) {
            JOptionPane.showMessageDialog(null, "Número de cores usada foi\n" + g.getNumeroCromatico() + " cores\nEm " + t + " *10^-9 segundos");
        }
        frame.habilitarBotoes();
    }
}
