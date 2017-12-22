package Tabuleiro;

import Pecas.*;
import Jogadas.Jogada;
import Jogadas.Posicao;
import Jogo.*;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Classe que representa um tabuleiro de xadrez
 *
 * @author tiago
 */
public class Tabuleiro implements Serializable {

    private final String COR_PECA_SELECIONADA = "; -fx-base: #4682B4;";
    CasaDeTabuleiro[][] tabuleiro = new CasaDeTabuleiro[8][8];

    /**
     * Metodo construtor
     */
    public Tabuleiro() {
        criarTabuleiro();//cria o tabuleiro
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (tabuleiro[i][j] == null) {
                    CasaDeTabuleiro botao = new CasaDeTabuleiro(null);//se tiver a null cria um novo botao

                    botao.configurarCasa(i, j);//configura a cor da casa

                    tabuleiro[i][j] = botao;//introduz o botao no array tabuleiro
                } else {
                    CasaDeTabuleiro botao = tabuleiro[i][j];//aqui apenas configura a cor da casa
                    botao.configurarCasa(i, j);
                    tabuleiro[i][j] = botao;
                }
            }
        }

    }

    /**
     * Metodo que cria o tabuleiro como ele é no inicio
     */
    public void criarTabuleiro() {
        tabuleiro = new CasaDeTabuleiro[][]{
            {new CasaDeTabuleiro(new Torre(Cor.BRANCO)), new CasaDeTabuleiro(new Cavalo(Cor.BRANCO)), new CasaDeTabuleiro(new Bispo(Cor.BRANCO)),
                new CasaDeTabuleiro(new Rei(Cor.BRANCO)), new CasaDeTabuleiro(new Rainha(Cor.BRANCO)), new CasaDeTabuleiro(new Bispo(Cor.BRANCO)),
                new CasaDeTabuleiro(new Cavalo(Cor.BRANCO)), new CasaDeTabuleiro(new Torre(Cor.BRANCO))},
            {new CasaDeTabuleiro(new Peao(Cor.BRANCO)), new CasaDeTabuleiro(new Peao(Cor.BRANCO)), new CasaDeTabuleiro(new Peao(Cor.BRANCO)),
                new CasaDeTabuleiro(new Peao(Cor.BRANCO)), new CasaDeTabuleiro(new Peao(Cor.BRANCO)), new CasaDeTabuleiro(new Peao(Cor.BRANCO)),
                new CasaDeTabuleiro(new Peao(Cor.BRANCO)), new CasaDeTabuleiro(new Peao(Cor.BRANCO))},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new CasaDeTabuleiro(new Peao(Cor.PRETO)), new CasaDeTabuleiro(new Peao(Cor.PRETO)), new CasaDeTabuleiro(new Peao(Cor.PRETO)),
                new CasaDeTabuleiro(new Peao(Cor.PRETO)), new CasaDeTabuleiro(new Peao(Cor.PRETO)), new CasaDeTabuleiro(new Peao(Cor.PRETO)),
                new CasaDeTabuleiro(new Peao(Cor.PRETO)), new CasaDeTabuleiro(new Peao(Cor.PRETO))},
            {new CasaDeTabuleiro(new Torre(Cor.PRETO)), new CasaDeTabuleiro(new Cavalo(Cor.PRETO)), new CasaDeTabuleiro(new Bispo(Cor.PRETO)),
                new CasaDeTabuleiro(new Rei(Cor.PRETO)), new CasaDeTabuleiro(new Rainha(Cor.PRETO)), new CasaDeTabuleiro(new Bispo(Cor.PRETO)),
                new CasaDeTabuleiro(new Cavalo(Cor.PRETO)), new CasaDeTabuleiro(new Torre(Cor.PRETO))}};

    }

    /**
     * Metodo que move uma peça e verifica se alguma peça foi comida
     *
     * @param atual posiçao atual da peça, onde ela se encontra
     * @param futura posiçao futura, a qual iremos mover a peça
     * @return true se comeu false se nao
     */
    public boolean mover(Posicao atual, Posicao futura, JogoView jogoView) {

        Peca peca = (Peca) tabuleiro[futura.getX()][futura.getY()].getPeca();//cria uma nova peça igual á peça que está na posiçao futura do movimento

        tabuleiro[futura.getX()][futura.getY()].setPeca(tabuleiro[atual.getX()][atual.getY()].getPeca());//altera a peca da casa
        tabuleiro[atual.getX()][atual.getY()].setPeca(null);

        removerClickTodos();//retira todos os setOnAction

        //atualiza a lista de ultimas jogadas feitas 
        jogoView.jogadaFeita(new Jogada(atual.getX(), atual.getY(), futura.getX(), futura.getY()));
        return peca != null; //vetifica que estava algum objeto do tipo peça na posicao futura
        //se estava uma peça
        //se nao estava

    }

    /**
     * Metodo que verifica se a jogada recebida é valida
     *
     * @param cor cor da peça que se pretende movimentar
     * @param x primeira coordenada do array para onde se pretende mover a peca
     * @param y segunda coordenada do array para onde se pretende mover a peca
     * @return true se é uma jogada valida, false caso contrário
     */
    public boolean isJogadaValida(Cor cor, int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {//sendo um tabuleiro bidimensional só podera variar de [0][0] até [7][7] pois o seu tamanho em cada zona é 8
            return false;//retorna false pois essas posiçoes nao existem logo é uma jogada ivalida
        }

        return tabuleiro[x][y].getGraphic() == null || ((Peca) tabuleiro[x][y].getGraphic()).getCor() != cor; //verifica se na posicao que pretende mover nao esta nenhuma peça igual
        //a jogada é valida pois nao esta nenhuma peça da mesma cor
        //falsa pois já encontra-se lá uma peça da mesma cor
    }

    /**
     * Metodo que converte as coordenadas de array para coordenadas de tabuleiro
     * de xadrest
     *
     * @param x primeira coordenada no array
     * @param y segunda coordenada no array
     * @return jogada no formato pretendido ex: A1
     */
    public static String converterCoordenadas(int x, int y) {
        String coordenada = "";
        switch (y) {
            case 0:
                coordenada += "a";
                break;
            case 1:
                coordenada += "b";
                break;

            case 2:
                coordenada += "c";
                break;
            case 3:
                coordenada += "d";
                break;
            case 4:
                coordenada += "e";
                break;
            case 5:
                coordenada += "f";
                break;
            case 6:
                coordenada += "g";
                break;
            case 7:
                coordenada += "h";
                break;

            default:
                throw new AssertionError();
        }
        return coordenada += x;
    }

    /**
     * Metodo que recolhe todas as jogadas que as pecas da cor recebida podem
     * fazer
     *
     * @param cor cor das peças
     * @return array do tipo Jogadas com todas as jogadas que as peças da cor
     * recebida podem fazer no tabuleiro atual
     */
    public ArrayList<Jogada> getJogadas(Cor cor) {
        ArrayList<Jogada> movimentos = new ArrayList();//criacao de uma array list Jogada

        int i = 0;
        if (cor == Cor.BRANCO) {
            i = 7;//se a cor for preta o i toma valor 7 para que a vrificaçao das jogadas comece em baixo
        }
        for (;;) {
            for (int j = 0; j < 8; j++) {

                if (((Peca) tabuleiro[i][j].getGraphic()) == null) {//se tivel null na posicao nao há peça nenhuma para verificar as jogadas logo devemos passar logo para o ciclo seguinte
                    continue;
                }

                if (((Peca) tabuleiro[i][j].getGraphic()).getCor() == cor) {//verifica se a peça na posiçao tem a cor pretendida

                    Jogada[] jogadasDaPeca = ((Peca) tabuleiro[i][j].getGraphic()).getJogadas(this, i, j);//vai buscar um array jogadas com as jogadas que a peça pode fazer neste tabuleiro e na sua posiç�o

                    if (jogadasDaPeca != null) { //se o array nao for null
                        for (Jogada jogadasDaPeca1 : jogadasDaPeca) {
                            if (jogadasDaPeca1 == null) {
                                //ja nao há mais jogadas no array logo termina-se o ciclo
                                break;
                            }
                            movimentos.add(jogadasDaPeca1); //adicionar a jogada ao array list
                        }
                    }
                }
            }
            if (cor == Cor.BRANCO) {//se a cor for branca, i incrementa
                i--;
            } else {
                i++;//se for preta variavel i desincrementa
            }
            if (i == 8 || i == -1) {
                break;//se o i=8 (caso das brancas) ou i=-1(caso das pretas) já percorremos o array todo e podemos sair do ciclo
            }
        }
        return movimentos; //retorna o array list com os movimentos
    }

    /**
     * Metodo que verifica se a jogada come alguma peça
     *
     * @param futura a posiçao para onde a peça irá ser movida
     * @param cor a cor da peça de
     * @return true se come, false caso contrario
     */
    public boolean comePeca(Posicao futura, Cor cor) {
        //verifica se a posicao futura é null ou tem uma peça da mesma cor
        // se sim falso a jogada nao come nenhuma peça inimiga
        //come em caso contrario

        return !(((Peca) this.tabuleiro[futura.getX()][futura.getY()].getGraphic()) == null
                || ((Peca) this.tabuleiro[futura.getX()][futura.getY()].getGraphic()).getCor() == cor);
    }

    /**
     * Metodo que devolve uma grid com os botoes do tabuleiro na forma de um
     * tabuleiro xadrez
     *
     * @return GridPane xadrez
     */
    public GridPane obterGridPaneTabuleiro() {
        GridPane board = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.add(tabuleiro[i][j], j, i);//adiciona os butoes
            }
        }
        board.setAlignment(Pos.CENTER);//mete ao centro
        return board;
    }

    /**
     * Metodo que recebe uma cor e aplica todas as jogadas possiveis de fazer
     * atravez do tabuleiro grafico
     *
     * @param cor cor jogador
     * @param jogo jogo onde se encontra o tabuleiro
     *
     * @param jogoView parte grafica do tabuleiro
     */
    public void meterJogada(Cor cor, final Jogo jogo, JogoView jogoView) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //verificar se a peça tem a cor pretendida
                if (tabuleiro[i][j].getGraphic() != null && ((Peca) tabuleiro[i][j].getGraphic()).getCor() == cor) {

                    //se a peça corresponder á cor obtem-se todas as jogadas possiveis
                    final Jogada[] tes = ((Peca) tabuleiro[i][j].getGraphic()).getJogadas(this, i, j);
                    final int x = i;
                    final int y = j;
//evento quando se clica no botao que mostra os botoes para onde pode mover e quando carrega neles uma jogada é feita
                    tabuleiro[i][j].setOnMouseClicked(event -> {
                        this.removerClickCasasSemPecas();//retorna a cor(caso já houvesse peças clicadas estas ficam sem a cor de selecionadas)
                        tabuleiro[x][y].setStyle(COR_PECA_SELECIONADA);//aplica a cor de peca selecionada
                        for (Jogada te : tes) {//por cada jogada aplica um evento para a mesma se realizar
                            if (te == null) {
                                continue;
                            }
                            meterJogadaNoBotao(te, jogo, jogoView);//mete a jogada no borao
                        }

                    });
                }
            }
        }

    }

    /**
     * Metodo que recebe uma jogada e pelo botao correspondente cria um evento
     * que quando nele clickado ocorrerá a mesma jogada, este botao tambem deve
     * ter a cor de casa/peca selecionada
     *
     * @param jogada jogada a ser feita
     * @param jogo jogo onde está a decorrer a jogada
     * @param jogoView interface grafica onde a jogada será vista e selecionada
     */
    public void meterJogadaNoBotao(final Jogada jogada, final Jogo jogo, JogoView jogoView) {
        //mete a cor de selecionada, mas neste caso a informar que se clicar na casa é uma jogada valida e a mesma
        //se irá realizar devido ao evento que está aqui a ser adicionado
        tabuleiro[jogada.getPosicaoFutura().getX()][jogada.getPosicaoFutura().getY()].setStyle(COR_PECA_SELECIONADA);
        tabuleiro[jogada.getPosicaoFutura().getX()][jogada.getPosicaoFutura().getY()].setOnMouseClicked(event -> {
            if (mover(jogada.getPosicaoAtual(), jogada.getPosicaoFutura(), jogoView)) {
                jogo.jogadorPerdePeca();

            }
            jogo.jogadaEfetuada();
            jogo.jogar(jogoView);//quando a jogada acontece o jogo continua
        });

    }

    /**
     * Metodo que retira os eventos de todos os botoes e retoma a sua cor de
     * tabuleiro
     */
    private void removerClickTodos() {
        for (int f = 0; f < 8; f++) {
            for (int i = 0; i < 8; i++) {

                tabuleiro[f][i].setOnMouseClicked(null);//retira os eventos todos
                tabuleiro[f][i].configurarCasa(f, i);//retoma as cores correcas das casas
            }
        }
    }

    /**
     * Metodo que retira os eventos de todos os botoes que nao contem peças e
     * retoma a sua cor de tabuleiro
     */
    private void removerClickCasasSemPecas() {
        for (int f = 0; f < 8; f++) {
            for (int i = 0; i < 8; i++) {
                if (tabuleiro[f][i].getGraphic() == null) {//apenas retira os eventos das casas que nao tem peças
                    tabuleiro[f][i].setOnMouseClicked(null);
                }
                tabuleiro[f][i].configurarCasa(f, i);//retorna as cores corretas
            }
        }
    }

    /**
     * Metodo que actualiza as pecas, necessario devido a um erro ao carregar
     * jogo
     */
    public void refreshPecas() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {

                if (tabuleiro[x][y].getPeca() != null) {//se tiver peca

                    Peca peca = tabuleiro[x][y].getPeca();

                    peca.actualizarImagemAoCarregar();//atualizaa imagem da peca
                    tabuleiro[x][y] = new CasaDeTabuleiro(peca);//volta a repor

                } else //caso contrario cria um novo botao e faz a sua configuraçao
                {
                    tabuleiro[x][y] = new CasaDeTabuleiro(null);
                }

                tabuleiro[x][y].configurarCasa(x, y);

            }
        }
    }
}
