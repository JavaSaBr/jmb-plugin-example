package com.ss.editor.plugin.example;

import com.ss.editor.plugin.api.messages.MessagesPluginFactory;

import java.util.ResourceBundle;

/**
 * The class with localised all plugin messages.
 *
 * @author JavaSaBr
 */
public class Messages {

    private static final ResourceBundle RESOURCE_BUNDLE = MessagesPluginFactory.getResourceBundle(ExamplePlugin.class,
            "plugin/example/messages/messages");

    public static final String EXAMPLE_FILE_CONVERTER_DESCRIPTION;
    public static final String EXAMPLE_FILE_CREATOR_TITLE;
    public static final String EXAMPLE_TEXT_FILE_EDITOR_NAME;

    static {
        EXAMPLE_FILE_CONVERTER_DESCRIPTION = RESOURCE_BUNDLE.getString("ExampleFileConverterDescription");
        EXAMPLE_FILE_CREATOR_TITLE = RESOURCE_BUNDLE.getString("ExampleFileCreatorTitle");
        EXAMPLE_TEXT_FILE_EDITOR_NAME = RESOURCE_BUNDLE.getString("ExampleTextFileEditorName");
    }
}
