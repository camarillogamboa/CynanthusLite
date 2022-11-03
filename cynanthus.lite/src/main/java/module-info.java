module cynanthus.lite {

    requires java.net.http;
    requires jdk.httpserver;
    requires java.logging;
    requires java.validation;
    requires org.hibernate.validator;
    requires com.fasterxml.classmate;
    requires com.google.gson;
    requires jakarta.mail;
    requires jakarta.activation;

    opens edu.cynanthus.domain to com.google.gson, org.hibernate.validator;
    opens edu.cynanthus.domain.config to com.google.gson, org.hibernate.validator;

}