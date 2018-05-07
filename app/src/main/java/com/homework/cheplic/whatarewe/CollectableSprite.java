package com.homework.cheplic.whatarewe;

public class CollectableSprite extends Sprite {

    private int speed = -8;

    public CollectableSprite(Vec2d v) {
        super(v);
        setBad(false);
        loadBitmaps();
    }

    public void setSpeed(int s) {
        speed = s;
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.collectible), 0.06);
        setBitmaps(s);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
        int x = 0;
        if (World.score >= 8)
            x += -16;
        else
            x += speed;
        setPosition(getPosition().add(new Vec2d(x, 0)));
    }

    @Override
    public void resolve(Collision collision, Sprite other) {

    }
}
