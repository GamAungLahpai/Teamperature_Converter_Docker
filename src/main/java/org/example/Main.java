package org.example;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter converter = new TemperatureConverter();

        // Test Fahrenheit to Celsius
        double fahrenheit = 98.6;
        double celsius = converter.fahrenheitToCelsius(fahrenheit);
        System.out.println(fahrenheit + "°F = " + celsius + "°C");

        // Test Celsius to Fahrenheit
        double celsiusTemp = 25;
        double fahrenheitResult = converter.celsiusToFahrenheit(celsiusTemp);
        System.out.println(celsiusTemp + "°C = " + fahrenheitResult + "°F");

        // Test extreme temperature check
        boolean isExtreme = converter.isExtremeTemperature(-50);
        System.out.println("Is -50°C extreme? " + isExtreme);

        // Test Kelvin to Celsius
        double kelvin = 300;
        double celsiusFromKelvin = converter.kelvinToCelsius(kelvin);
        System.out.println(kelvin + "K = " + celsiusFromKelvin + "°C");

        System.out.println("Temperature Converter Test Complete!");
    }
}
