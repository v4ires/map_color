/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;

/**
 *
 * @author Vinícius
 */
public class Vertice{  
    private String label;
    private String cor;
    private String id;
    private ArrayList<Vertice> listaDeAdjacencia = new ArrayList<>();

    public Vertice(String label, String id) {
        this.label = label;
        this.cor = "";
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getGrau() {
        return listaDeAdjacencia.size();
    }
    
    public ArrayList<Vertice> getListaDeAdjacencia() {
        return listaDeAdjacencia;
    }

    public void addAdjacencia(Vertice v) {
        this.listaDeAdjacencia.add(v);
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
