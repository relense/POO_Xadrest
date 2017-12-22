/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pecas;

import Jogadas.Jogada;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 *Classe que representa a peça Torre num tabuleiro de xadrez
 * @author tiago
 */
public class Torre extends Peca {
/**
 * Metodo construtor do objeto Torre
 * @param cor - cor da Torre
 */
    public Torre(Cor cor) {
        super(cor);
         Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("TorreBranca.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("TorrePreto.png"));
        }
       alterarImagem(figura);
       
    }

    /**
     * Metodo utilizado pra atualizar a imagem quando algum jogo é carregado
     */
    @Override
    public void actualizarImagemAoCarregar() {
        Image figura;
        if (super.getCor() == Cor.BRANCO) {

            figura = new Image(getClass().getResourceAsStream("TorreBranca.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("TorrePreto.png"));
        }
        alterarImagem(figura);

    }
/**
 * Metodo que devolve uma string com a informaçao da instancia Torre
 * @return (T) caso a cor seja branca, (t) caso contrario
 */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "T";

        } else {
            return "t";
        }
    }
/**
 * Metodo que devolve as jogadas que a torre pode fazer no tabuleiro que recebe e 
 * na posiçao que ele está, sendo essa posiçao tambem recebida
 * @param tabuleiro tabuleiro onde se encontra a torre
 * @param x coordenada vertical
 * @param y coordenada horizontal
 * @return array do tipo Jogadas com as jogadas que  torre pode fazer
 */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {
        int a = 0;
        Jogada[] movimentos = new Jogada[8];//array das jogadas possiveis da torre
        /**
         *  a torre pode mover uma ou duas casas na horizontal ou na vetical,
         * assim terá uma vinhinaça de duas casas por cada eixo para a sua jogada
         */
        
        //vizinhança de duas casas na vertical
        for (int i = x - 2; i < x + 3; i++) {
            if (tabuleiro.isJogadaValida(super.getCor(), i, y)) {//jogada valida?
                movimentos[a] = new Jogada(x, y, i, y);//se sim adiciona a jogada
                a++;
            }
        }
        //vizinhança de duas casas na horizontal
        for (int j = y - 2; j < y + 3; j++) {
            if (tabuleiro.isJogadaValida(super.getCor(), x, j)) {//jogada valida?
                movimentos[a] = new Jogada(x, y, x, j);//se sim adiciona a jogada
                a++;
            }
        }
        return movimentos;
    }
}
