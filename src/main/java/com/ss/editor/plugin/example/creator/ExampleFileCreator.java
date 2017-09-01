package com.ss.editor.plugin.example.creator;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.ss.editor.FileExtensions;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.extension.property.EditablePropertyType;
import com.ss.editor.plugin.api.file.creator.GenericFileCreator;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.plugin.example.Messages;
import com.ss.editor.ui.component.creator.FileCreatorDescription;
import com.ss.rlib.util.VarTable;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * THe example of a file creator.
 *
 * @author JavaSaBr
 */
public class ExampleFileCreator extends GenericFileCreator {

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final FileCreatorDescription DESCRIPTION = new FileCreatorDescription();

    static {
        DESCRIPTION.setFileDescription(Messages.EXAMPLE_FILE_CREATOR_TITLE);
        DESCRIPTION.setConstructor(ExampleFileCreator::new);
    }

    @Override
    protected @NotNull String getTitleText() {
        return Messages.EXAMPLE_FILE_CREATOR_TITLE;
    }

    @Override
    protected @NotNull String getFileExtension() {
        return FileExtensions.GLSL_VERTEX;
    }

    @Override
    @FromAnyThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        final Array<PropertyDefinition> definitions = ArrayFactory.newArray(PropertyDefinition.class);
        definitions.add(new PropertyDefinition(EditablePropertyType.STRING, "String prop", "string.prop", "Default value"));
        definitions.add(new PropertyDefinition(EditablePropertyType.FLOAT, "Float prop", "float.prop", 5F));

        return definitions;
    }

    @Override
    @BackgroundThread
    protected void writeData(@NotNull final VarTable vars, @NotNull final Path resultFile) throws IOException {
        super.writeData(vars, resultFile);

        final String stringValue = vars.getString("string.prop");
        final float floatValue = vars.getFloat("float.prop");

        final Path fileToCreate = notNull(getFileToCreate());

        Files.createFile(fileToCreate);
        Files.write(fileToCreate, (stringValue + floatValue).getBytes());
    }
}
