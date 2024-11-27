package view.utils;

import javax.swing.*;
import java.awt.*;

public class LabelFont {

    public LabelFont(){
    }

    public static JLabel labelWithFont(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
    public static JLabel labelWithFont(String text) {
        Font font = new Font("Dialog", Font.BOLD, 20);
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
