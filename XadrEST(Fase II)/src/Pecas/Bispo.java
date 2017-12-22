/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pecas;

import Jogadas.Jogada;
import Tabuleiro.Cor;
import Tabuleiro.Tabuleiro;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *Classe que representa a peça Bispo num tabuleiro de xadrez
 * @author tiago
 */
public class Bispo extends Peca {
/**
 * Metodo construtor do objeto Bispo
 * @param cor - cor do Bispo
 */
    public Bispo(Cor cor) {
        super(cor);
         Image figura;
        if (cor == cor.BRANCO) {
          
            figura = new Image(getClass().getResourceAsStream("BispoBranco.png"));
       
        } else {
            figura = new Image(getClass().getResourceAsStream("BispoPreto.png"));
        }
      alterarImagem(figura);
       
    }
    /**
     * Metodo utilizado pra atualizar a imagem quando algum jogo é carregado
     */
    @Override
    public void actualizarImagemAoCarregar() {
        Image figura;
        if (super.getCor() == Cor.BRANCO) {

            figura = new Image(getClass().getResourceAsStream("BispoBranco.png"));

        } else {
            figura = new Image(getClass().getResourceAsStream("BispoPreto.png"));
        }
        alterarImagem(figura);

    }
/**
 * Metodo que devolve as jogadas que o bispo pode fazer no tabuleiro que recebe e 
 * na posiçao que ele está, sendo essa posiçao tambem recebida
 * @param tabuleiro tabuleiro onde se encontra o bispo
 * @param x coordenada vertical
 * @param y coordenada horizontal
 * @return array do tipo Jogadas com as jogadas que o bispo pode fazer
 */
    @Override
    public Jogada[] getJogadas(Tabuleiro tabuleiro, int x, int y) {
        Jogada[] movimentos = new Jogada[8];//o bispo pode no maximo fazer 8 jogadas
        int a = 0;//variavel que vai servir para saber em que posicao do array movimentos vou colocar a proxima jogada

        for (int i = x - 2; i < x + 3; i++) {//bispo pode andar ate duas casas no eixo xx
            for (int j = y - 2; j < y + 3; j++) {//bispo pode andar ate duas casas no eixo yy

                if ((y - j == x - i || y - j == 0 - (x - i))// && (x != i && y != j) ou seja nao pode move-se para a casa atual no entanto o isJogadaValida j?? faz essa verifica??ao
                        //s?? quando anda at?? duas casas xx e yy na diagonal ou seja y-j == x-i||y-j==0-(x-i)
                        && tabuleiro.isJogadaValida(super.getCor(), i, j)) {//verificar se a jogada ?? valida
                    movimentos[a] = new Jogada(x, y, i, j);//adicionar a jogada ao nosso array das jogadas que o bispo pode fazer neste tabuleiro actual
                    a++;//a posi??ao do array j?? est?? ocupada, entao soma-se mais um para a proxima jogada ser inserida na posicao seguinte do array

                }

            }
        }

        return movimentos;//retorno da tabela movimentos com os movimentos no bispo no tabuleiro e posicao que recebe
    }
/**
 * Metodo que devolve uma string com a informaçao da instancia bispo
 * @return (B) caso a cor seja branca, (b) caso contrario
 */
    @Override
    public String toString() {
        if (super.getCor() == Cor.BRANCO) {
            return "B";//se a cor for branca 

        } else {
            return "b";//se a cor for preta ou seja !Branca
        }
    }

}
