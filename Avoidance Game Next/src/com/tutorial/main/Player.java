package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {

    Random random = new Random();
    Handler handler = new Handler();


    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp((int)x, 0, Game.WIDTH-36);
        y = Game.clamp((int)y, 0, Game.HEIGHT-58);

        collision();
        handler.addObject(new Trail((int)x, (int)y, ID.Trail, this.handler, Color.WHITE,32,32, 0.05f));

    }

    private void collision(){
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.HEALTH -=2;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.white);
        g.fillRect((int)x, (int)y, 32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32,32);
    }


}
