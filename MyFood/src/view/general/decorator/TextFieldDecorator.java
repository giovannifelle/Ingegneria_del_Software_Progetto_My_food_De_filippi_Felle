package view.general.decorator;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TextFieldDecorator extends JTextField {
    private final int arcWidth;
    private final int arcHeight;

    public TextFieldDecorator(JTextField textField, int arcWidth, int arcHeight) {
        super(textField.getColumns());
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;

        // Impostazioni di default, simili al tuo esempio
        this.setFont(new Font("Dialog", Font.PLAIN, 15));
        this.setForeground(Color.BLACK);
        this.setBackground(Color.WHITE);
        this.setBorder(new MatteBorder(0, 10, 0, 10, Color.LIGHT_GRAY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            // Anti-aliasing per rendere i bordi più lisci
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Colore di sfondo e forma arrotondata
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
            // Ridurre la quantità di testo dipinta per evitare che sia oscurata dallo sfondo
            setOpaque(false);
            super.paintComponent(g2d);
        } finally {
            g2d.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Colore del bordo e forma arrotondata
            g2d.setColor(Color.LIGHT_GRAY); // Puoi personalizzare il colore del bordo
            g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
        } finally {
            g2d.dispose();
        }
    }
}
