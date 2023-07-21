import greenfoot.*;

/**
 * Actor class Score: a text panel object to display game over message and final score
 */
public class Score extends Actor
{
    GreenfootImage goImg = new GreenfootImage("GAME\nOVER", 96, Color.WHITE, new Color(0, 0, 0, 0)); // the game over text image
    GreenfootImage scoreImg; // the score text image
    
    /**
     * Score Constructor: creates the score text image
     *
     * @param finalScore the final score of the game
     */
    public Score(int finalScore)
    {
        scoreImg = new GreenfootImage("Final Score:\n"+finalScore, 64, Color.WHITE, new Color(0, 0, 0, 0));
    }
    
    /**
     * Method addedToWorld: creates the panel and draws the text images onto it
     *
     * @param world the world this Score object was placed in
     */
    public void addedToWorld(World world)
    {
        GreenfootImage main = new GreenfootImage(world.getWidth(), world.getHeight()); // creates a world-sized panel image
        main.setColor(Color.LIGHT_GRAY); // sets border color of panel
        main.fill(); // fills the background of panel with border color
        main.setColor(Color.DARK_GRAY); // sets main background color of panel
        main.fillRect(50, 50, main.getWidth()-100, main.getHeight()-100); // fills main area of panel with main background color
        
        // draw the text images onto the main image for the panel
        main.drawImage(goImg, (main.getWidth()-goImg.getWidth())/2, (main.getHeight()-goImg.getHeight())/5);
        main.drawImage(scoreImg, (main.getWidth()-scoreImg.getWidth())/2, (main.getHeight()-scoreImg.getHeight())*3/5);
        
        setImage(main); // sets the image of this panel object to the main image
    }
}
