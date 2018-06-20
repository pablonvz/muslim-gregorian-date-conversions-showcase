package com.pablocastelnovo.muslim_gregorian_date_conversions_showcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class ConvertibleDateTests {
    @ParameterizedTest
    @ArgumentsSource(MuslimDatesIncludingNonProlepticYearsArguments.class)
    public void toGregorianJodaTime(LocalDate muslimDate, LocalDate gregorianDate) {
        assertEquals(gregorianDate, new ConvertibleDate(muslimDate).toGregorianJodaTime());
    }

    @ParameterizedTest
    @ArgumentsSource(MuslimDatesWithoutNonProlepticYearsArguments.class)
    public void toGregorianJavaTime(LocalDate muslimDate, LocalDate gregorianDate) {
        assertEquals(gregorianDate, new ConvertibleDate(muslimDate).toGregorianJavaTime());
    }

    @ParameterizedTest
    @ArgumentsSource(MuslimDatesOnlyNonProlepticYearsArguments.class)
    public void toGregorianJavaTimeProlepticYears(LocalDate muslimDate) {
        assertThrows(DateTimeException.class, () -> new ConvertibleDate(muslimDate).toGregorianJavaTime());
    }

    private static class MuslimDatesIncludingNonProlepticYearsArguments implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
            // http://www.islamicity.org/hijri-gregorian-converter
            return Stream.of( //
                Arguments.of(LocalDate.of(1209, Month.JANUARY.getValue(), 1), LocalDate.of(1794, Month.JULY.getValue(), 29)), // non-proleptic year
                Arguments.of(LocalDate.of(800, Month.FEBRUARY.getValue(), 10), LocalDate.of(1397, Month.NOVEMBER.getValue(), 10)), // non-proleptic year
                Arguments.of(LocalDate.of(1437, Month.FEBRUARY.getValue(), 1), LocalDate.of(2015, Month.NOVEMBER.getValue(), 14)));
        }

    }

    private static class MuslimDatesWithoutNonProlepticYearsArguments implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
            // http://www.islamicity.org/hijri-gregorian-converter
            return Stream.of( //
                Arguments.of(LocalDate.of(1437, Month.FEBRUARY.getValue(), 1), LocalDate.of(2015, Month.NOVEMBER.getValue(), 13)));
        }
    }

    private static class MuslimDatesOnlyNonProlepticYearsArguments implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
            // http://www.islamicity.org/hijri-gregorian-converter
            return Stream.of( //
                Arguments.of(LocalDate.of(1209, Month.JANUARY.getValue(), 1)), // non-proleptic year
                Arguments.of(LocalDate.of(800, Month.FEBRUARY.getValue(), 10)) // non-proleptic year
            );
        }
    }

}
