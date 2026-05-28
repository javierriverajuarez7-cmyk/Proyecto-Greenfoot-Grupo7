import greenfoot.*;

public class Enemy extends Actor
{
    int speed = 2;
    int health = 3;

    GreenfootImage normalImage;
    int flashTimer = 0;

    int shootCooldown = 0;

    int burstShots = 0;
    int burstDelay = 0;
    int burstAngle = 0;
    boolean isBursting = false;

    public Enemy()
    {
        GreenfootImage img = getImage();
        img.scale(100, 100);

        normalImage = new GreenfootImage(img);
        setImage(img);
    }

    public void act()
    {
        if (getWorld() == null) return;

        moveBehavior();
        avoidOverlap();
        handleShooting();
        handleFlash();

        if (shootCooldown > 0) shootCooldown--;
    }

    public void moveBehavior()
    {
        java.util.List<Robot> players = getWorld().getObjects(Robot.class);

        if (players.isEmpty())
        {
            return;
        }

        Robot player = players.get(0);

        int dx = player.getX() - getX();
        int dy = player.getY() - getY();

        double distance = Math.sqrt(dx*dx + dy*dy);

        int moveX = 0;
        int moveY = 0;

        if (distance > 200)
        {
            moveX = (dx > 0) ? 1 : -1;
            moveY = (dy > 0) ? 1 : -1;
        }
        else if (distance < 100)
        {
            moveX = (dx > 0) ? -1 : 1;
            moveY = (dy > 0) ? -1 : 1;
        }

        int randomOffsetX = Greenfoot.getRandomNumber(3) - 1;
        int randomOffsetY = Greenfoot.getRandomNumber(3) - 1;

        setLocation(
            getX() + moveX * speed + randomOffsetX,
            getY() + moveY * speed + randomOffsetY
        );
    }

    public void handleFlash()
    {
        if (flashTimer > 0)
        {
            flashTimer--;

            if (flashTimer == 0)
            {
                setImage(normalImage);
            }
        }
    }

    public void avoidOverlap()
    {
        Enemy other = (Enemy) getOneIntersectingObject(Enemy.class);

        if (other != null)
        {
            setLocation(
                getX() + Greenfoot.getRandomNumber(11) - 5,
                getY() + Greenfoot.getRandomNumber(11) - 5
            );
        }
    }

    public void handleShooting()
    {
        if (isBursting)
        {
            handleBurst();
            return;
        }

        if (shootCooldown == 0)
        {
            int attackType = Greenfoot.getRandomNumber(3);

            if (attackType == 0) attackFan();
            if (attackType == 1) attackLaser();
            if (attackType == 2) startBurst();

            shootCooldown = 100;
        }
    }

    public void attackFan()
    {
        Greenfoot.playSound("Abanico.wav");

        java.util.List<Robot> players = getWorld().getObjects(Robot.class);
        if (players.isEmpty()) return;

        Robot player = players.get(0);

        int baseAngle = (int)Math.toDegrees(Math.atan2(
            player.getY() - getY(),
            player.getX() - getX()
        ));

        for (int i = -1; i <= 1; i++)
        {
            Projectile p = new Projectile("red");
            getWorld().addObject(p, getX(), getY());
            p.setRotation(baseAngle + i * 15);
        }
    }

    public void attackLaser()
    {
        Greenfoot.playSound("Disparo.wav");

        java.util.List<Robot> players = getWorld().getObjects(Robot.class);
        if (players.isEmpty()) return;

        Robot player = players.get(0);

        Projectile p = new Projectile("purple");
        getWorld().addObject(p, getX(), getY());

        int angle = (int)Math.toDegrees(Math.atan2(
            player.getY() - getY(),
            player.getX() - getX()
        ));

        p.setRotation(angle);
    }

    public void startBurst()
    {
        java.util.List<Robot> players = getWorld().getObjects(Robot.class);
        if (players.isEmpty()) return;

        Robot player = players.get(0);

        burstAngle = (int)Math.toDegrees(Math.atan2(
            player.getY() - getY(),
            player.getX() - getX()
        ));

        burstShots = 3;
        burstDelay = 0;
        isBursting = true;
    }

    public void handleBurst()
    {
        if (burstShots <= 0)
        {
            isBursting = false;
            return;
        }

        if (burstDelay == 0)
        {
            Greenfoot.playSound("Rafaga.wav");

            Projectile p = new Projectile("green");
            getWorld().addObject(p, getX(), getY());
            p.setRotation(burstAngle);

            burstShots--;
            burstDelay = 10;
        }
        else
        {
            burstDelay--;
        }
    }

    public void takeDamage(int dmg)
    {
        health -= dmg;

        GreenfootImage flash = new GreenfootImage(normalImage);
        flash.setColor(new Color(255, 0, 0, 120));
        flash.fill();

        setImage(flash);
        flashTimer = 5;

        if (health <= 0 && getWorld() != null)
        {
            World world = getWorld();

            Greenfoot.playSound("Corto.wav");

            world.removeObject(this);

            if (world.getObjects(Enemy.class).isEmpty())
            {
                world.showText("VICTORY!", world.getWidth()/2, world.getHeight()/2);
                Greenfoot.stop();
            }
        }
    }
}