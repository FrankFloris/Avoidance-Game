package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class BossEnemy extends GameObject{

    private Handler handler;
    Random random = new Random();
    private Spawner spawner;

    private int arrivalTimer = 80;
    private int initilizingTimer = 50;
    private int battleTimer = 1000;


    public BossEnemy(int x, int y, ID id, Handler handler, Spawner spawner) {
        super(x, y, id);
        this.handler = handler;
        this.spawner = spawner;

        velX = 0;
        velY = 2;
    }

    @Override
    public void tick() {
        x += velX;
        y+= velY;

        spawner.scoreKeep--;

        if (arrivalTimer <= 0) {
            velY = 0;
            initilizingTimer--;}
        else arrivalTimer--;

        if (initilizingTimer <= 0){
            if (velX == 0) velX = 2;

            int spawn = random.nextInt(10);
            if (spawn == 0) handler.addObject(new BossEnemyBullet((int)x+48, (int)y+90, ID.BasicEnemy, handler));
            battleTimer--;
            if(battleTimer <= 0){
                handler.removeObject(this);
            }
        }

        if(x <= 0 || x >= Game.WIDTH-96) velX *= -1;


    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 96,96);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 96,96);
    }
}
