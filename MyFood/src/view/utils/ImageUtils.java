package view.utils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtils{

    // Metodo per ridimensionare l'immagine
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Crea un'immagine ridimensionata con il tipo di buffer della originale
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        // Ottieni il contesto grafico 2D dell'immagine ridimensionata
        Graphics2D g2d = resizedImage.createGraphics();

        // Disegna l'immagine originale ridimensionata
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }
}

