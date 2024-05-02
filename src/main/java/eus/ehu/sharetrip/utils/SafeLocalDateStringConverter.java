package eus.ehu.sharetrip.utils;

import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SafeLocalDateStringConverter extends StringConverter<LocalDate> {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    Label outputLabel;

    public SafeLocalDateStringConverter(Label outputLabel) {
        this.outputLabel = outputLabel;
    }

    @Override
    public String toString(LocalDate object) {
        if (object != null) {
            return object.format(formatter);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        try {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, formatter);
            } else {
                return null;
            }
        } catch (DateTimeParseException e) {
            outputLabel.setText("Invalid date");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return null;
        }
    }
}