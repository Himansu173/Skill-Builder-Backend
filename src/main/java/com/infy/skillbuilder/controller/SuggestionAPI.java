package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.SuggestionsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.SuggestionService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/suggestionapi")
public class SuggestionAPI {
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private Environment environment;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewSuggestion(@RequestBody @Valid SuggestionsDTO suggestionsDTO)
            throws SkillBuilderException {
        Integer id = suggestionService.registerNewSuggestion(suggestionsDTO);
        return new ResponseEntity<>(environment.getProperty("API.SUGGESTION_REGISTRATION_SUCCESS") + id,
                HttpStatus.CREATED);
    }

    @GetMapping("/getPopularSuggestions")
    public ResponseEntity<List<SuggestionsDTO>> getPopularSuggestions() throws SkillBuilderException {
        return new ResponseEntity<>(suggestionService.getPopularSuggestions(), HttpStatus.OK);
    }

    @PutMapping("/updateSuggestionStatus/{id}/{status}")
    public ResponseEntity<String> updateSuggestionStatus(@PathVariable Integer id,
            @PathVariable ReportAndSuggestionStatus status) throws SkillBuilderException {
        suggestionService.updateSuggestionStatus(id, status);
        return new ResponseEntity<>(environment.getProperty("API.REPORT_UPDATE_SUCCESS"), HttpStatus.OK);
    }

    @GetMapping("/getAllSuggestions")
    public ResponseEntity<List<SuggestionsDTO>> getAllSuggestions() throws SkillBuilderException {
        return new ResponseEntity<>(suggestionService.getAllSuggestions(), HttpStatus.OK);
    }

    @GetMapping("/findByUserIdAndUserType/{id}/{user}")
    public ResponseEntity<List<SuggestionsDTO>> findByUserIdAndUserType(@PathVariable Integer id,
            @PathVariable UserType user) throws SkillBuilderException {
        return new ResponseEntity<>(suggestionService.findByUserIdAndUserType(id, user), HttpStatus.OK);
    }
}