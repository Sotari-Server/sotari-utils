package fr.knsrhuseyin.utils.component.abstract_component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public abstract class AbstractButton extends JComponent implements MouseListener {

    private String text;

    private Color textColor;

    private final ArrayList<ActionListener> eventListeners = new ArrayList<ActionListener>();

    private boolean hover = false;

    public AbstractButton() {
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // If the button is enabled
        if(this.isEnabled())
            // Executing all the action listeners
            for(ActionListener eventListener : this.eventListeners)
                eventListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "RELEASED"));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hover = true;

        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hover = false;

        repaint();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        repaint();
    }

    public void setText(String text) {
        // If the given text is null, throwing an Illegal Argument Exception, else setting it
        if(text == null)
            throw new IllegalArgumentException("text == null");
        this.text = text;

        repaint();
    }

    public String getText() {
        return text;
    }

    public void setTextColor(Color textColor) {
        // If the given string color is null, throwing an Illegal Argument Exception, else setting it
        if(textColor == null)
            throw new IllegalArgumentException("textColor == null");
        this.textColor = textColor;

        repaint();
    }

    public Color getTextColor() {
        return textColor;
    }

    public void addEventListener(ActionListener eventListener) {
        // If the given event listener is null, throwing an Illegal Argument Exception, else setting it
        if(eventListener == null)
            throw new IllegalArgumentException("eventListener == null");

        this.eventListeners.add((ActionListener) eventListener);
    }

    public ArrayList<ActionListener> getEventListeners() {
        return this.eventListeners;
    }

    public boolean isHover() {
        return this.hover;
    }

}
