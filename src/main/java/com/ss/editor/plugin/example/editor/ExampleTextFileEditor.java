package com.ss.editor.plugin.example.editor;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.ss.editor.FileExtensions;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.plugin.api.editor.BaseFileEditorWithoutState;
import com.ss.editor.plugin.example.Messages;
import com.ss.editor.ui.component.editor.EditorDescription;
import com.ss.editor.ui.css.CssClasses;
import com.ss.rlib.ui.util.FXUtils;
import com.ss.rlib.util.FileUtils;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The example of a simple file editor.
 *
 * @author JavaSaBr
 */
public class ExampleTextFileEditor extends BaseFileEditorWithoutState {

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final EditorDescription DESCRIPTION = new EditorDescription();

    static {
        DESCRIPTION.setConstructor(ExampleTextFileEditor::new);
        DESCRIPTION.setEditorName(Messages.EXAMPLE_TEXT_FILE_EDITOR_NAME);
        DESCRIPTION.setEditorId(ExampleTextFileEditor.class.getSimpleName());
        DESCRIPTION.addExtension(FileExtensions.JME_MATERIAL);
    }

    /**
     * The original content of the opened file.
     */
    @Nullable
    private String originalContent;

    /**
     * The text area.
     */
    @Nullable
    private TextArea textArea;

    @Override
    protected void createContent(@NotNull final StackPane root) {

        textArea = new TextArea();
        textArea.textProperty().addListener((observable, oldValue, newValue) -> updateDirty(newValue));
        textArea.prefHeightProperty().bind(root.heightProperty());
        textArea.prefWidthProperty().bind(root.widthProperty());

        FXUtils.addToPane(textArea, root);
        FXUtils.addClassesTo(textArea, CssClasses.TRANSPARENT_TEXT_AREA);
    }

    /**
     * Update dirty state.
     */
    private void updateDirty(final String newContent) {
        setDirty(!getOriginalContent().equals(newContent));
    }

    @Override
    protected boolean needToolbar() {
        return true;
    }

    @Override
    protected void createToolbar(@NotNull final HBox container) {
        super.createToolbar(container);
        FXUtils.addToPane(createSaveAction(), container);
    }

    /**
     * @return the text area.
     */
    private @NotNull TextArea getTextArea() {
        return notNull(textArea);
    }

    @Override
    @FxThread
    protected void doOpenFile(@NotNull final Path file) throws IOException {
        super.doOpenFile(file);

        setOriginalContent(FileUtils.read(file));

        final TextArea textArea = getTextArea();
        textArea.setText(getOriginalContent());
    }

    /**
     * @return the original content of the opened file.
     */
    private @NotNull String getOriginalContent() {
        return notNull(originalContent);
    }

    /**
     * @param originalContent the original content of the opened file.
     */
    private void setOriginalContent(@NotNull final String originalContent) {
        this.originalContent = originalContent;
    }

    @Override
    @BackgroundThread
    protected void doSave(@NotNull final Path toStore) throws IOException {
        super.doSave(toStore);

        final TextArea textArea = getTextArea();
        final String newContent = textArea.getText();

        try (final PrintWriter out = new PrintWriter(Files.newOutputStream(getEditFile()))) {
            out.print(newContent);
        }
    }

    @Override
    @FxThread
    protected void postSave() {
        super.postSave();

        final TextArea textArea = getTextArea();
        final String newContent = textArea.getText();

        setOriginalContent(newContent);
        updateDirty(newContent);
    }

    @Override
    protected void handleExternalChanges() {
        super.handleExternalChanges();

        final String newContent = FileUtils.read(getEditFile());

        final TextArea textArea = getTextArea();
        final String currentContent = textArea.getText();
        textArea.setText(newContent);

        setOriginalContent(currentContent);
        updateDirty(newContent);
    }

    @Override
    public @NotNull EditorDescription getDescription() {
        return DESCRIPTION;
    }
}
