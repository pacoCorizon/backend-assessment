/*
 * Copyright 2024 Autumn Framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.paymeter.assessment.services;

import io.paymeter.assessment.Ticket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author oem
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = CustomCalculatorAdapterConfig.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CustomCalculatorAdapter.class})
public class CustomCalculatorAdapterTest {

    @Autowired
    private CustomCalculatorAdapter adapter;

    @Test
    public void testingOneHourParking() {

        Assertions.assertNotNull(adapter);
        LocalDateTime from = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        LocalDateTime to = LocalDateTime.now();
        Ticket calculatedTicket = adapter.calculate(Ticket
                .builder()
                .parkingId("P000123")
                .from(from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .to(to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
        log.info("calculated ticket {}", calculatedTicket);
        Assertions.assertEquals(calculatedTicket.getPrice().getAmount(), 2);
    }

}
