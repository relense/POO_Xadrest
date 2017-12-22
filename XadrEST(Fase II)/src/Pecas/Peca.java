/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pecas;

import Jogadas.Jogada;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

/**
 *Classe abstracta que representa a Peca do jogo
 * @author tiago
 */
public abstract class Peca extends ImageView implements Serializable {
/**
 * cor da peca (PRETA) ou (BRANCA)
 */
    private Cor cor;

/**
 * Metodo construtor do objeto Peca
 * @param cor -cor do objeto (BRANCA) ou (PRETA)
 */
    public Peca(Cor cor) {
       
        this.cor = cor;
      
    }
/**
 * Metodo que devolve a cor da peça
 * @return cor
 */

    /**
     * Metodo utilizado pra atualizar a imagem quando algum jogo é carregado
     */
    public abstract void  actualizarImagemAoCarregar();

    /**
     * Metodo que altera a imagem da peça
     * @param image imagem a por
     */
    protected void alterarImagem(Image image){
      
                
             this.setImage(image);
    }
    public Cor getCor() {
        return cor;
    }
/**
 * Metodo que obriga a que todas as classes filhas tenham de obter as jogadas 
 * conforme a sua posicao e o seu tabuleiro
 * @param tabuleiro tabuleiro onde a peça se encontra
 * @param x primeira coordenada no array/tabuleiro
 * @param y segunda coordenada no array/tabuleiro
 * @return um array do tipo Jogada com as jogadas possiveis
 */
    public abstract Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y);
/**
 * Metodo anstracto que obriga a todas as classes filhas a terem um toString redefinido
 * @return String da peça
 */
    public abstract String toString();

}
