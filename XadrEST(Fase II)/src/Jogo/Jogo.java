/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogo;

import java.io.Serializable;
import Jogador.*;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Classe onde ocorre o jogo
 *
 * @author tiago
 */
public class Jogo implements Serializable {

    public Tabuleiro tabuleiro = new Tabuleiro();
    private Jogador jogador1;
    private Jogador jogador2;
    private int jogadasFeitas = 0;
    private final int MAXIMO_JOGADAS = 500;
    private boolean isBrancoVez = true;
    private EstadoJogo estadoJogo = EstadoJogo.ATIVO;

    /**
     * Metodo que controla que é a jogar, verificando sempre de quem é a vez e
     * mudando sempre a variavel para isBrancoVez para nao criar conflito
     *
     * @return o jogador que é a jogar
     */
    private Jogador escolherJogadorAtual() {
        //verifica se é a vez das brancas
        if (isBrancoVez) {
            return jogador1;
        } else {//se for a vez das pretas
            return jogador2;//muda a variavel para a seguir seres as brancas a jogar

        }
    }

    /**
     * Metodo que verifica se o jogo já acabou sendo que este só acaba quando
     * acontece uma das seguintes ocasioes um dos jogadores ficarem sem peças
     * atingirem o maximo de jogadas o estado do jogo ser posto como inativo
     *
     * @return (false) caso já tenha acabado e (true) caso contrario
     */
    private boolean jogoNaoAcabou() {
        return !(jogador1.getPecas() == 0 || jogador2.getPecas() == 0//verifica se alguem ficou sem peças
                //verifica se foi atingido o maximo de jogadas ou se o jogo está inativo
                || jogadasFeitas == MAXIMO_JOGADAS || this.estadoJogo == EstadoJogo.INATIVO);
    }

    /**
     * Metodo onde o jogo decorre
     *
     * @param jogoView interface grafica onde o jogo está a ser mostrado
     */
    public void jogar(JogoView jogoView) {
        if (jogoNaoAcabou()) {
            //verifica se ambos os jogadores sao maquina
            if (jogador1 instanceof JogadorMaquina && jogador2 instanceof JogadorMaquina) {
                System.out.printf("test");
                jogoMaquinaVsMaquina(jogoView);//se sim inicia o jogo por uma nova tread

            } else {
                jogoNormal(jogoView);//se nao inicia um metodo que ira inserir setOnAction nos buttoes certos
            }
            if (!jogoNaoAcabou()) {
                jogoView.acabouJogo();//vetifica se o jogo já acabou e se sim abre janela de notificaçao de que o mesmo acabou
            }
        } else {
            jogoView.acabouJogo();
        }
    }

    /**
     * Metodo onde decorre o jogo quando nao é maquina vs maquina
     *
     * @param jogoView interface grafica onde o jogo está a ser mostrado
     */
    private void jogoNormal(JogoView jogoView) {
        boolean repetir = false;//para caso seja a maquina a jogar teremos de repetir o ciclo para depois ser o humano
        do {
            jogoView.atualizar();//atualiza as informaçoes na interface grafica
            repetir = false;
            Jogador jog = escolherJogadorAtual(); //vai buscar o jogador que irá jogar

            if (jog.jogar(this, jogoView)) {
                pecaComida(jog);//verifica se a peça foi comida pela jogada efetuaada
            }
            this.jogadasFeitas++;//incrementa as jogadas
            if (jog instanceof JogadorMaquina) {
                repetir = true;//se este jogador for maquina o ciclo tem de se repetir
            }
        } while (repetir);

    }

    /**
     * Metodo que muda a vez
     */
    public void jogadaEfetuada() {
        this.isBrancoVez = !isBrancoVez;
    }

    /**
     * Metodo que retira uma peça ao jogador certo
     *
     * @param jogador jogador que executou o movimento
     */
    private void pecaComida(Jogador jogador) {
        if (jogador.equals(jogador1)) {
            jogador2.perdeuPeca();
        } else {
            jogador1.perdeuPeca();
        }

    }

    /**
     * Metodo que retorna o objeto jogo(this)
     *
     * @return this
     */
    private Jogo getJogo() {
        return this;
    }

