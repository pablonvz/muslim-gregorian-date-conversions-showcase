package com.pablocastelnovo.muslim_gregorian_date_conversions_showcase;

import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoChronology;

public class ConvertibleDate {
    final private java.time.LocalDate date;

    public ConvertibleDate(java.time.LocalDate date) {
        this.date = date;
    }

    public LocalDate toGregorianJodaTime() {
        final org.joda.time.LocalTime midday = //
            new org.joda.time.LocalTime(12, 00, 00, 00, org.joda.time.chrono.IslamicChronology.getInstance());

        final org.joda.time.Instant muslimInstant = //
            new org.joda.time.LocalDate(date.getYear(), //
                date.getMonthValue(), //
                date.getDayOfMonth(), //
                org.joda.time.chrono.IslamicChronology.getInstance()) //
                    .toDateTime(midday, org.joda.time.DateTimeZone.UTC) //
                    .toInstant();

        final org.joda.time.DateTime gregorianDateTime = muslimInstant.toDateTime(org.joda.time.chrono.GregorianChronology.getInstance());

        final java.time.Instant javaInstant = java.time.Instant.ofEpochMilli(gregorianDateTime.getMillis());

        return java.time.LocalDateTime.ofInstant(javaInstant, java.time.ZoneOffset.UTC) //
            .toLocalDate();
    }

    public LocalDate toGregorianJavaTime() {
        // java.time doesn't support non-proleptic Muslim years
        final HijrahDate hijrahDate = //
            HijrahChronology.INSTANCE.date(HijrahEra.AH, date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        return IsoChronology.INSTANCE.date(hijrahDate);
    }

}
