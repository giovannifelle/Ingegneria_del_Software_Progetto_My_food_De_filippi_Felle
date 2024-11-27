package view.utils;
import view.general.decorator.PanelDecorator;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateComboBox extends PanelDecorator {

    private final JComboBox<String> dayComboBox;
    private final JComboBox<String> monthComboBox;
    private final JComboBox<String> yearComboBox;

    public DateComboBox(int arcWidth, int arcHeight) {
        super(new JPanel(), arcWidth, arcHeight);
        // Imposta il layout della classe JPanel
        setLayout(new FlowLayout());

        // ComboBox per il mese
        String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
        monthComboBox = new JComboBox<>(months);

        // ComboBox per il giorno
        dayComboBox = new JComboBox<>();

        // ComboBox per l'anno
        String[] years = new String[100];
        int currentYear = LocalDate.now().getYear();
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        yearComboBox = new JComboBox<>(years);

        // Aggiungi i ComboBox a questa classe JPanel
        add(dayComboBox);
        add(monthComboBox);
        add(yearComboBox);

        // Imposta i giorni in base al mese selezionato
        monthComboBox.addActionListener(e -> updateDays());
        updateDays(); // Aggiorna i giorni iniziali
    }

    // Metodo per aggiornare i giorni disponibili in base al mese selezionato
    private void updateDays() {
        int selectedMonth = monthComboBox.getSelectedIndex() + 1;
        int maxDays;
        switch (selectedMonth) {
            case 2: // Febbraio
                maxDays = 28;
                break;
            case 4: // Aprile
            case 6: // Giugno
            case 9: // Settembre
            case 11: // Novembre
                maxDays = 30;
                break;
            default:
                maxDays = 31;
        }
        dayComboBox.removeAllItems();
        for (int i = 1; i <= maxDays; i++) {
            dayComboBox.addItem(String.valueOf(i));
        }
    }

    // Metodo per ottenere la data selezionata
    public LocalDate getSelectedDate() {
        int day = Integer.parseInt((String) dayComboBox.getSelectedItem());
        int month = monthComboBox.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) yearComboBox.getSelectedItem());
        return LocalDate.of(year, month, day);
    }

    // Metodo per impostare la data selezionata
    public void setSelectedDate(LocalDate date) {
        dayComboBox.setSelectedItem(String.valueOf(date.getDayOfMonth()));
        monthComboBox.setSelectedIndex(date.getMonthValue() - 1);
        yearComboBox.setSelectedItem(String.valueOf(date.getYear()));
    }

    // Metodo per ottenere la data formattata come stringa
    public String getFormattedDate(DateTimeFormatter formatter) {
        return formatter.format(getSelectedDate());
    }
}
