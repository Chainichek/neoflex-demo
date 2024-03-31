package ru.chainichek.neoflexdemo.domain.usecase;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentIn;
import ru.chainichek.neoflexdemo.domain.dto.VacationPaymentOut;
import ru.chainichek.neoflexdemo.domain.gateway.ProductionCalendarGateway;
import ru.chainichek.neoflexdemo.domain.util.VacationPaymentCalculator;

import java.time.LocalDate;
import java.util.List;

@Component
public class CalculateVacationPaymentUseCase {
    private final ProductionCalendarGateway productionCalendarGateway;
    private final VacationPaymentCalculator calculator;
    private final double minAvgSalary;
    private final int minVacationDays;

    /**<h2>Сценарий использования вычисления суммы отпускных</h2>
     * <p>Перед отправкой данных в калькулятор проводит различные валидации, проверки, а также сравнение (по возможности) с календарём рабочих дней</p>
     * @param productionCalendarGateway - гейтвей для получения календаря рабочих дней
     * @param calculator - калькулятор, вычисляющий сумму отпускных
     * @param minAvgSalary - минимальная средняя заработанная плата за год
     * @param minVacationDays - минимальное количество отпускных дней
     */
    public CalculateVacationPaymentUseCase(final ProductionCalendarGateway productionCalendarGateway,
                                           final @Qualifier("russianVacationPaymentCalculator") VacationPaymentCalculator calculator,
                                           final @Value("${calculator.constraint.min-avg-salary}") double minAvgSalary,
                                           final @Value("${calculator.constraint.min-vacation-days}") int minVacationDays) {
        this.productionCalendarGateway = productionCalendarGateway;
        this.calculator = calculator;
        this.minAvgSalary = minAvgSalary;
        this.minVacationDays = minVacationDays;
    }

    /**<h2>Запуск сценария</h2>
     * <p>Обрабатывает входящее DTO, проводя валидацию в соответствии с политикой приложения</p>
     * @param vacationPaymentIn - DTO информации для вычисления суммы отпускных
     * @return DTO с суммой отпускных
     */
    public VacationPaymentOut execute(final VacationPaymentIn vacationPaymentIn) {
        final double avgSalaryPerYear = vacationPaymentIn.avgSalaryPerYear();
        final int vacationDays = vacationPaymentIn.vacationDays();
        final LocalDate startsFrom = vacationPaymentIn.startsFrom();

        if (avgSalaryPerYear < minAvgSalary) {
            throw new IllegalArgumentException("Average salary per year value is %f which is lesser than minimum salary %f"
                    .formatted(avgSalaryPerYear, minAvgSalary));
        }
        if (vacationDays < minVacationDays) {
            throw new IllegalArgumentException("Vacation days value is %d which is lesser than minimum vacation days %d"
                    .formatted(vacationDays, minVacationDays));
        }

        if (startsFrom != null) {
            final List<LocalDate> realVacationDays = productionCalendarGateway.getAllWorkingDaysBetweenTwoDates(startsFrom, startsFrom.plusDays(vacationDays));
            if (realVacationDays == null) {
                throw new RuntimeException("Error in dates processing from gateway");
            }
            if (realVacationDays.size() < minVacationDays) {
                throw new IllegalArgumentException("Real vacation days value is %d which is lesser than minimum vacation days %d"
                        .formatted(realVacationDays.size(), minVacationDays));
            }
            return new VacationPaymentOut(calculator.calculate(avgSalaryPerYear, realVacationDays.size()));
        }

        return new VacationPaymentOut(calculator.calculate(avgSalaryPerYear, vacationDays));
    }
}
