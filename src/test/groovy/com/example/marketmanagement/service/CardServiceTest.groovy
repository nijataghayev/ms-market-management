package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.CardEntity
import com.example.marketmanagement.dao.repository.CardRepository
import com.example.marketmanagement.mapper.CardMapper
import com.example.marketmanagement.model.CardDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

/**
 * @author: nijataghayev
 */

class CardServiceTest extends Specification {
    private EnhancedRandom random
    private CardService cardService
    private CardRepository cardRepository
    private CardMapper cardMapper

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        cardRepository = Mock()
        cardMapper = Mock()
        cardService = new CardService(cardRepository, cardMapper)
    }

    def "GetAllCards"() {
        given:
        def cardEntities = random.objects(CardEntity, 5).toList()
        def cardDtos = cardEntities.collect { random.nextObject(CardDto) }
        cardRepository.findAll() >> cardEntities
        cardEntities.eachWithIndex { entity, i ->
            cardMapper.mapToDto(entity) >> cardDtos[i]
        }

        when:
        def result = cardService.getAllCards()

        then:
        result.size() == cardDtos.size()
        result == cardDtos
    }

    def "GetCard"() {
        given:
        def cardId = 1L
        def cardEntity = random.nextObject(CardEntity)
        def cardDto = random.nextObject(CardDto)

        when:
        def result = cardService.getCard(cardId)

        then:
        1 * cardRepository.findById(cardId) >> Optional.of(cardEntity)
        1 * cardMapper.mapToDto(cardEntity) >> cardDto

        result == cardDto
    }
}