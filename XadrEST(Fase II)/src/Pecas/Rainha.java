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
 * Classe que representa a peça Rainha num tabuleiro de xadrez
 *
 * @author tiago
 */
public class Rainha extends Peca {

    /**
     * Metodo construtor do objeto Rainha
     *
     * @param cor - cor da rainha
     */
    public Rainha(Cor cor) {
         super(cor);
        Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("RainhaBranca.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("RainhaPreto.png"));
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

            figura = new Image(getClass().getResourceAsStream("RainhaBranca.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("RainhaPreto.png"));
        }
        alterarImagem(figura);

    }

    /**
     * Metodo que devolve uma string com a informaçao da instancia Rainha
     *
     * @return (R) caso a cor seja branca, (r) caso contrario
     */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "R";

        } else {
            return "r";
        }
    }

    /**
     * Metodo que devolve as jogadas que a rainha pode fazer no tabuleiro que
     * recebe e na posiçao que ele está, sendo essa posiçao tambem recebida
     *
     * @param tabuleiro tabuleiro onde se encontra a rainha
     * @param x coordenada vertical
     * @param y coordenada horizontal
     * @return array do tipo Jogadas com as jogadas que a rainha pode fazer
     */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {
        int a = 0;
        Jogada[] movimentos = new Jogada[24];
        /*
         Tendo em conta que após uma analise o que a rainha faz é basicamente o 
         contrario do que o cavalo faz, pois o cavalo pode andar em L e a rainha
         pode andar em tudo menos em L, isto num espaço de intervalo de 
         duas casas em cada eixo, dai os dois ciclos for, que vao permitir
         analisar uma vizinhança de até duas casas em cada eixo
         */
        for (int i = x - 2; i < x + 3; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                //como a rainha é o contrario do cavalo nesta vizinhança
                if (!(y - j != x - i && 0 - (y - j) != x - i && i != x && y != j)
                        && tabuleiro.isJogadaValida(super.getCor(), i, j)) {

                    movimentos[a] = new Jogada(x, y, i, j);//adicionar jogada
                    a++;
                }
            }
        }

        return movimentos;
    }

}
