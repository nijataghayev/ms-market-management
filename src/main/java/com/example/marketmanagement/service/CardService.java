package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.CardEntity;
import com.example.marketmanagement.dao.repository.CardRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.CardMapper;
import com.example.marketmanagement.model.CardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public List<CardDto> getAllCards() {
        log.info("ActionLog.getAllCards.start");
        List<CardEntity> cardEntityList = cardRepository.findAll();
        List<CardDto> cardDtoList = cardEntityList
                .stream()
                .map(cardMapper::mapToDto)
                .toList();
        log.info("ActionLog.getAllCards.end");
        return cardDtoList;
    }

    public CardDto getCard(Long cardId) {
        log.info("ActionLog.getCard.start cardId {}", cardId);
        var cardEntity = cardRepository
                .findById(cardId)
                .orElseThrow(() -> new NotFoundException(
                        "CARD_NOT_FOUND",
                        String.format("ActionLog.getCard.id %s not found", cardId)
                ));
        CardDto cardDto = cardMapper.mapToDto(cardEntity);
        log.info("ActionLog.getCard.end card {}", cardId);
        return cardDto;
    }
}
