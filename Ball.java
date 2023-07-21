import greenfoot.*;

/**
 * Actor class Ball: a moving ball that bounces off objects and the edge of the world;
 *                   a player controlled paddle is used to keep the ball from the bottom edge of the world,
 *                   where the ball is removed and one of three player lives is used up.
 */
public class Ball extends Actor
{
    // sounds for bouncing off of different edges and objects
    GreenfootSound topbounce = new GreenfootSound("topbounce.mp3"),
                                sidebounce = new GreenfootSound("sidebounce.mp3"),
                                bumperbounce = new GreenfootSound("bumperbounce.wav"),
                                blockbounce = new GreenfootSound("blockbounce.mp3"),
                                paddlebounce = new GreenfootSound("paddlebounce.mp3");
                                
    double realX, realY, speed = 3.0; // fields for controlling movement and location of ball

    /**
     * Ball Constructor: randomly sets the initial direction of movement for the ball between 1 and 179 degrees, inclusive
     */
    public Ball()
    {
        setRotation(Greenfoot.getRandomNumber(179)+1);
    }

    /**
     * Method addedToWorld: records the initial location of where ball was placed into the world
     *
     * @param world A parameter: the world the ball was placed in
     */
    public void addedToWorld(World world)
    {
        realX = getX();
        realY = getY();
    }

