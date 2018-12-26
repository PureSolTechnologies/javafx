module com.puresoltechnologies.javafx.tasks {

    requires com.puresoltechnologies.javafx.reactive;
    requires com.puresoltechnologies.javafx.utils;

    requires io.reactivex.rxjava2;
    requires javafx.controls;

    exports com.puresoltechnologies.javafx.tasks;
    
    opens com.puresoltechnologies.javafx.tasks.icons.FatCow_Icons16x16;
    opens com.puresoltechnologies.javafx.tasks.icons.FatCow_Icons32x32;
}
