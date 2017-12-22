/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Jogadas.Jogada;
import Jogador.*;
import Jogo.JogoView;
import Tabuleiro.Cor;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Serializable;

import javafx.scene.image.ImageView;

/**
 *
 * @author admin
 */
public class MenuBarra implements Serializable {

    private static final double LARGURA = 500;
    private static final double ALTURA = 400;
    private final StringBuilder JOGADAS = new StringBuilder();

    private Stage janela;

    private JogoView jogo;

    private Text ultimasJogadasEfetuadas = new Text();

    /**
     * Metodo construtor que recebe o jogo(interfae grafica) a que o menu está
     * associado
     *
     * @param jogo jogo associado ao menu
     */
    public MenuBarra(JogoView jogo) {
        this.jogo = jogo;
    }

    /**
     * Metodo que constrói a barra do menu
     *
     * @return barra construida
     */
    public MenuBar PainelComMenu() {

        MenuBar menuBar = new MenuBar();

        Menu menuConfigurar = new Menu("Menu");//separador menu
        MenuItem menuNovoJogo = new MenuItem("Novo Jogo");//item novo jogo
        menuNovoJogo.setOnAction(e -> {//inicia um novo jogo
            jogo.novoJogo();

        });

        MenuItem menuMudarNomeBrancas = new MenuItem("Alterar jogador das Peças brancas");//item de alterar jogador pecas brancas
        menuMudarNomeBrancas.setOnAction(e -> {//altera jogador peças brancas
            jogo.meterJogoStandBy();//mete o jogo em standby

            menuMudarNome(Cor.BRANCO);
            jogo.meterJogoAtivo();//reativa

        });
        //aqui é o mesmo mas para as pretas
        MenuItem menuMudarNomePretas = new MenuItem("Alterar jogador das Peças Pretas");
        menuMudarNomePretas.setOnAction(e -> {

            jogo.meterJogoStandBy();

            menuMudarNome(Cor.PRETO);
            jogo.meterJogoAtivo();
        });
        //item de gravar 
        MenuItem menuSave = new MenuItem("Gravar Jogo");
        menuSave.setOnAction(e -> {
            jogo.meterJogoStandBy();//mete o jogo em standby
            jogo.gravarJogo();//grava
            jogo.meterJogoAtivo();//reativa
        });
        //item de carregar jogo
        MenuItem menuLoad = new MenuItem("Carregar Jogo");
        menuLoad.setOnAction(e -> {
            jogo.meterJogoStandBy();//mete o jogo em standby
            jogo.lerJogo();//le o jogo
            jogo.meterJogoAtivo();//ativa(pois a leitura pode falhar e assim continua o jogo que estava)
        });
//adiciona os items ao separador
        menuConfigurar.getItems().addAll(menuNovoJogo, menuMudarNomeBrancas, menuMudarNomePretas, menuSave, menuLoad);
//separador sair
        Menu menuSair = new Menu("Ajuda");
        MenuItem menuInstrucoes = new MenuItem("Instruções");//item das instrucoes
        menuInstrucoes.setOnAction(e -> {
            janela = new Stage();

            janela.getIcons().add(new Image(getClass().getResourceAsStream("TorrePreto.png")));
            janela.setTitle("Instruções");
            janela.setScene(new Scene(menuInstrucoes()));//mete a scene na nova janela

            janela.showAndWait();//mostra e aguarda acao

        });

        MenuItem menuFechar = new MenuItem("Fechar");//item fechar o jogo
        menuFechar.setOnAction(e -> Platform.exit());//fecha
        menuSair.getItems().addAll(menuInstrucoes, menuFechar);//adiciona os items

        menuBar.getMenus().addAll(menuConfigurar, menuSair);//constroi o menu

        return menuBar;//devolve a barra
    }

