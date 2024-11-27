package view.general.decorator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PanelDecorator extends JPanel {
    private final int arcWidth;
    private final int arcHeight;

    public PanelDecorator(JPanel panel, int arcWidth, int arcHeight) {
        super(panel.getLayout());
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.setForeground(panel.getForeground());
        this.setBackground(panel.getBackground());
        this.setBorder(panel.getBorder());

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Colore di sfondo del pannello
            g2d.setColor(Color.LIGHT_GRAY);
            // Disegna lo sfondo con angoli arrotondati
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
            // Ridurre la quantità di testo dipinta per evitare che sia oscurata dallo sfondo
            setOpaque(false);
            super.paintComponent(g2d);  // Disegna gli altri componenti
        } finally {
            g2d.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Disegna il bordo con angoli arrotondati
            g2d.setColor(Color.GRAY);  // Colore del bordo
            g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
        } finally {
            g2d.dispose();
        }
    }
}
