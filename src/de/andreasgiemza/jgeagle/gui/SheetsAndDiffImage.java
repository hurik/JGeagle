package de.andreasgiemza.jgeagle.gui;

import de.andreasgiemza.jgeagle.data.EagleFile;
import de.andreasgiemza.jgeagle.helper.DiffImage;
import de.andreasgiemza.jgeagle.helper.Eagle;
import de.andreasgiemza.jgeagle.options.Options;
import de.andreasgiemza.jgeagle.repo.JGit;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public SheetsAndDiffImage(
            Options options,
            JComboBox sheetComboBox,
            JButton sheetButton,
            JButton diffImageButton) {
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

    public void schSelected(
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit) {
        Path oldCountFile = buildPath(oldCommit, eagleFile, ".txt");
        Path newCountFile = buildPath(oldCommit, eagleFile, ".txt");

        if (Files.exists(oldCountFile) && Files.exists(newCountFile)) {
            try {
                int oldCount = Integer.parseInt(Files.readAllLines(oldCountFile, Charset.defaultCharset()).get(0));
                int newCount = Integer.parseInt(Files.readAllLines(newCountFile, Charset.defaultCharset()).get(0));

                for (int i = 1; i <= Math.min(oldCount, newCount); i++) {
                    sheetComboBox.addItem(i);
                }
            } catch (IOException ex) {
                Logger.getLogger(SheetsAndDiffImage.class.getName()).log(Level.SEVERE, null, ex);
            }

            sheetButton.setEnabled(false);
            sheetComboBox.setEnabled(true);
            diffImageButton.setEnabled(true);
        } else {
            sheetButton.setEnabled(true);
        }
    }

    private Path buildPath(RevCommit revCommit, EagleFile eagleFile, String fileExtension) {
        return options.getReposDir()
                .resolve(eagleFile.getRepoName())
                .resolve(revCommit.getName())
                .resolve(eagleFile.getRepoFile().getParent())
                .resolve(eagleFile.getFileName() + fileExtension);
    }

    private Path buildDiffPath(EagleFile eagleFile, RevCommit oldCommit, RevCommit newCommit, String fileExtension) {
        return options.getReposDir()
                .resolve(eagleFile.getRepoName())
                .resolve(oldCommit.getName() + "-" + newCommit.getName())
                .resolve(eagleFile.getRepoFile().getParent())
                .resolve(eagleFile.getFileName() + fileExtension);
    }

    public void countSheets(
            JGit jGit,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit)
            throws IOException, InterruptedException {
        options.cleanTempDir();

        Path oldCountFile = buildPath(oldCommit, eagleFile, ".txt");

        if (!Files.exists(oldCountFile)) {
            Path oldSchematicFile = options.getTempDir().resolve("old.sch");
            jGit.extractFile(oldSchematicFile, oldCommit, eagleFile);

            Eagle.countSheets(
                    options.getPropEagleBinaryAsPath(),
                    options.getCountSheetsUlp(),
                    oldCountFile,
                    oldSchematicFile);
        }

        Path newCountFile = buildPath(newCommit, eagleFile, ".txt");

        if (!Files.exists(newCountFile)) {
            Path newSchematicFile = options.getTempDir().resolve("new.sch");
            jGit.extractFile(newSchematicFile, newCommit, eagleFile);

            Eagle.countSheets(
                    options.getPropEagleBinaryAsPath(),
                    options.getCountSheetsUlp(),
                    newCountFile,
                    newSchematicFile);
        }

        schSelected(eagleFile, oldCommit, newCommit);
    }

    public void createDiffImage(
            JGit jGit,
            EagleFile eagleFile,
            RevCommit oldCommit,
            RevCommit newCommit)
            throws IOException, InterruptedException {
        options.cleanTempDir();

        if (eagleFile.getFileExtension().equals(EagleFile.BRD)) {
            Path oldImageFile = buildPath(oldCommit, eagleFile, eagleFile.getFileName() + ".png");

            if (!Files.exists(oldImageFile)) {
                Path oldBoardFile = options.getTempDir().resolve("old.brd");
                jGit.extractFile(oldBoardFile, oldCommit, eagleFile);

                Eagle.extractBoard(
                        options.getPropEagleBinaryAsPath(),
                        oldImageFile,
                        options.getPropBoardDpiAsInt(),
                        oldBoardFile);
            }

            Path newImageFile = buildPath(newCommit, eagleFile, eagleFile.getFileName() + ".png");

            if (!Files.exists(newImageFile)) {
                Path newBoardFile = options.getTempDir().resolve("new.brd");
                jGit.extractFile(newBoardFile, newCommit, eagleFile);

                Eagle.extractBoard(
                        options.getPropEagleBinaryAsPath(),
                        newImageFile,
                        options.getPropBoardDpiAsInt(),
                        newBoardFile);
            }

            Path diffImageFile = buildDiffPath(eagleFile, oldCommit, newCommit, ".png");

            if (!Files.exists(diffImageFile)) {
                DiffImage.make(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        false,
                        0.3,
                        options.getPropAddedElementColor(),
                        options.getPropRemovedElementColor(),
                        options.getPropUndefinedColor());
            }
        } else {
            int sheet = (int) sheetComboBox.getSelectedItem();

            Path oldImageFile = buildPath(oldCommit, eagleFile, "-SHEET_" + sheet + ".png");

            if (!Files.exists(oldImageFile)) {
                Path oldSchematicFile = options.getTempDir().resolve("old.sch");
                jGit.extractFile(oldSchematicFile, oldCommit, eagleFile);

                Eagle.extractSheet(
                        options.getPropEagleBinaryAsPath(),
                        sheet,
                        oldImageFile,
                        options.getPropSchematicDpiAsInt(),
                        oldSchematicFile);
            }

            Path newImageFile = buildPath(newCommit, eagleFile, "-SHEET_" + sheet + ".png");

            if (!Files.exists(newImageFile)) {
                Path newSchematicFile = options.getTempDir().resolve("new.sch");
                jGit.extractFile(newSchematicFile, newCommit, eagleFile);

                Eagle.extractSheet(
                        options.getPropEagleBinaryAsPath(),
                        sheet,
                        newImageFile,
                        options.getPropSchematicDpiAsInt(),
                        newSchematicFile);
            }

            Path diffImageFile = buildDiffPath(eagleFile, oldCommit, newCommit, "-SHEET_" + sheet + ".png");

            if (!Files.exists(diffImageFile)) {
                DiffImage.make(
                        oldImageFile,
                        newImageFile,
                        diffImageFile,
                        true,
                        0.3,
                        options.getPropAddedElementColor(),
                        options.getPropRemovedElementColor(),
                        options.getPropUndefinedColor());
            }
        }
    }
}
