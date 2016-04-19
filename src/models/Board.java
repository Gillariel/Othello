/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.image.Image;

/**
 * 
 * @author User
 */
public class Board {
    private long id;
    private Pawn[] pawns;
    private Image img_background;

    public Board() {
        id = 0;
        pawns = null;
        img_background = null;
    }

    public Board(long id, Pawn[] pawns, Image img_background) {
        this.id = id;
        this.pawns = pawns;
        this.img_background = img_background;
    }
}
