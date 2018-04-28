package com.ss.editor.plugin.example.creator;

import static com.ss.editor.extension.property.EditablePropertyType.FLOAT;
import static com.ss.editor.extension.property.EditablePropertyType.STRING;
import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.ss.editor.FileExtensions;
import com.ss.editor.annotation.BackgroundThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.plugin.api.file.creator.GenericFileCreator;
import com.ss.editor.plugin.api.property.PropertyDefinition;
import com.ss.editor.plugin.example.PluginMessages;
import com.ss.editor.ui.component.creator.FileCreatorDescription;
import com.ss.rlib.common.util.VarTable;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
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

    public static final FileCreatorDescription DESCRIPTION = new FileCreatorDescription();

    static {
        DESCRIPTION.setFileDescription(PluginMessages.EXAMPLE_FILE_CREATOR_TITLE);
        DESCRIPTION.setConstructor(ExampleFileCreator::new);
    }

    @Override
    protected @NotNull String getTitleText() {
        return PluginMessages.EXAMPLE_FILE_CREATOR_TITLE;
    }

    @Override
    protected @NotNull String getFileExtension() {
        return FileExtensions.GLSL_VERTEX;
    }

    @Override
    @FromAnyThread
    protected @NotNull Array<PropertyDefinition> getPropertyDefinitions() {

        var definitions = ArrayFactory.<PropertyDefinition>newArray(PropertyDefinition.class);
        definitions.add(new PropertyDefinition(STRING, "String prop", "string.prop", "Default value"));
        definitions.add(new PropertyDefinition(FLOAT, "Float prop", "float.prop", 5F));

        return definitions;
    }

    @Override
    @BackgroundThread
    protected void writeData(@NotNull VarTable vars, @NotNull Path resultFile) throws IOException {
        super.writeData(vars, resultFile);

        var stringValue = vars.getString("string.prop");
        var floatValue = vars.getFloat("float.prop");
        var fileToCreate = notNull(getFileToCreate());

        Files.createFile(fileToCreate);
        Files.write(fileToCreate, (stringValue + floatValue).getBytes());
    }
}
