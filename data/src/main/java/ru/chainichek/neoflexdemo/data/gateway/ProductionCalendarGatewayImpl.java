package ru.chainichek.neoflexdemo.data.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.chainichek.neoflexdemo.domain.gateway.ProductionCalendarGateway;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** <h2>Реализация гейтвея получения производственного календаря</h2>
 * <p>Использует API российского производственного календаря. Требует токен доступа. <a href="https://production-calendar.ru/manual">ссылка</a></p>
 */
@Repository
public class ProductionCalendarGatewayImpl implements ProductionCalendarGateway {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String token;
    private final String url;

    public ProductionCalendarGatewayImpl(final RestTemplate restTemplate,
                                         final ObjectMapper objectMapper,
                                         final @Value("${calculator.production-calendar.token}") String token,
                                         final @Value("${calculator.production-calendar.url}") String url) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.token = token;
        this.url = url;
    }

    @Override
    @SneakyThrows
    public List<LocalDate> getAllWorkingDaysBetweenTwoDates(final LocalDate startsFrom,
                                                            final LocalDate endsOn) {
        final String requestUrl = "%s/%s/ru/%s-%s/json".formatted(url,
                token,
                startsFrom.format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                endsOn.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        final ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        final JsonNode root = objectMapper.readTree(response.getBody());
        final JsonNode status = root.findValue("status");
        final JsonNode days = root.findValue("days");
        if (days == null ||
            !days.isArray() ||
            status == null ||
            Objects.equals(status.asText(), "error")) {
            return null;
        }
        final List<LocalDate> workingDays = new ArrayList<>();
        for (final JsonNode day:
                days) {
            final JsonNode typeId = day.findValue("type_id");
            final JsonNode date = day.findValue("date");
            if (typeId == null ||
                !Objects.equals(typeId.asText(), "1") ||
                date == null ||
                date.asText().isBlank()) {
                continue;
            }
            final String[] dateInfo = date.asText().split("\\.");
            if (dateInfo.length != 3) {
                continue;
            }
            workingDays.add(LocalDate.of(Integer.parseInt(dateInfo[2]),
                    Integer.parseInt(dateInfo[1]),
                    Integer.parseInt(dateInfo[0])));
        }

        return workingDays;
    }
}
