package com.puresoltechnologies.javafx.preferences;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.junit.jupiter.api.Test;

public class PreferencesSerializerTest {

    @Test
    public void test() {
	@SuppressWarnings("rawtypes")
	ServiceLoader<PreferencesSerializer> loader = ServiceLoader.load(PreferencesSerializer.class);
	@SuppressWarnings("rawtypes")
	Iterator<PreferencesSerializer> iterator = loader.iterator();
	while (iterator.hasNext()) {
	    PreferencesSerializer<?> next = iterator.next();
	    System.out.println(next.getClass().getName());
	}
    }

}
