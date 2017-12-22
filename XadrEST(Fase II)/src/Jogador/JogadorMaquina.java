/**
 * Jogador é um pacote que guarda todos os tipos de jogadores posiveis em
 * xadrez.
 */
package Jogador;

import Jogadas.Jogada;
import Jogo.*;
import java.util.Random;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;

/**
 * Classe que representa o bot
 *
 * @author tiago
 */
public class JogadorMaquina extends Jogador {

    /**
     * Metodo construtor
     *
     * @param nome do jogador
     * @param cor do jogador
     * @param tabuleiro onde o jogador vai jogar
     */
    public JogadorMaquina(String nome, Cor cor, Tabuleiro tabuleiro) {
        super(nome, cor, tabuleiro);
    }

    /**
     * Metodo que escolhe uma jogada automaticamente podendo ser ou nao
     * inteligente, tendo a jogada inteligente prioriedade, considerando que uma
     * jogada inteligente é uma jogada que come uma peça ao adversário
     *
     ** @param jogo jogo onde vai jogar
     * @param jogoView jogo grafico onde vai jogar
     * @return (true) se a jogada comeu alguma peça, (false) caso contrario
     */
    @Override
    public boolean jogar(Jogo jogo, JogoView jogoView) {

        atualizarJogadas();
        Random a = new Random();

        //jogada inteligente que verifica que pode fazer alguma jogada que envolva comer uma peça
        for (Jogada jogada : jogadas) {

            if (tabuleiro.comePeca(jogada.getPosicaoFutura(), super.getCor())) {

                System.out.println(jogada);
                jogo.jogadaEfetuada();//jogada feita
                return tabuleiro.mover(jogada.getPosicaoAtual(),
                        jogada.getPosicaoFutura(), jogoView);

            }
        }

//jogada random
        int x = a.nextInt(jogadas.size());

        System.out.println(jogadas.get(x));
        jogo.jogadaEfetuada();//jogada feita
        return tabuleiro.mover(jogadas.get(x).getPosicaoAtual(), jogadas.get(x).getPosicaoFutura(), jogoView);

    }

}
