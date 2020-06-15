package com.fintech.web.rest;

import com.fintech.domain.Person;
import com.fintech.service.PersonService;
import com.fintech.web.rest.errors.PersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/person")
public class PersonEndPoint {

    @Autowired
    PersonService personService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Person> persons() {
        return personService.getAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person show(@PathVariable("id") Long id) {
        Person person = personService.getById(id);
        if (person == null) {
            throw new PersonException(String.format("Không tìm thấy Person nào với id là %s", id));
        }
        return person;
    }

}
