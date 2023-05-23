package fr.knsrhuseyin.utils.component.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowMover extends MouseAdapter {

    private Point click;
    private JFrame window;

    public WindowMover(JFrame window) {
        this.window = window;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // If the initial click point isn't null (can happen sometimes)
        if (click != null) {
            // Get the dragged point
            Point draggedPoint = MouseInfo.getPointerInfo()
                    .getLocation();

            // And move the window
            window.setLocation(new Point((int) draggedPoint.getX()
                    - (int) click.getX(), (int) draggedPoint
                    .getY() - (int) click.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        click = e.getPoint();
    }

}
