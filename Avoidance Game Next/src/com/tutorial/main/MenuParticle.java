package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class MenuParticle extends GameObject{

    private Handler handler;
    Random random = new Random();

    private Color col;

    public MenuParticle(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = (random.nextInt(5 - -5) + -5);
        velY = (random.nextInt(5 - -5) + -5);
        if(velX == 0) velX = 1;
        if(velY == 0) velY = 1;


        col = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Override
    public void tick() {
        x += velX;
        y+= velY;

        if(x <= 0 || x >= Game.WIDTH-16) velX *= -1;
        if(y <= 0 || y >= Game.HEIGHT-32) velY *=-1;

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, this.handler, col, 16,16, 0.04f));

    }

    @Override
    public void render(Graphics g) {
        g.setColor(col);
        g.fillRect((int)x, (int)y, 16,16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16,16);
    }
}
