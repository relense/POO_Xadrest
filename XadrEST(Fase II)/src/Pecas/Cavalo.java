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

/**
 *Classe que representa a peça Cavalo num tabuleiro de xadrez
 * @author tiago
 */
public class Cavalo extends Peca {
/**
 * Metodo construtor do objeto Cavalo
 * @param cor - cor do Cavalo
 */
    public Cavalo(Cor cor) {
        super(cor);
         Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("CavaloBranco.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("CavaloPreto.png"));
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

            figura = new Image(getClass().getResourceAsStream("CavaloBranco.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("CavaloPreto.png"));
        }
        alterarImagem(figura);

    }
/**
 * Metodo que devolve uma string com a informaçao da instancia cavalo
 * @return (C) caso a cor seja branca, (c) caso contrario
 */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "C";//se é cor branca

        } else {
            return "c";//se é cor preta
        }
    }
/**
 * Metodo que devolve as jogadas que o cavalo pode fazer no tabuleiro que recebe e 
 * na posiçao que ele está, sendo essa posiçao tambem recebida
 * @param tabuleiro tabuleiro onde se encontra o cavao
 * @param x coordenada vertical
 * @param y coordenada horizontal
 * @return array do tipo Jogadas com as jogadas que o cavalo pode fazer
 */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {

        int a = 0;
        Jogada[] movimentos = new Jogada[8];
        for (int i = x - 2; i < x + 3; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                //o cavalo só pode mover em L assim nao pode mover na diagonal nem na vertical/horizontal
                if (y - j != x - i && 0 - (y - j) != x - i && i != x && y != j) {
                    //verifica se a jogada é valida
                    if (tabuleiro.isJogadaValida(super.getCor(), i, j)) {

                        movimentos[a] = new Jogada(x, y, i, j);//acrescenta a jogada ao array
                        a++; 
                    }
                }
            }
        }

        return movimentos;

    }

}
