package com.ss.editor.plugin.example;

import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.file.converter.FileConverterRegistry;
import com.ss.editor.plugin.EditorPlugin;
import com.ss.editor.plugin.example.converter.ExampleFileConverter;
import com.ss.editor.plugin.example.creator.ExampleFileCreator;
import com.ss.editor.plugin.example.editor.ExampleTextFileEditor;
import com.ss.editor.ui.component.asset.tree.AssetTreeContextMenuFillerRegistry;
import com.ss.editor.ui.component.creator.FileCreatorRegistry;
import com.ss.editor.ui.component.editor.EditorRegistry;
import com.ss.rlib.common.plugin.PluginContainer;
import com.ss.rlib.common.plugin.annotation.PluginDescription;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of {@link EditorPlugin}.
 */
@PluginDescription(
        id = "com.ss.editor.plugin.example",
        version = "0.2",
        minAppVersion = "1.8.0",
        name = "Example plugin",
        description = "Example plugin"
)
public class ExamplePlugin extends EditorPlugin {

    public ExamplePlugin(@NotNull PluginContainer pluginContainer) {
        super(pluginContainer);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull AssetTreeContextMenuFillerRegistry registry) {
        super.register(registry);

        registry.registerSingle((element, items, actionTester) -> {

            var item = new MenuItem("Hello action");
            item.setOnAction(event -> System.out.println("Hello"));

            items.add(item);
        });
    }

    @Override
    @FromAnyThread
    public void register(@NotNull FileCreatorRegistry registry) {
        super.register(registry);
        registry.register(ExampleFileCreator.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull FileConverterRegistry registry) {
        super.register(registry);
        registry.register(ExampleFileConverter.DESCRIPTION);
    }

    @Override
    @FromAnyThread
    public void register(@NotNull EditorRegistry registry) {
        super.register(registry);
        registry.register(ExampleTextFileEditor.DESCRIPTION);
    }
}
