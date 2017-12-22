/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pecas;

import Tabuleiro.Tabuleiro;
import Jogadas.Jogada;
import Tabuleiro.Cor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *Classe que representa a peça Peao num tabuleiro de xadrez
 * @author tiago
 */
public class Peao extends Peca {
/**
 * Metodo construtor do objeto Peao
 * @param cor - cor do Peao
 */
    public Peao(Cor cor) {
        super(cor);
         Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("PeaoBranco.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("PeaoPreto.png"));
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

            figura = new Image(getClass().getResourceAsStream("PeaoBranco.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("PeaoPreto.png"));
        }
        alterarImagem(figura);

    }

    /**
 * Metodo que devolve uma string com a informaçao da instancia peao
 * @return (P) caso a cor seja branca, (p) caso contrario
 */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "P";

        } else {
            return "p";
        }
        
    }
/**
 * Metodo que devolve as jogadas que o peao pode fazer no tabuleiro que recebe e 
 * na posiçao que ele está, sendo essa posiçao tambem recebida
 * @param tabuleiro tabuleiro onde se encontra o peao
 * @param x coordenada vertical
 * @param y coordenada horizontal
 * @return array do tipo Jogadas com as jogadas que o peao pode fazer
 */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {
        int a = 0;
        Jogada[] movimentos = new Jogada[4];
        //o peao mover-se no eixo x
        for (int i = x - 1; i < x + 2; i++) {
            if (tabuleiro.isJogadaValida(super.getCor(), i, y)) {//jogada valida?
                movimentos[a] = new Jogada(x, y, i, y);//adiciona a jogada
                a++;
            }
        }
        //o peao mover no eixo y
        for (int j = y - 1; j < y + 2; j++) {
            if (tabuleiro.isJogadaValida(super.getCor(), x, j)) {//jogada valida?
                movimentos[a] = new Jogada(x, y, x, j); //adiciona a jogada
                a++;
            }
        }
        return movimentos;
    }
}
