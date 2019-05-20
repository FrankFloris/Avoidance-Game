package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class HardEnemy extends GameObject{

    private Handler handler;

    private Random random = new Random();


    public HardEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {
        x += velX;
        y+= velY;

        if(x <= 0 || x >= Game.WIDTH-16) {
            if(velX <= 0) velX =-(random.nextInt(9) +1) * -1;
            else velX = (random.nextInt(9)+1)*-1;
        }
        if(y <= 0 || y >= Game.HEIGHT-32) {
            if(velY <= 0) velY =-(random.nextInt(9) +1) * -1;
            else velY = (random.nextInt(9)+1)*-1;
        }

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, this.handler, Color.MAGENTA, 16,16, 0.02f));

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect((int)x, (int)y, 16,16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16,16);
    }
}
