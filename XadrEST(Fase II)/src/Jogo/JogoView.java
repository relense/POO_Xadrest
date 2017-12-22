 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jogo;

import Menu.ConfirmBox;
import Menu.MenuBarra;
import Menu.MenuInicial;
import Menu.RespostaMenuInicial;
import Jogadas.Jogada;
import Jogador.*;
import Tabuleiro.Cor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import javafx.application.Platform;

/**
 * Classe que representao a interface gráfica do Jogo
 *
 * @author tiago
 */
public class JogoView extends Application {

    private final BorderPane painelTabuleiro = new BorderPane();
    private Jogo jogo = new Jogo();
    private MenuBarra menuBarra = new MenuBarra(this);

    /**
     * Metodo main onde o programa ? inicializado
     *
     * @param primaryStage janela primaria
     */
    @Override
    public void start(Stage primaryStage) {
        //parte inicial onde se mete tudo para a janela ficar ? escala
        painelTabuleiro.setTop(menuBarra.PainelComMenu());
        painelTabuleiro.setCenter(jogo.tabuleiro.obterGridPaneTabuleiro());
        painelTabuleiro.setLeft(menuBarra.boxJogador(new JogadorHumano("", Cor.BRANCO, jogo.getTabuleiro())));
        painelTabuleiro.setRight(menuBarra.boxJogador(new JogadorHumano("", Cor.PRETO, jogo.getTabuleiro())));
        painelTabuleiro.setBottom(menuBarra.boxJogadas());

        Scene scene = new Scene(painelTabuleiro);

        //parte em que faz load de um .css criado para que os botoes nao tenham bordas arredondadas
        scene.getStylesheets().add("style.css");

        //icon da janela
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("TorrePreto.png")));
        primaryStage.setTitle("XadrEST");//titulo
        primaryStage.setScene(scene);

        MenuInicial menuInicial = new MenuInicial();
        final RespostaMenuInicial respostaMenuInicial = menuInicial.Inicio();
        System.out.print(respostaMenuInicial);
        primaryStage.setOnShown(e -> {

            if (respostaMenuInicial == RespostaMenuInicial.JOGAR) {
                //quando esta janela inicia ? pedido para alterar os jogadores
                menuBarra.menuMudarNome(Cor.BRANCO);

                menuBarra.menuMudarNome(Cor.PRETO);
                //e em seguida inicia-se o jogo
                jogo.jogar(this);
            } else {
                if (respostaMenuInicial == RespostaMenuInicial.CARREGAR_JOGO) {
                    lerJogo();
                }
            }

        });
        //se tentar fechar a ConfirmBox ir? aparecer
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            this.meterJogoStandBy();
            if (ConfirmBox.display("Fechar",
                    "Tem a certeza que quer fechar XadrEST?", this)) {
                primaryStage.close();
            };
            this.meterJogoAtivo();

        });

        if (respostaMenuInicial != RespostaMenuInicial.SAIR) {
            primaryStage.show();
        }

    }

    /**
     * Metodo que inicia um novo jogo
     */
    public void novoJogo() {
        this.meterJogoInativo();//mete o jogo anterior inactivo para caso esteja maquina vs maquina a tread morrer
        this.jogo = new Jogo();//cria novo jogo

        //muda os jogadores
        menuBarra.menuMudarNome(Cor.BRANCO);
        menuBarra.menuMudarNome(Cor.PRETO);

        //mete o tabuleiro e atualiza os dados todos
        painelTabuleiro.setCenter(jogo.getTabuleiro().obterGridPaneTabuleiro());
        painelTabuleiro.setBottom(menuBarra.boxJogadas());
        atualizar();
        //inicia o jogo
        jogo.jogar(this);
    }

    /**
     * Metodo que recebe um jogador, verifica a sua cor e substitui o antigo
     * conforme a cor do recebido
     *
     * @param jogador jogador a introduzir no jogo
     */
    public void putPlayer(Jogador jogador) {

        Jogador jogador3 = null;
        //verifica que nao ? nulo
        if (jogador != null) {
            //verifica a cor
            if (jogador.getCor() == Cor.BRANCO) {
                if (jogo.getJogador1() != null) {
                    jogador3 = jogo.getJogador1();//atribui ? variavel criada o valor do jogador a substituir
                }

                putPlayer1(jogador);//substitui
            } else {

                if (jogo.getJogador2() != null) {
                    jogador3 = jogo.getJogador2();//atribui ? variavel criada o valor do jogador a substituir
                }

                putPlayer2(jogador);//substitui

            }
            //se o jogador substituido nao for nulo nem ambos os jogadores atuais forem maquina
            //temos de mandar o jogo jogar senao o mesmo cria conflito quando mudamos jogadores
            if (jogador3 != null && !(jogador3 instanceof JogadorMaquina && jogador instanceof JogadorMaquina)) {
                System.out.println("NA");

                jogo.jogar(this);
            }

        }
    }

    /**
     * Metodo que substitui o jogador1 e atualiza toda a informa?ao gr?fica do
     * mesmo
     *
     * @param jogador jogadore a meter
     */
    private void putPlayer1(Jogador jogador) {
        if (jogo.getJogador1() != null) {
            jogador.setNumeroPecas(jogo.getJogador1().getPecas());
        }

        jogo.setJogador1(jogador);
        jogo.meterTabuleiro();
        painelTabuleiro.setLeft(menuBarra.boxJogador(jogo.getJogador1()));
    }

    /**
     * Metodo que substitui o jogador2 e atualiza toda a informa?ao gr?fica do
     * mesmo
     *
     * @param jogador jogadore a meter
     */
    private void putPlayer2(Jogador jogador) {
        if (jogo.getJogador2() != null) {
            jogador.setNumeroPecas(jogo.getJogador2().getPecas());
        }

        jogo.setJogador2(jogador);
        jogo.meterTabuleiro();
        painelTabuleiro.setRight(menuBarra.boxJogador(jogo.getJogador2()));

    }

    /**
     * Metodo responsavel por notificar que o jogo acabou
     */
    public void acabouJogo() {
        menuBarra = new MenuBarra(this);
        menuBarra.fimJogo();
        painelTabuleiro.setBottom(menuBarra.boxJogadas());

    }

    /**
     * Metodo que atualiza a informa?ao gr?fica dos jogadores
     */
    public void atualizar() {
        painelTabuleiro.setLeft(menuBarra.boxJogador(jogo.getJogador1()));
        painelTabuleiro.setRight(menuBarra.boxJogador(jogo.getJogador2()));

    }

    /**
     * Metodo que obtem que foi o vencedor
     *
     * @return vencedor
     */
    public String obterVencedor() {
        return jogo.vencedor();
    }

    /**
     * metodo que devolve a cor do jogador que ? a jogar
     *
     * @return Cor jogador
     */
    public Cor vezJogar() {
        return jogo.vezJogar();
    }

    /**
     * Metodo que atualiza a informa?ao grafica das ultimas jogadas feitas
     * acrescentando uma
     *
     * @param jogada jogada feita a acrescentar
     */
    public void jogadaFeita(Jogada jogada) {
        menuBarra.adicionarJogadaFeita(jogada);
    }

    /**
     * Metodo que le o jogo
     */
    public void lerJogo() {
        boolean leu = true;
        File file = obterFicheiroLer();
        Jogo jogo1 = jogo;
        System.out.println(file);
        if (file != null) {
            try {
                ObjectInputStream oss = new ObjectInputStream(new FileInputStream(file));
                this.jogo = (Jogo) oss.readObject();
                oss.close();

            } catch (IOException e) {
                leu = false;

            } catch (ClassNotFoundException e) {
                leu = false;
            }

        }
        if (jogo.getJogador1() == null || jogo.getJogador2() == null) {
            Platform.exit();
        }
        if (leu) {
            System.out.println(jogo.getJogador1().getPecas());
            jogo.getTabuleiro().refreshPecas();

            painelTabuleiro.setCenter(jogo.getTabuleiro().obterGridPaneTabuleiro());
            atualizar();
            painelTabuleiro.setBottom(menuBarra.boxJogadas());
            if (jogo1.equals(jogo)) {
                jogo.jogar(this);
            } else {
                jogo.jogar(this);
            }
        }

    }

    /**
     * Metodo responsav?l por gravar o jogo
     */
    public void gravarJogo() {
        File file = obterFicheiroGravar();
        if (file != null) {
            try {
                ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(file));
                oss.writeObject(jogo);
                oss.flush();
                oss.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Metodo que obtem o ficheiro de onde se quer ler o jogo
     *
     * @return File de onde se pretende carregar o jogo
     */
    private File obterFicheiroLer() {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        return fileChooser.showOpenDialog(stage);

    }

    /**
     * Metodo onde se obtem o ficheiro onde se pretende guardar o jogo, sendo
     * ele .dat
     *
     * @return File onde iremos gravar
     */
    private File obterFicheiroGravar() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        stage.setTitle("Gravar jogo");
        //Set extension filter
        FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(datFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        return file;
    }

    /**
     * Metodo que mete o jogo ATIVO
     */
    public void meterJogoAtivo() {
        this.jogo.setEstadoJogo(EstadoJogo.ATIVO);
    }

    /**
     * Metodo que mete o jogo em STAND_BY
     */
    public void meterJogoStandBy() {
        this.jogo.setEstadoJogo(EstadoJogo.STAND_BY);
    }

    /**
     * Metodo que mete o jogo INATIVO
     */
    public void meterJogoInativo() {
        this.jogo.setEstadoJogo(EstadoJogo.INATIVO);
    }
}
