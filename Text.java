import greenfoot.*;

/**
 * Actor class Text: an object to display a text string
 */
public class Text extends Actor
{
    int fontSize; // the size of the font to use for this text object  
    public Text(String text, int size)
    {
        fontSize = size; // save the size of the font
        setText(text); // create the image with the given text
    }

    /**
     * Method setText: creates the image with the given text
     *
     * @param text the text string for the image to display
     */
    public void setText(String text)
    {
        setImage(new GreenfootImage(text, fontSize, Color.BLACK, new Color(0, 0, 0, 0)));

    }
}
