package com.ss.editor.plugin.example.converter;

import static com.ss.rlib.common.util.array.ArrayFactory.asArray;
import com.ss.editor.FileExtensions;
import com.ss.editor.file.converter.FileConverterDescription;
import com.ss.editor.file.converter.impl.AbstractFileConverter;
import com.ss.editor.plugin.example.PluginMessages;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The example of implementing file converter.
 *
 * @author JavaSaBr
 */
public class ExampleFileConverter extends AbstractFileConverter {

    public static final FileConverterDescription DESCRIPTION = new FileConverterDescription();

    static {
        DESCRIPTION.setDescription(PluginMessages.EXAMPLE_FILE_CONVERTER_DESCRIPTION);
        DESCRIPTION.setConstructor(ExampleFileConverter::new);
        DESCRIPTION.setExtensions(asArray(FileExtensions.IMAGE_PNG));
    }


    @Override
    protected void convertImpl(@NotNull Path source, @NotNull Path destination, boolean overwrite) throws IOException {
        super.convertImpl(source, destination, overwrite);

        try (var in = Files.newInputStream(source)) {
            var bufferedImage = ImageIO.read(in);
            try (var out = Files.newOutputStream(destination)) {
                ImageIO.write(bufferedImage, "jpeg", out);
            }
        }
    }

    @Override
    public @NotNull String getTargetExtension() {
        return FileExtensions.IMAGE_JPG;
    }
}
