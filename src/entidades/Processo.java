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
        long t = (f - i) - g.getTime();
        if (g.isColorido()) {
            JOptionPane.showMessageDialog(null, "Número de cores usada foi\n" + g.getNumeroCromatico() + " cores\nEm " + t + " *10^-9 segundos");
        }
        frame.habilitarBotoes();
    }
}
