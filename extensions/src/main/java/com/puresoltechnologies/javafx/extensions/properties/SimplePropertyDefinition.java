package com.puresoltechnologies.javafx.extensions.properties;

import java.util.Objects;
import java.util.Optional;

public class SimplePropertyDefinition<T> implements PropertyDefinition<T> {

    private final String id;
    private final String name;
    private final String description;
    private final Class<T> type;
    private final T defaultValue;
    private final boolean editable;
    private final Optional<PropertyEditor<T>> editor;

    public SimplePropertyDefinition(String id, String name, Class<T> type, T defaultValue) {
	super();
	Objects.requireNonNull(id);
	Objects.requireNonNull(name);
	Objects.requireNonNull(type);
	this.id = id;
	this.name = name;
	this.description = null;
	this.type = type;
	this.defaultValue = defaultValue;
	this.editable = true;
	this.editor = Optional.empty();
    }

    public SimplePropertyDefinition(String id, String name, String description, Class<T> type, T defaultValue) {
	super();
	Objects.requireNonNull(id);
	Objects.requireNonNull(name);
	Objects.requireNonNull(type);
	this.id = id;
	this.name = name;
	this.description = description;
	this.type = type;
	this.defaultValue = defaultValue;
	this.editable = true;
	this.editor = Optional.empty();
    }

    public SimplePropertyDefinition(String id, String name, String description, Class<T> type, T defaultValue,
	    boolean editable, PropertyEditor<T> editor) {
	super();
	this.id = id;
	this.name = name;
	this.description = description;
	this.type = type;
	this.defaultValue = defaultValue;
	this.editable = editable;
	this.editor = Optional.ofNullable(editor);
    }

    @Override
    public final String getId() {
	return id;
    }

    @Override
    public final String getName() {
	return name;
    }

    @Override
    public final String getDescription() {
	return description;
    }

    @Override
    public final Class<T> getType() {
	return type;
    }

    @Override
    public final T getDefaultValue() {
	return defaultValue;
    }

    @Override
    public final boolean isEditable() {
	return editable;
    }

    @Override
    public final Optional<PropertyEditor<T>> getEditor() {
	return editor;
    }

}
