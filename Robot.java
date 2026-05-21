import greenfoot.*;
import java.util.List;

public class Robot extends Actor
{
    int speed = 6;
    int health = 4;
    int attackCooldown = 0;
    
    GreenfootImage normalImage;
    int flashTimer = 0;
    
    public Robot()
    {
        GreenfootImage img = new GreenfootImage("Personaje (1).png");
        img.scale(120, 120);
        normalImage = new GreenfootImage(img);
        setImage(img);
    }
    
    public void act()
    {
        movePlayer();
        handleAttack();
        handleFlash();
        
        if (attackCooldown > 0) attackCooldown--;
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
    
    public void movePlayer()
{
    if (Greenfoot.isKeyDown("w")) {
        setLocation(getX(), getY() - speed);
    }
    if (Greenfoot.isKeyDown("s")) {
        setLocation(getX(), getY() + speed);
    }
    if (Greenfoot.isKeyDown("a")) {
        setLocation(getX() - speed, getY());
    }
    if (Greenfoot.isKeyDown("d")) {
        setLocation(getX() + speed, getY());
    }
}

public void handleAttack()
    {
        if (Greenfoot.isKeyDown("space") && attackCooldown == 0)
        {
            attack();
            attackCooldown = 50;
        }
    }

public void attack()
    {
        Greenfoot.playSound("Onda.wav");
        int range = 120;
        java.util.List<Enemy> enemies = getObjectsInRange(range, Enemy.class);
        
        for (Enemy e : enemies)
        {
            e.takeDamage(1);
        }
        AttackWave wave = new AttackWave(this);
        getWorld().addObject(wave, getX(), getY());
    }
    
public void takeDamage(int dmg)
    {
        health -= dmg;
        
        GreenfootImage flash = new GreenfootImage(normalImage);
        flash.setColor(new Color(255, 0, 0, 120));
        flash.fill();
    
        setImage(flash);
    
        flashTimer = 5;

        if (health <= 0)
        {
            getWorld().removeObject(this);
        }
    }
    
}
