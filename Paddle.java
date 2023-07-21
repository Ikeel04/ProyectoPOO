import greenfoot.*;

public class Paddle extends Actor
{
    // the texts related to the paddle controls
    String[] texts = { "< keyboard >", "\"m\" for mouse", "< mouse >", "\"k\" for keyboard" };
    
    Text inputText = new Text(" ", 16); // text showing what paddle control is current
    Text altText = new Text(" ", 16); // text showing intruction on how to change paddle control
    int inputType; // current paddle control value (zero (0) for keyboard control; one (1) for mouse control)
    
    /**
     * Method addedToWorld: adds the text objects related to the paddle controls into the world and sets the initial paddle control
     *
     * @param world the world this paddle object was placed in
     */
    public void addedToWorld(World world)
    {
        world.addObject(inputText, getX(), getY()); // adds the current control text object at paddle location
        world.addObject(altText, world.getWidth()/2, world.getHeight()-16); // add the control change instruction text object to world
        updateInput(0); // sets the initial control for the paddle to keyboard
    }
    
    /**
     * Method act: controls paddle movement and deals with the changing of paddle control 
     */
    public void act()
    {
        if(inputType==0)
        { // control is keyboard input
            // determine direction of movement
            int dx=0;
            if(Greenfoot.isKeyDown("left"))dx--;
            if(Greenfoot.isKeyDown("right"))dx++;
            // move paddle
            move(3*dx);
            // keep padddle inside world
            if(getX()<getImage().getWidth()/2 || getX()>getWorld().getWidth()-getImage().getWidth()/2) move(-3*dx);
        }
        else
        { // control is mouse movement
            if(Greenfoot.mouseMoved(null))
            { // mouse has moved
                // determine new x coordinate of mouse
                MouseInfo mouse = Greenfoot.getMouseInfo();
                int x = mouse.getX();
                // keep paddle at last mouse location inside world
                if(x>=getImage().getWidth()/2 && x<getWorld().getWidth()-getImage().getWidth()/2) setLocation(x, getY());
            }
        }
        inputText.setLocation(getX(), getY()); // keep current control text with paddle
        
        // check for change in paddle control
        String key = Greenfoot.getKey(); // record possible keystroke
        if(key==null)return; // no keystroke found
        if("k".equals(key.toLowerCase())) updateInput(0); // set control to keyboard input
        if("m".equals(key.toLowerCase())) updateInput(1); // set control to mouse movement
    }
    
    /**
     * Method updateInput: sets the control for the paddle (keyboard input or mouse movement)
     *
     * @param type the control value (zero (0) for keyboard input; one (1) for mouse movement)
     */
    private void updateInput(int type)
    {
        inputText.setText(texts[type*2]); // set current control text
        altText.setText(texts[type*2+1]); // set change control instruction text
        inputType = type; // save current control value
    }
}
