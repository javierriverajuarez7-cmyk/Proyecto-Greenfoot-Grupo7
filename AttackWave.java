import greenfoot.*;
import java.util.List;
import java.util.HashSet;

public class AttackWave extends Actor
{
    int size = 20;
    int maxSize = 140;

    int holdTime = 10;
    int timer = 0;

    boolean expanding = true;

    Actor owner;

    public AttackWave(Actor owner)
    {
        this.owner = owner;
        updateImage();
    }

    public void act()
    {
        if (owner != null && owner.getWorld() != null)
        {
            setLocation(owner.getX(), owner.getY());
        }

        if (expanding)
        {
            expand();
        }
        else
        {
            hold();
        }
    }

    public void expand()
    {
        size += 30;

        if (size >= maxSize)
        {
            size = maxSize;
            expanding = false;
        }

        updateImage();
    }

    public void hold()
    {
        timer++;

        if (timer >= holdTime)
        {
            if (getWorld() != null)
            {
                getWorld().removeObject(this);
            }
        }
    }

    public void updateImage()
    {
        GreenfootImage img = new GreenfootImage(size, size);

        img.setColor(new Color(0, 255, 255, 100));
        img.fillOval(0, 0, size, size);

        img.setColor(new Color(0, 255, 255, 200));
        img.drawOval(0, 0, size-1, size-1);

        setImage(img);
    }
}