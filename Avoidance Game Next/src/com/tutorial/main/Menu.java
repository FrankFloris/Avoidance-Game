package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private Random random = new Random();
    private HUD hud;

    public Menu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.Menu){
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.gameState = Game.STATE.Select;
                AudioPlayer.getSound("sound").play();
//                createNewGame();
                return;
            }
            if(mouseOver(mx, my, 210, 250, 200, 64)){
                game.gameState = Game.STATE.Help;
                clickSound();
            }
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                clickSound();
                System.exit(1);
            }
        }

        if(game.gameState == Game.STATE.Help){
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                clickSound();
                game.gameState = Game.STATE.Menu;
            }
        }
        if(game.gameState == Game.STATE.GameOver){
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                clickSound();
                game.gameState = Game.STATE.Menu;
            }
        }
        if(game.gameState == Game.STATE.Select){
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.diff = 0;
                createNewGame();
            }
            if(mouseOver(mx, my, 210, 250, 200, 64)){
                game.diff = 1;
                createNewGame();
            }
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                clickSound();
                game.gameState = Game.STATE.Menu;
            }
        }
    }

    public static void clickSound(){
        AudioPlayer.getSound("sound").play();
    }

    public void mouseReleased(MouseEvent e){

    }

    public void createNewGame(){
        clickSound();
        game.gameState = Game.STATE.Game;
        hud.setLevel(1);
        hud.setScore(0);
        handler.addObject(new Player(game.WIDTH/2-32, game.HEIGHT/2-32, ID.Player, handler));
        handler.clearEnemies();
        if(game.diff == 0){
            handler.addObject(new BasicEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
        }else if(game.diff == 1){
            handler.addObject(new HardEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
        }
        AudioPlayer.getMusic("music").loop();
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else return false;
        }else return false;
    }

    public void tick(){

    }

    public void render(Graphics g){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        Font fnt3 = new Font("arial", 1, 20);
        if(game.gameState == Game.STATE.Menu) {
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Wave", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 277, 192);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 277, 292);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 277, 392);
        }else if(game.gameState == Game.STATE.Help){
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 240, 70);

            g.setFont(fnt3);
            g.drawString("Use WASD keys to move player and dodge enemies", 55, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 277, 392);
        }else if(game.gameState == Game.STATE.GameOver){
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("GAME OVER!!!", 150, 70);

            g.setFont(fnt3);
            g.drawString("You lost with a score of: " + hud.getScore(), 180, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try again", 240, 392);
        }else if(game.gameState == Game.STATE.Select){
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("SELECT DIFFICULTY", 75, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Normal", 255, 192);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Hard", 277, 292);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 277, 392);
        }
    }
}