    /**
     * Method act: controls the movement of the ball
     */
    public void act()
    {
        Arena arena = (Arena) getWorld(); // get the world the ball is in
        if (Greenfoot.getRandomNumber(2000)==0) turn(1); // occasionally add a random slight turn (prevents eternal back-and-forth bouncing)
        move(speed); // move the ball

        // check bounce off rectangular object
        if (intersectsBlock())
        { // bouncing off rectangular object

            // move ball back off object
            double backDist = 0.0;
            while (intersectsBlock()) { move(-0.1); backDist += 0.1; }

            move(0.1); // move ball to touch object

            // get reference to object
            Actor actor = getOneIntersectingObject(Block.class);
            if (actor == null) actor = getOneIntersectingObject(Paddle.class);

            arena.addObject(new Flash(actor), actor.getX(), actor.getY()); // add flash to object

            // add score for bouncing off object and produce bounce sound
            if (actor instanceof Block)
            {
                blockbounce.play(); 
                arena.addScore(10);
            }
            else
            {
                paddlebounce.play();
                arena.addScore(100);
            }

            // get current width (w) and height (h) of object depending on its rotation
            int dir = actor.getRotation() / 90;
            int size[] = { actor.getImage().getWidth(), actor.getImage().getHeight() };
            int w = size[dir % 2], h = size[1 - dir % 2];

            // determine where bounce occurs on the block
            if (Math.abs(actor.getY() - getY()) <= h / 2)
            { // bouncing off vertical side
                move(-0.1); // move back off object
                setRotation(540 - getRotation()); // set new direction of movement for ball
                move(backDist); // move remaining move distance away from object
                return;
            }
            if (Math.abs(actor.getX() - getX()) <= w / 2)
            { // bouncing off horizontal side
                move(-0.1); // move back off object
                setRotation(360 - getRotation()); // set new direction of movement for ball
                move(backDist); // move remaining distance away from object
                return;
            }
            // bouncing off a corner of the object (only option remaining)            
            int cornerX = actor.getX() + (int) Math.signum(getX() - actor.getX()) * w / 2; // get x coordinate of corner
            int cornerY = actor.getY() + (int) Math.signum(getY() - actor.getY()) * h / 2; // get y coordinate of corner
            double angleFromCorner = Math.atan2(getY() - cornerY, getX() - cornerX) * 180.0 / Math.PI; // get angle of contact
            int newRotation = (int) angleFromCorner * 2 + (540 - getRotation()); // record new direction for ball
            move(-0.1); // move back off object
            setRotation(newRotation); // set new direction of movement for ball
            move(backDist); // move remaining distance away from object
            return;
        }

        // check bounce off round object
        if (intersectsBumper())
        { // bouncing off bumper
            // move back off object
            double backDist = 0.0;
            while (intersectsBumper()) { move(-0.1); backDist += 0.1; }

            move(0.1); // move ball to touch object
            Bumper bumper = (Bumper) getOneIntersectingObject(Bumper.class); // get reference to bumper
            arena.addObject(new Flash(bumper), bumper.getX(), bumper.getY()); // add flash to bumper
            bumperbounce.play(); // produce bounce sound
            arena.addScore(15); // add score for bouncing
            double angle = Math.atan2(getY() - bumper.getY(), getX() - bumper.getX()) * 180.0 / Math.PI; // get angle of contact
            int newRotation = (int) angle * 2 + (540 - getRotation()); // record new direction of movement for ball
            move(-0.1); // move ball back off object
            setRotation(newRotation); // set the new direction of movement for ball
            move(backDist); // move remaining distance away from object
            return;
        }

        // check bounce off edge of world
        if (intersectsWorldEdge())
        { // bouncing off edge of world
            // move ball off edge
            double backDist = 0.0;
            while(intersectsWorldEdge()) { move(-0.1); backDist += 0.1; }
            
            move(0.1); // move ball to touch edge
            
            // check which edge ball is bouncing off of
            if (getX() <= getImage().getWidth() / 2 || getX() >= getWorld().getWidth() - getImage().getWidth() / 2) 
            { // bouncing off a side edge
                sidebounce.play(); // produce bounce sound
                move(-0.1); // move back off edge
                setRotation(540 - getRotation()); // set the new direction of movement for ball
                move(backDist); // move remaining distance away from edge
                turnBlocks(); // switch rotation of blocks
                return;
            }
            if (getY() <= getImage().getHeight() / 2)
            { // bouncing off the top edge
                topbounce.play(); // produce bounce sound
                move(-0.1); // move back off edge
                setRotation(360 - getRotation()); // set the new direction of movement for ball
                move(backDist); // move remaining distance away from edge
                turnBlocks(); // switch rotation of blocks
                return;
            }
            // check at bottom edge
            if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) getWorld().removeObject(this); // remove ball if at bottom edge
        }
    }

    /**
     * Method turnBlocks: switches the rotations of the block objects
     */
    private void turnBlocks()
    {
        for (Object obj : getWorld().getObjects(Block.class))
        { // for each block object in the world
            Actor block = (Actor) obj; // get reference to block
            block.turn(90); // rotate block
        }
    }

    /**
     * Method intersectsBlock: returns true if ball is intersecting a rectangular object (Block or Paddle object)
     *
     * @return the state of the ball intersecting a rectangular object
     */
    private boolean intersectsBlock()
    {
        Actor actor = null;
        if (!getIntersectingObjects(Block.class).isEmpty()) actor = getOneIntersectingObject(Block.class); // get reference to intersecting block object
        if (!getIntersectingObjects(Paddle.class).isEmpty()) actor = getOneIntersectingObject(Paddle.class); // get reference to intersecting paddle object
        if (actor == null) return false; // no rectangular object found intersecting ball
        
        // determine current width (w) and height (h) of object depending on its rotation
        int dir = actor.getRotation() % 90; 
        int size[] = { actor.getImage().getWidth(), actor.getImage().getHeight() };
        int w = size[dir % 2], h = size[1 - dir % 2];
        
        // check intersecting one of the four sides of the object
        if (Math.abs(actor.getX() - getX()) <= w / 2 || Math.abs(actor.getY() - getY()) <= h / 2)
        { // intersecting a side of the object
            return true; // return state of intersection
        }
        
        // check intersecting one of the four corners of the object
        int cornerX = actor.getX() + (int) Math.signum(getX() - actor.getX()) * w / 2; // get x coordinate of closest corner
        int cornerY = actor.getY() + (int) Math.signum(getY() - actor.getY()) * h / 2; // get y coordinate of closest corner
        return Math.hypot((double) (getX() - cornerX), (double) (getY() - cornerY)) <= getImage().getWidth() / 2; // return state of intersection

    }

    /**
     * Method intersectsBumper: returns true if ball is intersecting a round object (Bumper object)
     *
     * @return the state of the ball intersecting a round object
     */
    private boolean intersectsBumper()
    {
        if (getIntersectingObjects(Bumper.class).isEmpty()) return false; // no round objects found near ball
        Bumper bumper = (Bumper) getOneIntersectingObject(Bumper.class); // get reference to near bumper object
        // return state of bumper object intersecting ball
        return Math.hypot((double) getX() - bumper.getX(), (double) getY() - bumper.getY()) <= (bumper.getImage().getWidth() + getImage().getWidth()) / 2;
    }

    /**
     * Method intersectsWorldEdge: returns true if ball is too close to an edge of the world
     *
     * @return the state of the ball being too close to an edge of the world
     */
    private boolean intersectsWorldEdge()
    {
        return getX() <= getImage().getWidth() / 2 ||
               getX() >= getWorld().getWidth() - getImage().getWidth() / 2 ||
               getY() <= getImage().getHeight() / 2 ||
               getY() >= getWorld().getHeight() - getImage().getHeight() / 2;
    }

    /**
     * Method move: performs movement of the ball, tracking its precise location
     *
     * @param velocity the distance the ball is currently moving each act cycle
     */
    public void move(double velocity)
    {
        realX += Math.cos(getRotation() * Math.PI / 180) * velocity; // adjust real x location
        realY += Math.sin(getRotation() * Math.PI / 180) * velocity; // adjust real y location
        setLocation((int) Math.round(realX), (int) Math.round(realY)); // move ball to closest pixel coordinates
    }
}
