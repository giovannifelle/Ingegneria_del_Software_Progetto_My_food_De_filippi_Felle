import javax.swing.*;
import java.awt.*;
import business.SessionBusiness;
import view.MainFrame;
import view.MainLoadingFrame;

public class MainDemo {
    public static void main(String[] args) {
        // Crea il frame di caricamento
        MainLoadingFrame loadingFrame = new MainLoadingFrame();
        loadingFrame.setVisible(true);

        // Utilizza SwingWorker per eseguire il caricamento della sessione in background
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Avvia la sessione (questo potrebbe richiedere del tempo)
                SessionBusiness.startSession();
                return null;
            }

            @Override
            protected void done() {
                // Chiudi il frame di caricamento
                loadingFrame.dispose();

                // Mostra il frame principale
                MainFrame frame = MainFrame.getInstance();
                frame.setVisible(true);
            }
        };

        // Avvia il caricamento della sessione in background
        worker.execute();
    }
}