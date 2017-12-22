/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tabuleiro;

/**
 * Enumerado das cores que as peças podem ter
 *
 * @author tiago
 */
public enum Cor {

    PRETO, BRANCO;

    /**
     * ToString do enumerado
     *
     * @return "pretas" caso a cor seja PRETO ou "brancas" caso a cor seja
     * BRANCO
     */
    @Override
    public String toString() {
        switch (this) {
            case PRETO:
                return "pretas";
            case BRANCO:
                return "brancas";
            default:
                throw new AssertionError();
        }
    }
}