    /**
     * Metodo onde decorre o jogo maquina vs maquina com a criaçao de uma nova
     * tread
     *
     * @param jogoView jogo grafico
     */
    private void jogoMaquinaVsMaquina(JogoView jogoView) {

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                do {

                    Platform.runLater(() -> {
//Fazer a jogada do jogador atual
                        if (estadoJogo != EstadoJogo.STAND_BY) {//se o jogo nao estiver em stand by ele joga normalmente
                            Jogador atual = escolherJogadorAtual();//obtem o jogador que é a jogar
                            if (atual.jogar(getJogo(), jogoView)) {//vetifica que o movimento da maquina comeu alguma peca
                                pecaComida(atual);//se sim uma peca foi comida

                            }
                            jogoView.atualizar();//atualiza a interface grafica
                            jogadasFeitas++;//incrementa as jogadas

                            if (jogoNaoAcabou() == false) {

                                jogoView.acabouJogo();//verifica se o jogo já acabou

                            }
                            /*verifica se algum dos jogadores foi mudado a meio do jogo para um humano 
                             pois isto implica a morte da tread e a continuaçao do jogo no outro metodo jogoNormal()
                             */
                            if (escolherJogadorAtual() instanceof JogadorHumano) {
                                jogar(jogoView);

                            }

                        }
                    }
                    );
//Parar 0.5 segundos:
                    try {
                        //espera 500 milesimas
                        Thread.sleep(500);
                    } catch (Exception ex) {
                    }
                    //o ciclo termina quando o jogo acaba ou algum jogador muda para o tipo humano
                } while (jogoNaoAcabou() && jogador2 instanceof JogadorMaquina && jogador1 instanceof JogadorMaquina);

                return null;

            }

        };
//Criar a Thread que executará a tarefa “em paralelo”:
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Metodo que retorna o tabuleiro
     *
     * @return tabuleiro onde o jogo está a decorrer
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Metodo que retorna o jogador1(brancas)
     *
     * @return jogador
     */
    public Jogador getJogador1() {
        return jogador1;
    }

    /**
     * Metodo que altera o jogador1(brancas)
     *
     * @param jogador1
     */
    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    /**
     * Metodo que retorna o jogador2(pretas)
     *
     * @return jogador
     */
    public Jogador getJogador2() {
        return jogador2;
    }

    /**
     * * Metodo que altera o jogador2(pretas)
     *
     * @param jogador2
     */
    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    /**
     * Metodo que anuncia que foi o vencedor, caso nao exista anuncia um empate
     *
     * @return String com o vencedor(ou empate)
     */
    public String vencedor() {

        if (jogador1.getPecas() == 0) {//verifica quem é o vencedor 
            return ("Venceu " + jogador2 + " - " + jogador2.getCor());
        } else {
            if (jogador2.getPecas() == 0) {
                return ("Venceu " + jogador1 + " - " + jogador1.getCor());
            } else {
                return "Jogo empatado devio a ultupassar limite de jogadas(500)";
            }
        }
    }

    /**
     * Metodo que mete o tabuleiro correspondente nos novos jogadores
     */
    public void meterTabuleiro() {
        if (jogador1 != null) {//se o jogador existir mete tabuleiro
            jogador1.setTabuleiro(this.tabuleiro);
        }

        if (jogador2 != null) {
            jogador2.setTabuleiro(this.tabuleiro);
        }
    }

    /**
     * Metodo que verifica de que é a vez de jogar e que tira uma peca a esse
     * jogador
     */
    public void jogadorPerdePeca() {
        if (isBrancoVez) {
            jogador2.perdeuPeca();
        } else {
            jogador1.perdeuPeca();
        }

    }

    /**
     * Metodo que altera o estado do jogo
     *
     * @param estadoJogo
     */
    public void setEstadoJogo(EstadoJogo estadoJogo) {
        this.estadoJogo = estadoJogo;
    }

    /**
     * Metodo que retorna a cor do jogador a jogar
     *
     * @return cor do jogador que possui a vez
     */
    public Cor vezJogar() {
        if (isBrancoVez) {
            return Cor.BRANCO;
        } else {
            return Cor.PRETO;
        }
    }
}
