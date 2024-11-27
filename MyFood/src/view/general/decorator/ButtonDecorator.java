package view.general.decorator;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ButtonDecorator extends JButton {
    private final int arcWidth;
    private final int arcHeight;
    private Color color=Color.LIGHT_GRAY;
    public static final Color colorOrange=new Color(255, 165, 0);

    public ButtonDecorator(JButton button, int arcWidth, int arcHeight) {
        super(button.getText());
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;

        // Impostare Arial come font di default
        this.setFont(new Font("Dialog", Font.BOLD, 25));


        // Copiare altre proprietà del bottone originale
        this.setForeground(button.getForeground());
        this.setBackground(button.getBackground());
        this.setBorder(button.getBorder());
        this.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            // Anti-aliasing per rendere i bordi più lisci
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Disegna il bottone con bordi arrotondati
            g2d.setColor(color);
            g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

            setContentAreaFilled(false);
            // Disegna il testo
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

            // Disegna il bordo del rettangolo con bordi arrotondati
            g2d.setColor(getBackground());
            g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

            setContentAreaFilled(false);
        } finally {
            g2d.dispose();
        }
    }

    public void setColor(Color color){
        this.color=color;
        repaint();
    }

    public Color getOrange(){
        return colorOrange;
    }
}
