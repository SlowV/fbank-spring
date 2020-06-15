package com.fintech.web.controller;

import com.fintech.domain.Person;
import com.fintech.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    PersonService personService;

    @GetMapping
    public String create(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("statusType", Person.StatusType.values());
        return "person/create";
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/add")
    @ResponseBody
    public Person store(@ModelAttribute("person") Person person) {
        return personService.add(person);
    }
}
