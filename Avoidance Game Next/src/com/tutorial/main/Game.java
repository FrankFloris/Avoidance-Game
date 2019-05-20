package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -1L;

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public static boolean paused = false;
    public int diff = 0;

    private Random random;
    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Menu menu;
    private Shop shop;

    public enum STATE {
        Menu,
        Select,
        Help,
        Shop,
        Game,
        GameOver;
    }

    public static STATE gameState = STATE.Menu;

    public Game(){
        handler = new Handler();
        hud = new HUD();
        shop = new Shop(handler, hud);
        spawner = new Spawner(handler, hud, this);
        random = new Random();

        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);
        this.addMouseListener(shop);

        AudioPlayer.load();

        new Window(WIDTH, HEIGHT, "Let's build a game!", this);




        if (gameState == STATE.Game){
            handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
            handler.addObject(new BasicEnemy(random.nextInt(WIDTH), random.nextInt(HEIGHT), ID.BasicEnemy, handler));
        }else{
            for (int i = 0; i < 20; i++) {
                handler.addObject(new MenuParticle(random.nextInt(WIDTH), random.nextInt(HEIGHT), ID.MenuParticle, handler));
            }
        }
    }

    public synchronized void start (){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop (){
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks; double delta = 0;
        long timer = System.currentTimeMillis(); int frames = 0;
        while(running) { long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: "+ frames); frames = 0;
            }
        }
        stop();
    }

    private void tick(){

        if(gameState == STATE.Game){
            if(!paused){
                handler.tick();
                hud.tick();
                spawner.tick();

                if(HUD.HEALTH <= 0){
                    HUD.HEALTH = 100;
                    AudioPlayer.getMusic("music").stop();
                    gameState = STATE.GameOver;
                    hud.bounds = 0;
                    shop.setB1(1000);
                    shop.setB2(1000);
                    shop.setB3(1000);
                    handler.clearEnemies();
                    for (int i = 0; i < 20; i++) {
                        handler.addObject(new MenuParticle(random.nextInt(WIDTH), random.nextInt(HEIGHT), ID.MenuParticle, handler));
                    }
                }
            }
        }else if(gameState == STATE.Menu || gameState == STATE.GameOver || gameState == STATE.Select){
            menu.tick();
            handler.tick();
        }


    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);



        if(paused){
            g.setColor(Color.white);
            g.drawString("Paused", 100,100);
        }

        if (gameState == STATE.Game){
            hud.render(g);
            try {
                handler.render(g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(gameState == STATE.Shop){
            shop.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Help  || gameState == STATE.GameOver || gameState == STATE.Select){
            menu.render(g);
            try {
                handler.render(g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max){
        if(var >= max) {return var = max;}
        else if(var <= min) {return var = min;}
        else {return var;}
    }

    public static void main(String args[]){
        new Game();
    }


}
