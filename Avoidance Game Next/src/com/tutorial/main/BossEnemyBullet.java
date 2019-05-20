package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class BossEnemyBullet extends GameObject{

    private Handler handler;
    Random random = new Random();


    public BossEnemyBullet(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = (random.nextInt(5 - -5)+ -5);
        velY = 5;
    }

    @Override
    public void tick() {
        x += velX;
        y+= velY;


        if ( y>= Game.HEIGHT) handler.removeObject(this);

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, this.handler, Color.orange, 16,16, 0.015f));

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 16,16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16,16);
    }
}
