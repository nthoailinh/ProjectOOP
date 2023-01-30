module ProjectOOP{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires jcommander;
    requires async.http.client;
    opens models;
    opens controller to javafx.fxml;
    opens views to javafx.fxml;
    exports controller;
    exports views;
}