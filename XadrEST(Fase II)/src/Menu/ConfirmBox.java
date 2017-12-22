/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Jogo.JogoView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Serializable;

/**
 * Classe que contem metedos estaticos para a confirmacao de saida
 *
 * @author admin
 */
public class ConfirmBox implements Serializable {

    private static Button yesButton, noButton, yesAndSaveButton;
    private static boolean answer;

    /**
     * Metodo que recebe a mensagem, o titulo e o jogo onde esta a decorrer para
     * perguntar a mensagem se que sair do jogo tendo 3 possibilidades (Sair e
     * gravar, sair, cancelar)
     *
     * @param title titulo da stage
     * @param message mensagem mostrada ao utilizador
     * @param jogo jogo de onde selecionou que queria sair
     * @return (false) caso cancele, (true) caso contrário
     */
    public static boolean display(String title, String message, JogoView jogo) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        yesAndSaveButton = new Button("Gravar e sair");
        yesButton = new Button("Sair");
        noButton = new Button("Cancelar");
//botao que sai e grava
        yesAndSaveButton.setOnAction(e -> {
            jogo.gravarJogo();
            answer = true;
            window.close();
        });
//botao que apenas sai
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
//botao que nao sair
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(yesAndSaveButton, yesButton, noButton);
        layout.getChildren().addAll(label, hBox);
        layout.setAlignment(Pos.CENTER);

        Scene cena = new Scene(layout, 400, 300);
        window.setScene(cena);
        window.showAndWait();

        return answer;//retorna a resposta se sai ou nao
    }

}
