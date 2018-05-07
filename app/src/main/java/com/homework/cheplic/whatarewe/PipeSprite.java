package com.homework.cheplic.whatarewe;

public class PipeSprite extends Sprite {

    private int speed = -8;

    public PipeSprite(Vec2d v) {
        super(v);
        setBad(true);
        loadBitmaps();
    }

    public void setSpeed(int s) {
        speed = s;
    }

    private void loadBitmaps() {
        BitmapRepo r = BitmapRepo.getInstance();
        BitmapSequence s = new BitmapSequence();
        s.addImage(r.getImage(R.drawable.fire1), 0.06);
        s.addImage(r.getImage(R.drawable.fire2), 0.06);
        s.addImage(r.getImage(R.drawable.fire3), 0.06);
        s.addImage(r.getImage(R.drawable.fire4), 0.06);
        s.addImage(r.getImage(R.drawable.fire5), 0.06);
        s.addImage(r.getImage(R.drawable.fire6), 0.06);
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
