/**
 * Jogador é um pacote que guarda todos os tipos de jogadores posiveis em
 * xadrez.
 */
package Jogador;

import Jogo.*;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;

/**
 * Classe que representa o jogador humano e o que ele pode fazer em xadrez
 *
 * @author tiago
 */
public class JogadorHumano extends Jogador {

    /**
     * Metodo construtor
     *
     * @param nome -nome do jogador
     * @param cor - cor da peças do jogador
     * @param tabuleiro -tabuleiro onde o jogador vai jogar
     */
    public JogadorHumano(String nome, Cor cor, Tabuleiro tabuleiro) {

        super(nome, cor, tabuleiro);
    }

    /**
     * Metodo onde o jogador vai fazer a jogada
     *
     * * @param jogo jogo onde vai jogar
     * @param jogoView jogo grafico onde vai jogar
     * @return true se a jogada comeu alguma peça, false caso contrario
     */
    @Override
    public boolean jogar(Jogo jogo, JogoView jogoView) {

        tabuleiro.meterJogada(this.getCor(), jogo, jogoView);
        return false;
    }

}
