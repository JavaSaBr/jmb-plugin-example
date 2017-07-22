package com.ss.editor.plugin.example.converter;

import static com.ss.rlib.util.array.ArrayFactory.asArray;
import com.ss.editor.FileExtensions;
import com.ss.editor.file.converter.FileConverterDescription;
import com.ss.editor.file.converter.impl.AbstractFileConverter;
import com.ss.editor.plugin.example.Messages;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The example of implementing file converter.
 *
 * @author JavaSaBr
 */
public class ExampleFileConverter extends AbstractFileConverter {

    /**
     * The constant DESCRIPTION.
     */
    @NotNull
    public static final FileConverterDescription DESCRIPTION = new FileConverterDescription();

    static {
        DESCRIPTION.setDescription(Messages.EXAMPLE_FILE_CONVERTER_DESCRIPTION);
        DESCRIPTION.setConstructor(ExampleFileConverter::new);
        DESCRIPTION.setExtensions(asArray(FileExtensions.IMAGE_PNG));
    }


    @Override
    protected void convertImpl(@NotNull final Path source, @NotNull final Path destination, final boolean overwrite) {

        try (InputStream in = Files.newInputStream(source)) {

            final BufferedImage bufferedImage = ImageIO.read(in);

            try (OutputStream out = Files.newOutputStream(destination)) {
                ImageIO.write(bufferedImage, "jpeg", out);
            }

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        super.convertImpl(source, destination, overwrite);
    }

    @NotNull
    @Override
    public String getTargetExtension() {
        return FileExtensions.IMAGE_JPG;
    }
}
