import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        super(800, 600, 1); 
        setBackground("Fondo.png");
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Robot robot = new Robot();
        addObject(robot,224,255);
        robot.setLocation(411,304);
        robot.setLocation(401,305);
        Enemy enemy = new Enemy();
        addObject(enemy,674,144);
        Enemy enemy2 = new Enemy();
        addObject(enemy2,126,485);
        enemy2.setLocation(137,486);
        removeObject(enemy2);
        Enemy enemy3 = new Enemy();
        addObject(enemy3,117,495);
        removeObject(enemy3);
        Enemy enemy4 = new Enemy();
        addObject(enemy4,143,484);
    }
}
