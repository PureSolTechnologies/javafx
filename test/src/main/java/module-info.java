module com.puresoltechnologies.javafx.test {

    requires com.puresoltechnologies.javafx.charts;
    requires com.puresoltechnologies.javafx.extensions;
    requires com.puresoltechnologies.javafx.perspectives;
    requires com.puresoltechnologies.javafx.preferences;
    requires com.puresoltechnologies.javafx.workspaces;

    requires javafx.controls;
    requires javafx.graphics;

    opens com.puresoltechnologies.javafx.test;
    opens com.puresoltechnologies.javafx.test.charts;
    opens com.puresoltechnologies.javafx.test.extensions;
    opens com.puresoltechnologies.javafx.test.perspectives;
    opens com.puresoltechnologies.javafx.test.preferences;
    opens com.puresoltechnologies.javafx.test.workspaces;

}