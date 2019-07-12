module com.puresoltechnologies.javafx.tasks {

    requires transitive com.puresoltechnologies.javafx.reactive;
    requires com.puresoltechnologies.javafx.utils;

    requires transitive javafx.controls;
    requires transitive javafx.graphics;

    exports com.puresoltechnologies.javafx.tasks;

    opens com.puresoltechnologies.javafx.tasks.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.tasks.icons.FatCow_Icons32x32;
}
