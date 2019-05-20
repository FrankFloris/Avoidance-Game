package com.tutorial.main;

import java.util.Random;

public class Spawner {

    private Handler handler;
    private HUD hud;
    private Random random = new Random();
    private Game game;

    public int scoreKeep = 0;

    public Spawner(Handler handler, HUD hud, Game game){
        this.handler = handler;
        this.hud = hud;
        this.game = game;
    }

    public void tick(){
        scoreKeep++;

        if(scoreKeep >= 250){
            scoreKeep = 0;
            hud.setLevel(hud.getLevel()+1);
            if(hud.getLevel() == 3 || hud.getLevel() == 7 || hud.getLevel() == 13 || hud.getLevel() == 17) {
                handler.addObject(new FastEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
            } else if (hud.getLevel() == 4 || hud.getLevel() == 8 || hud.getLevel() == 14 || hud.getLevel() == 18){
                handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
            } else if (hud.getLevel() == 10){
                handler.clearEnemies();
                handler.addObject(new BossEnemy(Game.WIDTH/2-64, -120, ID.BossEnemy, handler, this));
            } else {
                if(game.diff == 0){
                    handler.addObject(new BasicEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
                }else if(game.diff == 1){
                    handler.addObject(new HardEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
                }
            }
            if (hud.getLevel() >= 11){
                handler.addObject(new BasicEnemy(random.nextInt(Game.WIDTH - 50), random.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
            }

        }
    }

}

//        handler.addObject(new BasicEnemy(random.nextInt(WIDTH), random.nextInt(HEIGHT), ID.BasicEnemy, handler));
//        for(int i = 0; i < 5; i++){
//            handler.addObject(new BasicEnemy(random.nextInt(WIDTH), random.nextInt(HEIGHT), ID.BasicEnemy, handler));
//        }