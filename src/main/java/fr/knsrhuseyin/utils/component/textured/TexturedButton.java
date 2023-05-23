package fr.knsrhuseyin.utils.component.textured;

import fr.knsrhuseyin.utils.component.abstract_component.AbstractButton;

import java.awt.*;
import java.awt.image.BufferedImage;

import static fr.knsrhuseyin.utils.component.util.Util.*;

public class TexturedButton extends AbstractButton {

    private Image texture;

    private Image textureHover;

    private Image textureDisabled;

    public TexturedButton(BufferedImage texture) {
        this(texture, null, null);
    }

    public TexturedButton(BufferedImage texture, BufferedImage textureHover) {
        this(texture, textureHover, null);
    }

    public TexturedButton(BufferedImage texture, BufferedImage textureHover, BufferedImage textureDisabled) {
        super();

        // If the texture is null, throwing an Illegal Argument Exception, else setting it
        if(texture == null)
            throw new IllegalArgumentException("texture == null");
        this.texture = texture;

        // If the texture hover is null, setting it to the texture, but with a transparent rectangle
        // on it of the Swinger.HOVER_COLOR (by default WHITE) color. Else setting it
        if(textureHover == null)
            this.textureHover = fillImage(copyImage(texture), HOVER_COLOR, this.getParent());
        else
            this.textureHover = textureHover;

        // If the texture disabled is null, setting it to the texture, but with a transparent rectangle
        // on it of the Swinger.DISABLED_COLOR (by default GRAY) color. Else setting it
        if(textureDisabled == null)
            this.textureDisabled = fillImage(copyImage(texture), DISABLED_COLOR, this.getParent());
        else
            this.textureDisabled = textureDisabled;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Getting the corresponding texture
        Image texture;
        if(!this.isEnabled())
            texture = textureDisabled;
        else if (super.isHover())
            texture = textureHover;
        else
            texture = this.texture;

        // Then drawing it
        drawFullsizedImage(g, this, texture);

        // If the text is not null
        if(getText() != null) {
            // Activating the anti alias
            activateAntialias(g);

            // Picking the string color
            if (getTextColor() != null)
                g.setColor(getTextColor());

            // Drawing the text, centered
            drawCenteredString(g, getText(), this.getBounds());
        }
    }

    public void setTexture(Image texture) {
        // If the given texture is null, throwing an Illegal Argument Exception, else setting it
        if(texture == null)
            throw new IllegalArgumentException("texture == null");
        this.texture = texture;

        repaint();
    }

    public void setTextureHover(Image textureHover) {
        // If the given hover texture is null, throwing an Illegal Argument Exception, else setting it
        if(textureHover == null)
            throw new IllegalArgumentException("textureHover == null");
        this.textureHover = textureHover;

        repaint();
    }

    public void setTextureDisabled(Image textureDisabled) {
        // If the given disabled texture is null, throwing an Illegal Argument Exception, else setting it
        if(textureDisabled == null)
            throw new IllegalArgumentException("textureDisabled == null");
        this.textureDisabled = textureDisabled;

        repaint();
    }

    public Image getTexture() {
        return this.texture;
    }

    public Image getTextureHover() {
        return this.textureHover;
    }

    public Image getTextureDisabled() {
        return this.textureDisabled;
    }

    public void setBounds(int x, int y) {
        this.setBounds(x, y, this.texture.getWidth(this.getParent()), this.texture.getHeight(this.getParent()));
    }

}
