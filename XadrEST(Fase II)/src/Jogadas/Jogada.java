
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogadas;

import java.io.Serializable;

/**
 * Classe que representa uma jogada num tabuleiro 8x8
 *
 * @author tiago
 */
public class Jogada implements Serializable {

    private Posicao posicaoAtual;
    private Posicao posicaoFutura;

    /**
     * Metodo construtor
     *
     * @param x posiçao actual da peca no array na vertical
     * @param y posicao actual da peca no array na horizontal
     * @param xx posicao futura da peca no array na vertical
     * @param yy posicao futura da peca no array na horizontal
     */
    public Jogada(int x, int y, int xx, int yy) {
        posicaoAtual = new Posicao(x, y);
        posicaoFutura = new Posicao(xx, yy);
    }

    /**
     * Metodo que retorna o objeto Posicao da posicao atual da jogada
     *
     * @return uma copia da posicao atual
     */
    public Posicao getPosicaoAtual() {
        Posicao copia = new Posicao(posicaoAtual.getX(), posicaoAtual.getY());
        return copia;
    }

    /**
     * Metodo que retorna o objeto Posicao da posicao futura da jogada
     *
     * @return uma copia da posicao futura
     */
    public Posicao getPosicaoFutura() {
        Posicao copia = new Posicao(posicaoFutura.getX(), posicaoFutura.getY());
        return copia;
    }

    /**
     * Metodo que devolve uma string das posiçoes de uma instancia Jogadas
     * (exemplo A3-A2)
     *
     * @return string da jogada
     */
    @Override
    public String toString() {
        return posicaoAtual.toString() + "-" + posicaoFutura.toString();
    }

}
