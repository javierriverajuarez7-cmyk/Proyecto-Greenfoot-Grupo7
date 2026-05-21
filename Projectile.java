import greenfoot.*;

public class Projectile extends Actor
{
    int speed = 4;
    String type;

    public Projectile(String type)
    {
        this.type = type;
        createImage();
        setSpeed();
    }

    public void act()
    {
        if (getWorld() == null) return;

        move(speed);
        checkHit();
    }

    public void createImage()
    {
        if (type.equals("red"))
        {
            GreenfootImage img = new GreenfootImage(10, 10);
            img.setColor(Color.RED);
            img.fillOval(0, 0, 10, 10);
            setImage(img);
        }

        if (type.equals("purple"))
        {
            GreenfootImage img = new GreenfootImage(18, 6);
            img.setColor(new Color(180, 0, 255));
            img.fillRect(0, 0, 18, 6);
            setImage(img);
        }

        if (type.equals("green"))
        {
            GreenfootImage img = new GreenfootImage(14, 14);

            img.setColor(new Color(0, 255, 0, 100));
            img.fillOval(0, 0, 14, 14);

            img.setColor(Color.GREEN);
            img.drawOval(0, 0, 13, 13);

            setImage(img);
        }
    }

    public void setSpeed()
    {
        if (type.equals("red")) speed = 4;
        if (type.equals("purple")) speed = 8;
        if (type.equals("green")) speed = 2;
    }

    public void checkHit()
    {
        Robot player = (Robot) getOneIntersectingObject(Robot.class);

        if (player != null)
        {
            player.takeDamage(1);

            if (getWorld() != null)
            {
                getWorld().removeObject(this);
            }

            return;
        }

        if (isAtEdge())
        {
            if (getWorld() != null)
            {
                getWorld().removeObject(this);
            }
        }
    }
}