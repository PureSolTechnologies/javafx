module com.puresoltechnologies.javafx.preferences {

    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.utils;

    requires transitive javafx.graphics;

    exports com.puresoltechnologies.javafx.preferences;
    exports com.puresoltechnologies.javafx.preferences.dialogs;
    exports com.puresoltechnologies.javafx.preferences.menu;

    provides com.puresoltechnologies.javafx.preferences.PreferencesSerializer
	    with com.puresoltechnologies.javafx.preferences.serializers.BooleanPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.BytePreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.ColorPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.DoublePreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.EnumerationPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.FilePreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.FloatPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.FontDefinitionPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.IntegerPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.LongPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.ShortPreferenceSerializer,
	    com.puresoltechnologies.javafx.preferences.serializers.StringPreferenceSerializer;

    opens com.puresoltechnologies.javafx.preferences.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.preferences.icons.FatCow_Icons32x32;

    uses com.puresoltechnologies.javafx.preferences.PreferencesSerializer;
    uses com.puresoltechnologies.javafx.preferences.dialogs.PreferencesPage;
}
