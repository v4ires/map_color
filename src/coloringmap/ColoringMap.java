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

package coloringmap;

import java.io.IOException;
import screen.*;

public class ColoringMap {

    public static void main(String[] args) throws IOException {        
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
    }
}
