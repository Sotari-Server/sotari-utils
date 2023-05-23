package fr.knsrhuseyin.utils.component.abstract_component;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractProgressBar extends JComponent {

    /**
     * The value of the progress bar
     */
    private int value;

    /**
     * The progress bar maximum value
     */
    private int maximum;

    /**
     * The progress bar string
     */
    private String string;

    /**
     * If the string is painted
     */
    private boolean stringPainted;

    /**
     * The color of the string
     */
    private Color stringColor;

    /**
     * If the bar is vertical
     */
    private boolean vertical = false;

    /**
     * Set the value of the progress bar
     *
     * @param value
     *            The new value to set
     */
    public void setValue(int value) {
        this.value = value;

        repaint();
    }

    /**
     * Return the value of the progress bar
     *
     * @return The bar value
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of the progress bar
     *
     * value
     *            The new value to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;

        repaint();
    }

    /**
     * Return the maximum value of the progress bar
     *
     * @return The bar maximum
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Set the string of the progress bar
     *
     * @param string
     *            The new string to set
     */
    public void setString(String string) {
        // If the given string is null, throwing an Illegal Argument Exception, else setting it
        if(string == null)
            throw new IllegalArgumentException("string == null");
        this.string = string;

        repaint();
    }

    /**
     * Return the string of the progress bar
     *
     * @return The bar string
     */
    public String getString() {
        return string;
    }

    /**
     * Set if the string is painted or not
     *
     * @param stringPainted
     *            If it is painted
     */
    public void setStringPainted(boolean stringPainted) {
        this.stringPainted = stringPainted;

        repaint();
    }

    /**
     * Return if the string is painted or not
     *
     * @return If it is painted
     */
    public boolean isStringPainted() {
        return stringPainted;
    }

    /**
     * Set the string color
     *
     * @param stringColor
     *            The new string color
     */
    public void setStringColor(Color stringColor) {
        // If the given string color is null, throwing an Illegal Argument Exception, else setting it
        if(stringColor == null)
            throw new IllegalArgumentException("stringColor == null");
        this.stringColor = stringColor;

        repaint();
    }

    /**
     * Return the string color (default is null)
     *
     * @return The string color
     */
    public Color getStringColor() {
        return stringColor;
    }

    /**
     * Set if the bar is vertical or not
     *
     * @param vertical
     *            If it is vertical
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;

        repaint();
    }

    /**
     * Return if the bar is vertical or not
     *
     * @return If it is vertical
     */
    public boolean isVertical() {
        return vertical;
    }
}
