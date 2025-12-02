package br.com.saudepraja.domain.service.user.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SaudePrajaUtils {

    public static final String dayMonthYearBRType = "dd/MM/YYYY";

    public static LocalDate stringToLocalDate(String data, String format) {
        Objects.requireNonNull(data, "Please provide Data");
        Objects.requireNonNull(format, "Please provide format");
        return LocalDate.parse(data, DateTimeFormatter.ofPattern(format));
    }

}
