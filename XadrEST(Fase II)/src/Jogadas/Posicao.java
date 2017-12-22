/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogadas;

import Tabuleiro.Tabuleiro;

import java.io.Serializable;

/**
 * Classe que representa a posiçao numa peça num array que pretende ilustrar um
 * tabuleiro 8x8
 *
 * @author tiago
 */
public class Posicao implements Serializable {

    private final int X;
    private final int Y;

    /**
     * Metodo construtor
     *
     * @param X primeira coordenada no array
     * @param Y segunda coordenada no array
     */
    public Posicao(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Metodo que devolve o valor de x
     *
     * @return x
     */
    public int getX() {
        return X;
    }

    /**
     * Metodo que devolve o valor de y
     *
     * @return y
     */
    public int getY() {
        return Y;
    }

    /**
     * Metodo que devolve uma string com a posiçao em formato tabuleiro
     *
     * @return string em formato tabuleiro
     */
    @Override
    public String toString() {
        return Tabuleiro.converterCoordenadas(8 - X, Y);
    }

}
