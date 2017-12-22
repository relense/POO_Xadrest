/**
 * Jogador é um pacote que guarda todos os tipos de jogadores posiveis em
 * xadrez.
 */
package Jogador;

import Jogadas.Jogada;
import Jogo.*;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe abstracta que representa um jogador
 *
 * @author tiago
 */
public abstract class Jogador implements Serializable {

    private String nome;
    private Cor cor;
    protected Tabuleiro tabuleiro;
    private int numeroPecas = 16;
    protected ArrayList<Jogada> jogadas = new ArrayList();

    /**
     * Metodo construtor
     *
     * @param nome do jogador
     * @param cor do jogador
     * @param tabuleiro onde o jogador vai estar a jogar
     */
    public Jogador(String nome, Cor cor, Tabuleiro tabuleiro) {
        this.nome = validarNome(nome);
        this.cor = cor;
        this.tabuleiro = tabuleiro;
    }

    /**
     * Metodo que valida o nome nao podendo ser vazio nem nulo
     *
     * @param nome
     * @return var (nome) se ok, "Nome indefenido" caso contrario
     */
    public String validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "Nome indefenido";
        } else {
            return nome;
        }
    }

    /**
     * Metodo que retorna o nome do jogador
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que retorna a cor que o jogador escolheu ser
     *
     * @return cor
     */
    public Cor getCor() {
        return this.cor;
    }

    /**
     * Metodo que retorna a quantidade de peças do jogador
     *
     * @return numero de pecas do jogador
     */
    public int getPecas() {
        return this.numeroPecas;
    }

    /**
     * Método que retira uma peça ao jogador
     */
    public void perdeuPeca() {
        System.out.println(nome + " perdeu uma peça");
        this.numeroPecas--;
    }

    /**
     * Metodo que actualiza as jogadas que o jogador pode fazer no tabuleiro
     * actual
     */
    public void atualizarJogadas() {
        jogadas = tabuleiro.getJogadas(cor);

    }

    /**
     * Metodo abstrato a implementar em todas as classes filhas em que consiste
     * em que faça a jogada
     *
     * @param jogo jogo onde vai jogar
     * @param jogoView jogo grafico onde vai jogar
     * @return true se a jogada comeu alguma peça, false caso contrario
     */
    public abstract boolean jogar(Jogo jogo, JogoView jogoView);

    /**
     * Metodo que converte o objeto numa string no formato ( (nome) com
     * (numeroDePecas) peças)
     *
     * @return string formatada
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append(nome).append(" com ").append(numeroPecas).append(" peças");
        return toString.toString();
    }

    /**
     * Metodo que altera o tabuleiro do jogador
     *
     * @param tabuleiro
     */
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    /**
     * Metodo que altera o numero de pecas do jogador
     *
     * @param numeroPecas
     */
    public void setNumeroPecas(int numeroPecas) {
        this.numeroPecas = numeroPecas;
    }
}