    /**
     * Metodo que mudar jogador conforme a cor
     *
     * @param cor cor do jogador
     */
    public void menuMudarNome(Cor cor) {

        janela = new Stage();//cria uma nova janela
        janela.getIcons().add(new Image(getClass().getResourceAsStream("TorrePreto.png")));//mete icon
        janela.setTitle("Alterar jogador das Peças " + cor);//mete titulo
        janela.initModality(Modality.APPLICATION_MODAL);//mete modal

        VBox box = new VBox(0.2);
        //texto a pedir o novo nome
        Text nome = new Text("Nome do Jogador das Peças " + cor);
        defaultStyleText(nome, 16);;
        //texto de informaçao
        Text info = new Text("(Fechar esta janela fará com o que o jogador fique Humano)");
        //para inserir o nome
        TextField textFieldNome = new TextField();
        textFieldNome.setMinSize(12.0, 10.0);
        //para escolher o tipo
        Text tipoJogador = new Text("Escolha o tipo de Jogador");
        //combo box com as opçoes
        ComboBox<String> listaTipoJogador = new ComboBox<>();

        ObservableList<String> tipo = FXCollections.observableArrayList();
        listaTipoJogador.setItems(tipo);
        //os tipos
        tipo.add("Jogador Humano");
        tipo.add("Jogador Maquina");

        Button continuar = new Button("Continuar");//continuar, para alterar/criar o jogador
        continuar.setOnAction(e -> {

            String nomeJogador = textFieldNome.getText();
            //verifica o tipo de jogador selecionado
            if (listaTipoJogador.getSelectionModel().getSelectedIndex() == 1) {

                jogo.putPlayer(new JogadorMaquina(nomeJogador, cor, null));

            } else {

                jogo.putPlayer(new JogadorHumano(nomeJogador, cor, null));

            }

            janela.close();

        });
        janela.setOnCloseRequest(e -> {
            jogo.putPlayer(new JogadorHumano(null, cor, null));//se sair, cria-se um jogador humano
        });
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(nome, info, textFieldNome, tipoJogador, listaTipoJogador, continuar);

        janela.setScene(new Scene(box));
        janela.showAndWait();

    }

    /**
     * Metodo que mete o texto a bold, steelblue e o tamanho que recebe
     *
     * @param text texto que se pretende modelar
     * @param tamanhoLetra tamanho do texto
     */
    private void defaultStyleText(Text text, int tamanhoLetra) {
        text.setFill(Color.STEELBLUE);
        text.setFont(Font.font(null, FontWeight.BOLD, tamanhoLetra));
    }

    /**
     * Metodo que devolve uma vBox com as instruçoes
     *
     * @return VBox com instrucoes
     */
    private VBox menuInstrucoes() {

        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        //criar o titulo
        Text titulo = new Text("Instruções XadrEST");
        defaultStyleText(titulo, 40);

        //istruçao
        Text instrucao = new Text(new StringBuilder("O XadrEST e diferente do xadrez original ")
                .append("sendo que neste o jogo acaba quando 1 dos jogadores ficar ")
                .append("sem peças ou quando o numero de jogadas for igual a 500.").toString());
        //segundo titulo
        Text titulo2 = new Text("Movimentos das Peças");
        defaultStyleText(titulo2, 20);
        //informacao de movimentos
        Text movimentos = new Text(new StringBuilder("Peão: Move-se uma casa na horizontal ou na vertical;\n")
                .append(" Torre: Move-se uma ou duas casas na horizontal ou na vertical;\n")
                .append(" Rainha: Move-se uma ou duas casas na horizontal, vertical ou diagonal.\n")
                .append(" Rei: Move-se uma casa em qualquer direção.\n")
                .append(" Bispo: Move-se uma ou duas casas na diagonal.\n")
                .append(" Cavalo: Move-se em forma de L, ou seja, duas casas num eixo e uma casa noutro eixo.").toString());

        Button percebi = new Button("Percebi");//botao que fecha a janela quando acionado
        percebi.setOnAction(e -> janela.close());

        box.getChildren().addAll(titulo, instrucao, titulo2, movimentos, percebi);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }

