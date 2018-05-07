package com.homework.cheplic.whatarewe;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class PlayerSprite extends Sprite {

    public int velocity = 0;
    private int gravity = 200;
    private int lift = -1350;

    private Context c;

    public int hp = 3;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private BitmapSequence s;
    private BitmapRepo r;

    public PlayerSprite(Vec2d v) {
        super(v);
        loadBitmaps();
    }

    public void setVelocity(int v) {
        velocity = v;
    }

    private void loadBitmaps() {
        r = BitmapRepo.getInstance();
        c = r.getContext();
        s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.playerfullhp), 0.1);
        s.addImage(r.getImage(R.drawable.player2hp), 0.1);
        s.addImage(r.getImage(R.drawable.player1hp), 0.1);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void tick(double dt) {
        //super.tick(dt);
        if(World.score >= 15) {
            gravity = 400;
            lift = -1750;
        } else {
            gravity = 200;
            lift = -1350;
        }
        int y = 0;
        velocity += gravity;
        y += velocity;
        setPosition(getPosition().add(new Vec2d(0*dt, y*dt)));
        if(getPosition().getY() > height) {
            setPosition(new Vec2d(100, height));
            velocity = 0;
            hp-=3;
        }
        if(getPosition().getY() < 0) {
            setPosition(new Vec2d(100,0));
            velocity = 0;
        }
    }

    public void onTouch() {
        velocity = lift;
    }

    @Override
    public void resolve(Collision collision, Sprite other) {
        final MediaPlayer mp = MediaPlayer.create(c, R.raw.death);
        if(other.getClass() == PipeSprite.class || other.getClass() == PipeSpriteU.class) {
            mp.start();
            hp--;
            if(hp == 2)
                s.nextBitmap();
            if(hp == 1)
                s.nextBitmap();
            if(hp == 0) {
                Intent failIntent = new Intent(r.getContext(), GameOver.class);
                ((GameActivity)r.getContext()).startActivity(failIntent);
                ((GameActivity) r.getContext()).finish();
            }
        }
    }
}
