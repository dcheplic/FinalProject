package com.homework.cheplic.whatarewe;

import android.graphics.Canvas;
import android.view.TextureView;

class RenderLoop implements Runnable {
    private static final int FPS = 60;
    private final World world;
    private final TextureView textureView;
    private boolean lose = false;

    public RenderLoop(TextureView textureView) {
        this.textureView = textureView;
        world = new World();
    }

    @Override
    public void run() {
        while(!Thread.interrupted() && World.hitcount != 3) {
            world.tick(1.0/FPS);
            drawWorld();
            try {
                delay();
            } catch (InterruptedException ex) {

            }
        }
    }

    private void delay() throws InterruptedException {
        Thread.sleep((long)(1.0/FPS * 1000));
    }

    private void drawWorld() {
        Canvas c = textureView.lockCanvas();
        try {
            world.draw(c);
        } finally {
            textureView.unlockCanvasAndPost(c);
        }
    }
}
