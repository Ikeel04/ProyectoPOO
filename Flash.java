import greenfoot.*;

/**
 * Actor class Flash: an object to make other objects seem to momentarily brighten
 */
public class Flash extends Actor
{
    int transVal = 255; // the initial transparency (brightness) value for the flash object
    Actor actor = null; // the actor the the flash object is being used for
    
    /**
     * Flash Constructor:  creates the appropriate image for the flash depending on the shape of the given actor
     *
     * @param actorObj the actor that this flash object will make flash
     */
    public Flash(Actor actorObj)
    {
        actor = actorObj; // save a reference to the actor that is to flash
        GreenfootImage image = new GreenfootImage(actor.getImage().getWidth(), actor.getImage().getHeight()); // create an image the size of the actor's image
        image.setColor(Color.WHITE); // set white light flash color
        if (actor instanceof Bumper) image.fillOval(0, 0, actor.getImage().getWidth()-1, actor.getImage().getHeight()-1); // set bumper flash image
        if (actor instanceof Paddle) image.fill(); // set paddle flash image
        if (actor instanceof Block)
        {
            setRotation(actor.getRotation()); // mimic rotation of block
            image.fill(); // set block flash image
        }
        setImage(image); // set image of flash object
    }
    
    /**
     * Method addedToWorld: removes any current flash objects from the actor that is to flash (avoids compound flashing)
     *
     * @param world the world this flash object was placed in
     */
    public void addedToWorld(World world)
    {
        if (!getIntersectingObjects(Flash.class).isEmpty())
        { // an older flash object exists at this location
            world.removeObject(getOneIntersectingObject(Flash.class)); // remove the old flash object from the world
        }
    }

    /**
     * Method act: performs dimming and disappearing of the flash object
     */
    public void act()
    {
        if (actor instanceof Paddle) setLocation(actor.getX(), actor.getY()); // keeps flash on paddle object
        if (actor instanceof Block) setRotation(actor.getRotation()); // keeps same rotation as block object
        transVal -= 5; // decreases the brightness value of the flash object
        getImage().setTransparency(transVal); // sets the current brightness of the flash
        if (transVal< 50) getWorld().removeObject(this); // removes the flash object when flashing effect is minimal
    }
}
