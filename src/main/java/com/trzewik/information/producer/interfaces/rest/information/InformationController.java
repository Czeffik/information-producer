package com.trzewik.information.producer.interfaces.rest.information;

import com.trzewik.information.producer.domain.information.Color;
import com.trzewik.information.producer.domain.information.InformationRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @ApiOperation(value = "Return information selected by id", httpMethod = "GET")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "SUCCESS", response = InformationDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorDto.class),
        @ApiResponse(code = 500, message = "FAILURE", response = ErrorDto.class)
    })
    @GetMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto getInformation(
        @ApiParam(required = true, value = "Id of searched information") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Header which ") @RequestHeader(value = "important") String important
    ) throws InformationRepository.NotFoundException {
        log.info("Get information request with id: [{}]", id);
        final InformationService.GetInformationCommand command = new InformationService.GetInformationCommand(id);
        return InformationDto.from(informationService.get(command));
    }

    @ApiOperation(value = "Create information", httpMethod = "POST")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "CREATED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST", response = ErrorDto.class),
        @ApiResponse(code = 500, message = "FAILURE", response = ErrorDto.class)
    })
    @PostMapping(value = "/information", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InformationDto createInformation(
        @ApiParam(required = true, value = "Information object") @RequestBody InformationForm form,
        @ApiParam(required = true, value = "Header which ") @RequestHeader(value = "important") String important
    ) {
        log.info("Create information request with body: [{}]", form);
        return InformationDto.from(informationService.create(form.toCreateInformationCommand()));
    }

    @ApiOperation(value = "Replace information", httpMethod = "PUT")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "REPLACED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST", response = ErrorDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorDto.class),
        @ApiResponse(code = 500, message = "FAILURE", response = ErrorDto.class)
    })
    @PutMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto replaceInformation(
        @ApiParam(required = true, value = "Id of information to replace") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Information object") @RequestBody InformationForm form,
        @ApiParam(required = true, value = "Header which ") @RequestHeader(value = "important") String important
    ) throws InformationRepository.NotFoundException {
        log.info("Replace information request with id: [{}] and body: [{}]", id, form);
        return InformationDto.from(informationService.replace(form.toReplaceInformationCommand(id)));
    }

    @ApiOperation(value = "Update information", httpMethod = "PATCH")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UPDATED", response = InformationDto.class),
        @ApiResponse(code = 400, message = "BAD REQUEST", response = ErrorDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorDto.class),
        @ApiResponse(code = 500, message = "FAILURE", response = ErrorDto.class)
    })
    @PatchMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto updateInformation(
        @ApiParam(required = true, value = "Id of information to update") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Information object") @RequestBody InformationForm form,
        @ApiParam(required = true, value = "Header which ") @RequestHeader(value = "important") String important
    ) throws InformationRepository.NotFoundException {
        log.info("Update information request with id: [{}] and body: [{}]", id, form);
        return InformationDto.from(informationService.update(form.toUpdateInformationCommand(id)));
    }

    @ApiOperation(value = "Delete information", httpMethod = "DELETE")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "DELETED", response = InformationDto.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorDto.class),
        @ApiResponse(code = 500, message = "FAILURE", response = ErrorDto.class)
    })
    @DeleteMapping(value = "/information/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InformationDto deleteInformation(
        @ApiParam(required = true, value = "Id of information to delete") @PathVariable(value = "id") String id,
        @ApiParam(required = true, value = "Header which ") @RequestHeader(value = "important") String important
    ) throws InformationRepository.NotFoundException {
        log.info("Delete information request with id: [{}]", id);
        InformationService.DeleteInformationCommand command = new InformationService.DeleteInformationCommand(id);
        return InformationDto.from(informationService.delete(command));
    }

    @ExceptionHandler(value = {InformationRepository.NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(InformationRepository.NotFoundException ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDto response = new ErrorDto(message, status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleBadRequest(NullPointerException ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorDto response = new ErrorDto(message, status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handlInternalServerError(Exception ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDto response = new ErrorDto(message, status);
        return new ResponseEntity<>(response, status);
    }

    @Data
    static class InformationForm {
        private String description;
        private String message;
        private PersonForm person;
        private List<CarForm> cars;

        InformationService.CreateInformationCommand toCreateInformationCommand() {
            return new InformationService.CreateInformationCommand(
                this.description,
                this.message,
                this.person.toPerson(),
                this.cars.stream().map(CarForm::toCar).collect(Collectors.toList())
            );
        }

        InformationService.UpdateInformationCommand toUpdateInformationCommand(String id) {
            return new InformationService.UpdateInformationCommand(
                id,
                this.description,
                this.message,
                this.person.toPerson(),
                this.cars.stream().map(CarForm::toCar).collect(Collectors.toList())
            );
        }

        InformationService.ReplaceInformationCommand toReplaceInformationCommand(String id) {
            return new InformationService.ReplaceInformationCommand(
                id,
                this.description,
                this.message,
                this.person.toPerson(),
                this.cars.stream().map(CarForm::toCar).collect(Collectors.toList())
            );
        }
    }

    @Data
    static class PersonForm {
        private String name;
        private String lastName;

        InformationService.InformationCommand.Person toPerson() {
            return new InformationService.InformationCommand.Person(
                this.name,
                this.lastName
            );
        }
    }

    @Data
    static class CarForm {
        private String brand;
        private String model;
        private Color color;

        InformationService.InformationCommand.Car toCar() {
            return new InformationService.InformationCommand.Car(
                this.brand,
                this.model,
                this.color
            );
        }
    }
}
