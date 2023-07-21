import greenfoot.*;

/**
 * Actor class Block: an object for the ball to bounce off of;
 *                    objects of this class turn 90 degrees every time the ball hits a side or top edge of the world
 */
public class Block extends Actor
{
    /**
     * Block Constructor: sets the initial rotation of the block object
     *
     * @param rotated a flag indicating whether the initial state is rotated 90 degrees or not
     */
    public Block(boolean rotated)
    {
        if (rotated) turn(90); // turns the object 90 degrees if initial state 'rotated' is true
    }
}
