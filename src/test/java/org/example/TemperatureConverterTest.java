package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureConverterTest {

    private TemperatureConverter converter;
    private final double DELTA = 0.001;

    @BeforeEach
    void setUp() {
        converter = new TemperatureConverter();
    }

    // Tests for fahrenheitToCelsius method

    @Test
    void testFahrenheitToCelsius_FreezingPoint() {
        assertEquals(0, converter.fahrenheitToCelsius(32), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_BoilingPoint() {
        assertEquals(100, converter.fahrenheitToCelsius(212), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_NegativeValue() {
        assertEquals(-40, converter.fahrenheitToCelsius(-40), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_RoomTemperature() {
        assertEquals(20, converter.fahrenheitToCelsius(68), DELTA);
    }

    @Test
    void testFahrenheitToCelsius_Zero() {
        assertEquals(-17.778, converter.fahrenheitToCelsius(0), DELTA);
    }


    // Tests for celsiusToFahrenheit method

    @Test
    void testCelsiusToFahrenheit_FreezingPoint() {
        assertEquals(32, converter.celsiusToFahrenheit(0), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_BoilingPoint() {
        assertEquals(212, converter.celsiusToFahrenheit(100), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_NegativeValue() {
        assertEquals(-40, converter.celsiusToFahrenheit(-40), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_RoomTemperature() {
        assertEquals(68, converter.celsiusToFahrenheit(20), DELTA);
    }

    @Test
    void testCelsiusToFahrenheit_Zero() {
        assertEquals(32, converter.celsiusToFahrenheit(0), DELTA);
    }

    // Tests for isExtremeTemperature method

    @Test
    void testIsExtremeTemperature_BelowMinus40() {
        assertTrue(converter.isExtremeTemperature(-45));
        assertTrue(converter.isExtremeTemperature(-50));
        assertTrue(converter.isExtremeTemperature(-100));
    }

    @Test
    void testIsExtremeTemperature_Above50() {
        assertTrue(converter.isExtremeTemperature(55));
        assertTrue(converter.isExtremeTemperature(60));
        assertTrue(converter.isExtremeTemperature(100));
    }

    @Test
    void testIsExtremeTemperature_NormalTemperatures() {
        assertFalse(converter.isExtremeTemperature(25));
        assertFalse(converter.isExtremeTemperature(0));
        assertFalse(converter.isExtremeTemperature(-20));
        assertFalse(converter.isExtremeTemperature(30));
    }

    @Test
    void testIsExtremeTemperature_BoundaryValues() {
        assertFalse(converter.isExtremeTemperature(-40));   // exactly -40 (not extreme)
        assertFalse(converter.isExtremeTemperature(50));    // exactly 50  (not extreme)
        assertTrue(converter.isExtremeTemperature(-40.1));  // just below -40 (extreme)
        assertTrue(converter.isExtremeTemperature(50.1));   // just above 50  (extreme)
    }

    // Inverse conversion tests

    @Test
    void testTemperatureConversionsAreInverse() {
        double originalFahrenheit = 75.5;
        double celsius = converter.fahrenheitToCelsius(originalFahrenheit);
        double backToFahrenheit = converter.celsiusToFahrenheit(celsius);
        assertEquals(originalFahrenheit, backToFahrenheit, DELTA);
    }

    @Test
    void testTemperatureConversionsAreInverseReverse() {
        double originalCelsius = 23.5;
        double fahrenheit = converter.celsiusToFahrenheit(originalCelsius);
        double backToCelsius = converter.fahrenheitToCelsius(fahrenheit);
        assertEquals(originalCelsius, backToCelsius, DELTA);
    }
    // NEW: Tests for kelvinToCelsius method
    @Test
    void testKelvinToCelsius_AbsoluteZero() {
        // 0 K = -273.15°C (absolute zero)
        assertEquals(-273.15, converter.kelvinToCelsius(0), DELTA);
    }

    @Test
    void testKelvinToCelsius_FreezingPoint() {
        // 273.15 K = 0°C (water freezing point)
        assertEquals(0.0, converter.kelvinToCelsius(273.15), DELTA);
    }

    @Test
    void testKelvinToCelsius_BoilingPoint() {
        // 373.15 K = 100°C (water boiling point)
        assertEquals(100.0, converter.kelvinToCelsius(373.15), DELTA);
    }

    @Test
    void testKelvinToCelsius_RoomTemperature() {
        // 293.15 K = 20°C (room temperature)
        assertEquals(20.0, converter.kelvinToCelsius(293.15), DELTA);
    }

    @Test
    void testKelvinToCelsius_BodyTemperature() {
        // 310.15 K = 37°C (human body temperature)
        assertEquals(37.0, converter.kelvinToCelsius(310.15), DELTA);
    }

    @Test
    void testKelvinToCelsius_NegativeInput_ThrowsException() {
        // Kelvin cannot be negative — physical impossibility
        assertThrows(IllegalArgumentException.class,
                () -> converter.kelvinToCelsius(-1));
    }

    @Test
    void testKelvinToCelsius_NegativeInput_ExceptionMessage() {
        // Verify the exception message is correct
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.kelvinToCelsius(-1));
        assertEquals("Kelvin cannot be negative", exception.getMessage());
    }

    //  Parameterized test covering multiple known Kelvin → Celsius conversions
    @ParameterizedTest(name = "{0} K should equal {1} °C")
    @CsvSource({
            "0.0,    -273.15",   // absolute zero
            "273.15,    0.0",    // freezing point
            "373.15,  100.0",    // boiling point
            "293.15,   20.0",    // room temperature
            "310.15,   37.0",    // body temperature
            "255.37,  -17.78"    // 0°F in Kelvin
    })
    void testKelvinToCelsius_Parameterized(double kelvin, double expectedCelsius) {
        assertEquals(expectedCelsius, converter.kelvinToCelsius(kelvin), DELTA,
                "Kelvin to Celsius conversion failed for " + kelvin + " K");
    }
}