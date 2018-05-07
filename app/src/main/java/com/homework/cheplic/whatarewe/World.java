package com.homework.cheplic.whatarewe;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class World {
    private List<Sprite> sprites;
    private PlayerSprite player;
    private Random rnd = new Random();
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int score = 0;
    public static int hitcount = 0;

    public World() {
        sprites = new ArrayList<>();
        sprites.add(player = new PlayerSprite(new Vec2d(100,1000)));
        int boS = rnd.nextInt((height-900)) + 900;
        sprites.add(new PipeSprite(new Vec2d(width, boS)));
        sprites.add(new PipeSpriteU(new Vec2d(width, boS - 2600)));
        sprites.add(new CollectableSprite(new Vec2d(width + 25, boS - 200)));
    }

    public void tick(double dt) {
        grabTouchEvents();
        for(Sprite s: sprites) {
            s.tick(dt);
            System.out.println((int) sprites.get(1).getPosition().getX());
        }
        if((int) sprites.get(sprites.size()-1).getPosition().getX() % 93 == 0) {
            int boS = rnd.nextInt((height-300) - 300) + 300;
            sprites.add(new PipeSprite(new Vec2d(width, boS)));
            sprites.add(new PipeSpriteU(new Vec2d(width, boS - 2600)));
            sprites.add(new CollectableSprite(new Vec2d(width + 25, boS - 200)));
        }
        resolveCollisions();
        for(int i=0; i < sprites.size()-1; i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                if (sprites.get(j).getPosition().getX() == -100)
                    sprites.remove(j);
            }
        }
    }

    private void grabTouchEvents() {
        MotionEvent motionEvent = TouchEventQueue.getInstance().dequeue();
        while (motionEvent != null) {
            handleMotionEvent(motionEvent);
            motionEvent = TouchEventQueue.getInstance().dequeue();
        }
    }

    private void resolveCollisions() {
        ArrayList<Collision> collisions = new ArrayList<>();
        Sprite p = sprites.get(0);
        for(int i=0; i < sprites.size()-1; i++)
            for(int j=i+1; j < sprites.size(); j++) {
                Sprite s1 = sprites.get(i);
                Sprite s2 = sprites.get(j);

                if (s1.collidesWith(s2)) {
                    collisions.add(new Collision(s1, s2));
                    if(s2.getClass() == PipeSprite.class) {
                        sprites.remove(j);
                        sprites.remove(j);
                        hitcount++;
                    }
                    if(s2.getClass() == PipeSpriteU.class) {
                        sprites.remove(j);
                        sprites.remove(j-1);
                        hitcount++;
                    }
                    if(s2.getClass() == CollectableSprite.class) {
                        sprites.remove(s2);
                        score++;
                    }
                }
            }
        for(Collision c: collisions) c.resolve();
    }


    /**
     * When the user touches the screen, this message is sent.  Probably you
     * want to tell the player to do something?
     *
     * @param e the MotionEvent corresponding to the touch
     */
    private void handleMotionEvent(MotionEvent e) {
        player.onTouch();
    }

    public void draw(Canvas c) {
        Bitmap bg = BitmapRepo.getInstance().getImage(R.drawable.background);
        float x = player.getPosition().getX();
        int backgroundNumber = (int)(x / bg.getWidth());
        c.drawBitmap(bg, bg.getWidth()*(backgroundNumber), 0,  null);
        for(Sprite s: sprites)
            s.draw(c);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        c.drawText(score+"", width-150, 100, paint);
    }

}
