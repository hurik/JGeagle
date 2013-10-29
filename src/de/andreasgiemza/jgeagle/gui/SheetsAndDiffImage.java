package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.data.EagleFile;
import de.andreasgiemza.jgeagle.options.Options;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 *
 * @author hurik
 */
public class SheetsAndDiffImage {

    private final Options options;
    private final JComboBox sheetComboBox;
    private final JButton sheetButton;
    private final JButton diffImageButton;

    public SheetsAndDiffImage(Options options, JComboBox sheetComboBox, JButton sheetButton, JButton diffImageButton) {
        this.options = options;
        this.sheetComboBox = sheetComboBox;
        this.sheetButton = sheetButton;
        this.diffImageButton = diffImageButton;
    }

    public void reset() {
        sheetComboBox.setEnabled(false);
        sheetComboBox.removeAllItems();
        sheetButton.setEnabled(false);
        diffImageButton.setEnabled(false);
    }

    public void brdSelected() {
        diffImageButton.setEnabled(true);
    }

    public void schSelected(EagleFile eagleFile, RevCommit oldCommit, RevCommit newCommit) {
        Path oldCountFile = buildPath(oldCommit, eagleFile);
        Path newCountFile = buildPath(oldCommit, eagleFile);

        if (Files.exists(oldCountFile) && Files.exists(newCountFile)) {
            sheetComboBox.setEnabled(true);
        }

        sheetButton.setEnabled(true);
    }

    private Path buildPath(RevCommit revCommit, EagleFile eagleFile) {
        return options.getReposDir()
                .resolve(eagleFile.getRepoName())
                .resolve(revCommit.getName())
                .resolve(eagleFile.getFileName() + ".txt");
    }
}
