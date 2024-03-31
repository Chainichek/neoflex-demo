package ru.chainichek.neoflexdemo.web.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentOut;

import java.time.LocalDate;

public interface CalculatorApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = """
                                     Принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
                                     При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться расчёт отпускных с учётом праздников и выходных.
                                    """,
                            content = @Content(
                                    array =
                                    @ArraySchema(
                                            schema =
                                            @Schema(implementation = VacationPaymentOut.class)))),
                    @ApiResponse(responseCode = "400",
                            description = "Ошибка вызвана некорректным водом пользователя."),
                    @ApiResponse(responseCode = "500",
                            description = "Произошла непредвиденная ошибка.")
            }
    )
    @GetMapping("/calculate")
    ResponseEntity<VacationPaymentOut> calculate(@RequestParam @NotNull double averageSalaryPerYear,
                                                 @RequestParam @NotNull int vacationDays,
                                                 @RequestParam @Nullable LocalDate startsFrom);
}
