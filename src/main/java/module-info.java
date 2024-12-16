module org.example.conjuntofxhibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.media;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires net.sf.jasperreports.core;


    opens org.example.conjuntofxhibernate to javafx.fxml;
    exports org.example.conjuntofxhibernate;
    exports org.example.conjuntofxhibernate.models;
    //opens org.example.conjuntofxhibernate.models to javafx.fxml;
    opens org.example.conjuntofxhibernate.models to org.hibernate.orm.core;
}

