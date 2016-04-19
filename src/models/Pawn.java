/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.scene.paint.Color;

/**
 *
 * @author User
 */
public class Pawn {
    private long id;
    private int x;
    private int y;
    private Color color;

    public Pawn() {
        id = 0;
        x = 0;
        y = 0;
        color = Color.BLACK;
    }

    public Pawn(long id, int x, int y, Color color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public long getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }

    public void setId(long id) { this.id = id; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setColor(Color color) { this.color = color; }
    
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
