package ui;

public interface MapsEvent<T> {
    void handle(T event);
}