package utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH));
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }

    public LocalDateTime stringToLocalDateTimeResTurno(String fechaYHora, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime fechaYHoraLDT = LocalDateTime.parse(fechaYHora, formatter);

        return fechaYHoraLDT;
    }

    public String localDateTimeToString(LocalDateTime fechaYHora, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String fechaYHoraLDT = fechaYHora.format(formatter);

        return fechaYHoraLDT;
    }
}
