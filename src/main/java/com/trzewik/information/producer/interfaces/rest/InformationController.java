package com.trzewik.information.producer.interfaces.rest;

import com.trzewik.information.producer.domain.information.InformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @GetMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public InformationDto getInformation(@PathVariable(value = "id") String id) {
        log.info("Get information request with id: [{}]", id);
        return InformationDto.from(informationService.getInformation(id));
    }
}
