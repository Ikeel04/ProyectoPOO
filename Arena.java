import greenfoot.*;

/**
 *  World class Arena: a world where a ball is kept in play by keeping it above the player controlled paddle.
 */
public class Arena extends World
{
    int lives = 3; // the game is over when the third ball is lost
    Text livesText = new Text("Lives: "+lives, 24); // displays the current lives remaining
    int score = 0; // each bounce off an object score some points
    Text scoreText = new Text("Score: "+score, 24); // displays the current score
    
    /**
     * Arena Constructor:  creates the bumpers, the paddle and the first ball to put in play
     */
    public Arena()
    {    
        super(401, 620, 1);
        
        // set the speed of the scenario
        Greenfoot.setSpeed(53);
        
        // add the text display for current lives remaining
        addObject(livesText, 60, 600);
        
        // add the text display for current score
        addObject(scoreText, 330, 600);
        
        // add four switching blocks
        addObject(new Block(false), 200, 85);
        addObject(new Block(false), 200, 315);
        addObject(new Block(true), 85, 200);
        addObject(new Block(true), 315, 200);
        
        // add five round bumper
        addObject(new Bumper(), 315, 85);
        addObject(new Bumper(), 85, 85);
        addObject(new Bumper(), 200, 200);
        addObject(new Bumper(), 85, 315);
        addObject(new Bumper(), 315, 315);
        
        // add the player controlled paddle
        addObject(new Paddle(), 200, 580);
        
        // add the first moving ball
        addObject(new Ball(), 20, 20);
    }
    
    /**
     * Method started: starts a new game if last game was over
     */
    public void started()
    {
        if (!getObjects(Score.class).isEmpty()) Greenfoot.setWorld(new Arena());
    }
    
    /**
     * Method act: adds new ball to the world when necessary and ends game when no lives remain
     */
    public void act()
    {
        // check presents of ball in world
        if(getObjects(Ball.class).isEmpty())
        { // ball was removed
            lives--; // decrement remaining lives
            livesText.setText("Lives: "+lives); // update display of remaining lives
            if (lives==0)
            { // no lives remaining
                // add display of final score
                addObject(new Score(score), getWidth()/2, getHeight()/2);
                // stop scenario
                Greenfoot.stop();
                return;
            }
            // lives remain, add a new ball to the world
            addObject(new Ball(), 20, 20);
        }
    }
    
    /**
     * Method addScore: adjusts the current score
     *
     * @param amount A parameter
     */
    public void addScore(int amount)
    {
        score += amount; // add change amount to score
        scoreText.setText("Score: "+score); // update display of current score
    }
}