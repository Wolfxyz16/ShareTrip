package eus.ehu.sharetrip.utils;
import java.text.Normalizer;
import java.util.Optional;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class AutoCompleteTextField extends TextField {
    private ObservableList<String> entries;
    private PauseTransition pause = new PauseTransition(Duration.millis(300)); // 300 ms delay


    public AutoCompleteTextField() {
        super();
        this.entries = FXCollections.observableArrayList();
        setupAutoComplete();
    }

    private void setupAutoComplete() {
        pause.setOnFinished( event -> {
            String enteredText = getText();


            if (enteredText.isEmpty()) {
                return;
            }

            String normalizedEnteredText = normalizeText(enteredText);

            Optional<String> match = entries.stream()
                    .filter(entry -> normalizeText(entry).startsWith(normalizedEnteredText))
                    .findFirst();



            match.ifPresent(m -> {
                setText(m);
                positionCaret(enteredText.length());
                selectRange(enteredText.length(), m.length());
            });

        });
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> pause.playFromStart());
    }

    private String normalizeText(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();

        return normalized;

    }

    public void setEntries(ObservableList<String> newEntries) {
        this.entries = newEntries;
    }
}
