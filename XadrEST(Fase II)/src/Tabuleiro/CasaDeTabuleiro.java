package Tabuleiro;

import Pecas.Peca;
import javafx.scene.control.Button;

import java.io.Serializable;

/**
 * Classe que representa uma casa de tabuleiro
 *
 * @author tiago
 */
public class CasaDeTabuleiro extends Button implements Serializable {

    private final int TAMANHO_BOTAO = 75;//tamanho do botao
    private Peca peca;//peca correspondeste

    /**
     * Metodo construtor
     *
     * @param peca peca correspondente
     */
    public CasaDeTabuleiro(Peca peca) {
        super("", peca);//super
        if (peca != null)//
        {
            this.peca = peca;
        }
    }

    public void configurarCasa(int i, int j) {
        //conforme a sua posicao define a sua cor
        if ((i + 1) % 2 == (j + 1) % 2) {
            super.setStyle("; -fx-base: #FFFFFF;");
        } else {
            super.setStyle("; -fx-base: #ADADAD;");
        }
        super.setMaxSize(this.TAMANHO_BOTAO, this.TAMANHO_BOTAO);//define o tamanho
        super.setMinSize(this.TAMANHO_BOTAO, this.TAMANHO_BOTAO);//define o tamanho

    }

    /**
     * Metodo que atualuza a imagem
     */
    public void loadNode() {
        super.setGraphic(null);
        if (this.peca != null) {
            super.setGraphic(this.peca);
        }
    }

    /**
     * Metodo que retorna a peca
     *
     * @return a peca
     */
    public Peca getPeca() {
        return peca;
    }

    /**
     * Metodo que recebe uma peca e altera a que tem
     *
     * @param peca nova peca
     */
    public void setPeca(Peca peca) {
        this.peca = peca;
        super.setGraphic(peca);
    }
}
