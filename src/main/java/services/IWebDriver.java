package services;

import java.util.List;

public interface IWebDriver<TypeElement, Mechanism> {
    void get(String url);
    void quit();
    TypeElement findElement(Mechanism by);
    List<TypeElement> findElements(Mechanism by);
}