    /**
     * Metodo que retorna uma vBox com a informaçao do jogador
     *
     * @param jogador jogador a reter informacao
     * @return vBox com a informaçao
     */
    public VBox boxJogador(Jogador jogador) {
        //criar a VBox
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setSpacing(20);

        //criar textos
        Text nome = new Text(100, 50, "Peças " + jogador.getCor());
        defaultStyleText(nome, 20);

        Text nome2 = new Text(jogador.getNome());

        Text pecas = new Text(100, 50, "Total Peças");
        defaultStyleText(pecas, 20);
        //numero de pecas
        Text numeroPecas = new Text(String.valueOf(jogador.getPecas()));

        //criar imagem(esta tera uma animaçao caso seja a vez do jogador a jogar)
        ImageView imagem = imagemJogador(jogador, box);

        box.getChildren().addAll(nome, nome2, pecas, numeroPecas, imagem);

        box.setPrefSize(150, 100);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    /**
     * Metodo que retorna a imagem do jogador conforme a sua cor, e conforme a
     * sua cor a posiciona
     *
     * @param jogador jogador que vai-se atribuir imagem
     * @param box box onde a imagem vai estar contida
     * @return imagem, torre preta caso a cor seja preta, torre branca caso
     * contrario
     */
    private ImageView imagemJogador(Jogador jogador, VBox box) {
        ImageView imagem;
        //verificar a cor
        if (jogador.getCor() == Cor.BRANCO) {
            //atribuir imagem
            imagem = new ImageView(new Image(getClass().getResourceAsStream("TorreBranca.png")));
            //atibuir posicao
            box.setAlignment(Pos.CENTER_LEFT);
        } else {
            //o mesmo que faria em cima comentado
            imagem = new ImageView(new Image(getClass().getResourceAsStream("TorrePreto.png")));
            box.setAlignment(Pos.CENTER_RIGHT);
        }

        if (jogo.vezJogar() == jogador.getCor()) {
            Animacoes.scale(imagem);//mete a animaçao caso seja a vez dele a jogar
        }
        return imagem;
    }

    /**
     * VBOX que contem as ultimas 5 jogadas do jogo
     *
     * @return vBox das jogadas
     */
    public VBox boxJogadas() {

        VBox box = new VBox();
        //cria o titulo
        Text texto = new Text("Ultimas jogadas efectuadas:");
        defaultStyleText(texto, 16);

        box.setAlignment(Pos.BOTTOM_CENTER);//mete no centro
        //adiciona o texto e o parametro de texto com as 5 ultimas jogadas
        box.getChildren().addAll(texto, ultimasJogadasEfetuadas);

        return box;

    }

    /**
     * Metodo que adiciona a jogada, caso estejam mais de 5 jogadas elemina a
     * mais antiga
     *
     * @param jogada jogada que foi feita
     */
    public void adicionarJogadaFeita(Jogada jogada) {
        if (JOGADAS.length() != 0) {
            JOGADAS.append(" || ");
        }

        JOGADAS.append(jogada.toString());//adiciona a jogada ao string builder

        if (JOGADAS.length() >= 50) {//significa que tem mais de 5 jogadas
            JOGADAS.delete(0, 9);//remove a mais antiga
        }

        ultimasJogadasEfetuadas.setText(JOGADAS.toString());//mete o texto actualizado
        Animacoes.fade(ultimasJogadasEfetuadas);//mete animaçao no texto

    }

    /**
     * Metodo que refere-se ao fim do jogo, onde anuncia o vencedor e quando a
     * janela é fechada um novo jogo inicia-se Esta janela impede qualquer outra
     * acao no tabuleiro sem antes ser fechada(APPLICATION MODAL)
     */
    public void fimJogo() {

        Stage window = new Stage();
        window.setTitle("Fim jogo");
        window.initModality(Modality.APPLICATION_MODAL);//aplication modal
        window.setOnCloseRequest(event -> jogo.novoJogo());//quando a fecha um novo jogo inicia

        Text text = new Text();

        text.setText(jogo.obterVencedor());//mete o texto com o vencedor
        defaultStyleText(text, 16);

        Animacoes.scale(text);//mete animaçao

        //constroi e mostra
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(text);
        Scene scene = new Scene(stackPane, 700, 200);

        window.setScene(scene);
        window.show();

    }

}
