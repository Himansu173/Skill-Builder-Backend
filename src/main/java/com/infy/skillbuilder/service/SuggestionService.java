package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.SuggestionsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface SuggestionService {
    Integer registerNewSuggestion(SuggestionsDTO suggestionsDTO) throws SkillBuilderException;

    List<SuggestionsDTO> getPopularSuggestions() throws SkillBuilderException;

    void updateSuggestionStatus(Integer suggestionId, ReportAndSuggestionStatus status) throws SkillBuilderException;

    List<SuggestionsDTO> getAllSuggestions() throws SkillBuilderException;

    List<SuggestionsDTO> findByUserIdAndUserType(Integer id, UserType user) throws SkillBuilderException;
}