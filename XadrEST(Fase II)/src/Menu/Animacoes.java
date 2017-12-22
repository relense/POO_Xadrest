package Menu;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Classe que contem metodos abstratos para aplicarem anima?oes a Nodes Created
 * by tiago on 7/5/15.
 */
public class Animacoes {

    private static double SCALE_VALUE = 0.5;//valor da escala para setByX e setByY

    /**
     * Metodo que recebe um node e aplica nele uma anima?ao do tipo
     * ScaleTransition
     *
     * @param node node a aplicar a animacao
     */
    public static void scale(Node node) {
        ScaleTransition transicao = new ScaleTransition(Duration.millis(2000.0), node);
        transicao.setByX(SCALE_VALUE);
        transicao.setByY(SCALE_VALUE);
        transicao.setCycleCount(Timeline.INDEFINITE);
        transicao.setAutoReverse(true);
        transicao.play();
    }

    /**
     * Metodo que recebe um node e aplica nele uma anima?ao do tipo
     * FadeTransition
     *
     * @param node node a aplicar a animacao
     */
    public static void fade(Node node) {
        FadeTransition transicao = new FadeTransition(Duration.millis(2000.0), node);
        transicao.setFromValue(1.0);
        transicao.setToValue(0.1);
        transicao.setCycleCount(Timeline.INDEFINITE);
        transicao.setAutoReverse(true);
        transicao.play();
    }
}
