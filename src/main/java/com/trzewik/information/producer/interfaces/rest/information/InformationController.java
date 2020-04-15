package com.trzewik.information.producer.interfaces.rest.information;

import com.trzewik.information.producer.domain.information.InformationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @ApiOperation(value = "Return information selected by id", httpMethod = "GET")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "SUCCESS", response = InformationDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "FAILURE")
    })
    @GetMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto getInformation(
        @ApiParam(required = true, value = "Id of searched information") @PathVariable(value = "id") String id
    ) {
        log.info("Get information request with id: [{}]", id);
        return InformationDto.from(informationService.getInformation(id));
    }

    @ApiOperation(value = "Create information", httpMethod = "POST")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "CREATED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 500, message = "FAILURE")
    })
    @PostMapping(value = "/information", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InformationDto createInformation(
        @ApiParam(required = true, value = "Information object") @NonNull @RequestBody InformationForm form
    ) {
        log.info("Create information request with body: [{}]", form);

        return InformationDto.from(informationService.getInformation(form.getId()));
    }

    @ApiOperation(value = "Replace information", httpMethod = "PUT")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "REPLACED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "FAILURE")
    })
    @PutMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto updateInformation(
        @ApiParam(required = true, value = "Id of information to replace") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Information object") @NonNull @RequestBody InformationForm form
    ) {
        log.info("Replace information request with id: [{}] and body: [{}]", id, form);

        return InformationDto.from(informationService.getInformation(id));
    }

    @ApiOperation(value = "Update information", httpMethod = "PATCH")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UPDATED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "FAILURE")
    })
    @PatchMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto patchInformation(
        @ApiParam(required = true, value = "Id of information to update") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Information object") @NonNull @RequestBody InformationForm form
    ) {
        log.info("Update information request with id: [{}] and body: [{}]", id, form);

        return InformationDto.from(informationService.getInformation(id));
    }

    @ApiOperation(value = "Delete information", httpMethod = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "DELETED", response = InformationDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND"),
        @ApiResponse(code = 500, message = "FAILURE")
    })
    @DeleteMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto deleteInformation(
        @ApiParam(required = true, value = "Id of information to delete") @PathVariable(value = "id") String id
    ) {
        log.info("Delete information request with id: [{}]", id);
        return InformationDto.from(informationService.getInformation(id));
    }


    @Data
    public static class InformationForm {
        private String id;
        private String description;
        private String message;
        private PersonForm person;
        private List<CarForm> cars;
    }

    @Data
    public static class PersonForm {
        private String name;
        private String lastName;
    }

    @Data
    public static class CarForm {
        private String brand;
        private String model;
        private String color;
    }
}
