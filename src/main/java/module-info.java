module ProjectOOP{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires jcommander;
    requires async.http.client;
    opens models;
    opens UI.controller to javafx.fxml;
    opens UI.views to javafx.fxml;
    exports UI.controller;
    exports UI.views;
}