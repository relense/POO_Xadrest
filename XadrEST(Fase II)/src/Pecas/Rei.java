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
 *Classe que representa a peça Rei num tabuleiro de xadrez
 * @author tiago
 */
public class Rei extends Peca {
/**
 * Metodo construtor do objeto Rei
 * @param cor - cor do Rei
 */
    public Rei(Cor cor) {
        super(cor);
         Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("ReiBranco.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("ReiPreto.png"));
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

            figura = new Image(getClass().getResourceAsStream("ReiBranco.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("ReiPreto.png"));
        }
        alterarImagem(figura);

    }
/**
 * Metodo que devolve uma string com a informaçao da instancia Rei
 * @return (K) caso a cor seja branca, (k) caso contrario
 */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "K";//Se for branca

        } else {
            return "k";//Se for preta
        }
    }
/**
 * Metodo que devolve as jogadas que o rei pode fazer no tabuleiro que recebe e 
 * na posiçao que ele está, sendo essa posiçao tambem recebida
 * @param tabuleiro tabuleiro onde se encontra o rei
 * @param x coordenada vertical
 * @param y coordenada horizontal
 * @return array do tipo Jogadas com as jogadas que o rei pode fazer
 */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {
        int a = 0;
        Jogada[] movimentos = new Jogada[8];
        
        /*
        sabendo que o rei pode andar uma e só uma casa para todos os lados,
        os dois ciclos for seguintes permitem analisar toda essa vizinhança
        de até uma casa em cada eixo
        */
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (tabuleiro.isJogadaValida(super.getCor(), i, j)) {//jogada valida?
                    movimentos[a] = new Jogada(x, y, i, j);//se sim, adiciona a jogada
               a++;
                }
            }
        }

        return movimentos;
    }
}
