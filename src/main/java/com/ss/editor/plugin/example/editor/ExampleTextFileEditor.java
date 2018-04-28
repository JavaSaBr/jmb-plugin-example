package com.ss.editor.plugin.example.editor;

import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.ss.editor.FileExtensions;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.plugin.api.editor.BaseFileEditorWithoutState;
import com.ss.editor.plugin.example.PluginMessages;
import com.ss.editor.ui.component.editor.EditorDescription;
import com.ss.editor.ui.css.CssClasses;
import com.ss.rlib.common.util.FileUtils;
import com.ss.rlib.fx.util.FxControlUtils;
import com.ss.rlib.fx.util.FxUtils;
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

    public static final EditorDescription DESCRIPTION = new EditorDescription();

    static {
        DESCRIPTION.setConstructor(ExampleTextFileEditor::new);
        DESCRIPTION.setEditorName(PluginMessages.EXAMPLE_TEXT_FILE_EDITOR_NAME);
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
    protected void createContent(@NotNull StackPane root) {

        textArea = new TextArea();
        textArea.prefHeightProperty().bind(root.heightProperty());
        textArea.prefWidthProperty().bind(root.widthProperty());

        FxControlUtils.onTextChange(textArea, this::updateDirty);

        FxUtils.addClass(textArea, CssClasses.TRANSPARENT_TEXT_AREA);
        FxUtils.addChild(root, textArea);
    }

    /**
     * Update dirty state.
     */
    private void updateDirty(@NotNull String newContent) {
        setDirty(!getOriginalContent().equals(newContent));
    }

    @Override
    protected boolean needToolbar() {
        return true;
    }

    @Override
    protected void createToolbar(@NotNull HBox container) {
        super.createToolbar(container);
        FxUtils.addChild(container, createSaveAction());
    }

    /**
     * Get the text area.
     *
     * @return the text area.
     */
    private @NotNull TextArea getTextArea() {
        return notNull(textArea);
    }

    @Override
    @FxThread
    protected void doOpenFile(@NotNull Path file) throws IOException {
        super.doOpenFile(file);

        setOriginalContent(FileUtils.read(file));
        getTextArea().setText(getOriginalContent());
    }

    /**
     * Get the original content of the opened file.
     *
     * @return the original content of the opened file.
     */
    private @NotNull String getOriginalContent() {
        return notNull(originalContent);
    }

    /**
     * Set the original content of the opened file.
     *
     * @param originalContent the original content of the opened file.
     */
    private void setOriginalContent(@NotNull String originalContent) {
        this.originalContent = originalContent;
    }

    @Override
    @BackgroundThread
    protected void doSave(@NotNull Path toStore) throws IOException {
        super.doSave(toStore);

        var textArea = getTextArea();
        var newContent = textArea.getText();

        try (var out = new PrintWriter(Files.newOutputStream(getEditFile()))) {
            out.print(newContent);
        }
    }

    @Override
    @FxThread
    protected void postSave() {
        super.postSave();

        var textArea = getTextArea();
        var newContent = textArea.getText();

        setOriginalContent(newContent);
        updateDirty(newContent);
    }

    @Override
    protected void handleExternalChanges() {
        super.handleExternalChanges();

        var newContent = FileUtils.read(getEditFile());

        var textArea = getTextArea();
        var currentContent = textArea.getText();
        textArea.setText(newContent);

        setOriginalContent(currentContent);
        updateDirty(newContent);
    }

    @Override
    public @NotNull EditorDescription getDescription() {
        return DESCRIPTION;
    }
}
