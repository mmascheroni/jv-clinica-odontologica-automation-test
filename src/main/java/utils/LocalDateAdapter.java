package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter {

//    public LocalDate stringToLocalDateResTurno(String fechaYHora, String pattern) {
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String fechaIngresoString = localDate.format(formatter);
//    }

    public String localDateToString(LocalDate fecha, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String fechaString = fecha.format(formatter);

        return fechaString;
    }
}
