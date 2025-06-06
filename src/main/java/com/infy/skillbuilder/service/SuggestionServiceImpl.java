package com.infy.skillbuilder.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.SuggestionsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Notifications;
import com.infy.skillbuilder.entity.Suggestions;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.NotificationsRepository;
import com.infy.skillbuilder.repository.SuggestionsRepository;

import jakarta.transaction.Transactional;

@Service("suggestionService")
@Transactional
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    private SuggestionsRepository suggestionsRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public Integer registerNewSuggestion(SuggestionsDTO suggestionsDTO) throws SkillBuilderException {
        Suggestions suggestion = mapper.map(suggestionsDTO, Suggestions.class);
        suggestion.setStatus(ReportAndSuggestionStatus.PENDING);
        suggestion.setCreatedAt(LocalDateTime.now());
        return suggestionsRepository.save(suggestion).getSuggestionId();
    }

    @Override
    public List<SuggestionsDTO> getPopularSuggestions() throws SkillBuilderException {
        return mapper.map(suggestionsRepository.getPopularSuggestions().subList(0, 4),
                new TypeToken<List<Suggestions>>() {
                }.getType());
    }

    @Override
    public void updateSuggestionStatus(Integer suggestionId, ReportAndSuggestionStatus status)
            throws SkillBuilderException {
        Suggestions suggestion = suggestionsRepository.findById(suggestionId)
                .orElseThrow(() -> new SkillBuilderException("Service.SUGGESTION_NOT_FOUND"));
        suggestion.setStatus(status);

        Notifications notification = new Notifications();
        notification.setSenderId(10001);
        notification.setSenderType(UserType.ADMIN);
        notification.setReceiverId(suggestion.getUserId());
        notification.setReceiverType(suggestion.getUserType());
        notification.setMessage(
                "The current status of your suggestion on " + suggestion.getSuggestionsType() + " is " + status);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationsRepository.save(notification);
    }

    @Override
    public List<SuggestionsDTO> getAllSuggestions() throws SkillBuilderException {
        Sort sort = Sort.by("createdAt").descending();
        return mapper.map(suggestionsRepository.findAll(sort), new TypeToken<List<SuggestionsDTO>>() {
        }.getType());
    }

    @Override
    public List<SuggestionsDTO> findByUserIdAndUserType(Integer id, UserType user) throws SkillBuilderException {
        return mapper.map(suggestionsRepository.findByUserIdAndUserTypeOrderByCreatedAtDesc(id, user),
                new TypeToken<List<SuggestionsDTO>>() {
                }.getType());
    }

}
