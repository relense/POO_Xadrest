package Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by tiago on 7/5/15.
 */
public class MenuInicial {

    private static RespostaMenuInicial respostaMenuInicial;
    private static final short TAMANHO_JANELA = 500;
/**
 * Metodo que corresponde á pagina inicial onde se seleciona se quer Jogar,Carregar Jogo ou Sair
 * @return RespostaMenuInicial com uma das tres opçoes disponiveis
 */
    public RespostaMenuInicial Inicio() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        //imagem gif com o nome do jogo
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("xadrest.gif")));
//botao para jogar
        Button jogar = new Button("Jogar");
        jogar.setOnAction(event -> {
            respostaMenuInicial = RespostaMenuInicial.JOGAR;//resposta é jogar
            stage.close();
        });
//botao para carregar jogo
        Button carregarJogo = new Button("Carregar Jogo");
        carregarJogo.setOnAction(event -> {
            respostaMenuInicial = RespostaMenuInicial.CARREGAR_JOGO;//resposta é carregar jogo
            stage.close();
        });
//botao para sair
        Button sair = new Button("Sair");
        sair.setOnAction(event -> {
            respostaMenuInicial = RespostaMenuInicial.SAIR;//resposta é sair
            stage.close();
        });

        stage.setOnCloseRequest(event -> respostaMenuInicial = RespostaMenuInicial.SAIR);//on closerequeste ele sai

        
        //layout VBOX com tudo
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(image, jogar, carregarJogo, sair);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
       
        scene.getStylesheets().add("styleMenu.css");//importaçao de css com backgroud e formato dos botoes

        stage.setMaximized(true);
        stage.setScene(scene);
//musica de fundo
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("chess.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();//inicia musica

        stage.showAndWait();//mostra e aguarda acao
        mediaPlayer.stop();//termina musica, fazendo com que só apareça no menu
        return respostaMenuInicial;//devolve a resposta

    }
}
