package com.tutorial.main;

import org.newdawn.slick.state.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean[] keyDown = new boolean[4];

    private Game game;


    public KeyInput(Handler handler, Game game){
        this.handler = handler;
        this.game = game;


        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_W) {tempObject.setVelY(-handler.spd); keyDown[0] = true;}
                if(key == KeyEvent.VK_A) {tempObject.setVelX(-handler.spd); keyDown[1] = true;}
                if(key == KeyEvent.VK_S) {tempObject.setVelY(+handler.spd); keyDown[2] = true;}
                if(key == KeyEvent.VK_D) {tempObject.setVelX(+handler.spd); keyDown[3] = true;}
            }
        }

        if(key == KeyEvent.VK_P){
            if (game.gameState == Game.STATE.Game){
                if(Game.paused) Game.paused = false;
                else {
                    Game.paused = true;
                }
            }
        }
        if(key == KeyEvent.VK_ESCAPE) System.exit(1);
        if(key == KeyEvent.VK_SPACE){
            if(Game.gameState == Game.STATE.Game) {
                Game.gameState = Game.STATE.Shop;
                AudioPlayer.getMusic("music").pause();
            }else if(Game.gameState == Game.STATE.Shop) {
                Game.gameState = Game.STATE.Game;
                AudioPlayer.getMusic("music").resume();
            }
        }

    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_W) keyDown[0] = false;//tempObject.setVelY(0);
                if(key == KeyEvent.VK_A) keyDown[1] = false;//tempObject.setVelX(0);
                if(key == KeyEvent.VK_S) keyDown[2] = false;//tempObject.setVelY(0);
                if(key == KeyEvent.VK_D) keyDown[3] = false;//tempObject.setVelX(0);

                if(!keyDown[0] && !keyDown[2]) tempObject.setVelY(0);
                if(!keyDown[1] && !keyDown[3]) tempObject.setVelX(0);
            }
        }


    }

}
