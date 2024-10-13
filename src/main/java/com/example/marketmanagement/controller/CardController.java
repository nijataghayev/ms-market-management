package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.CardDto;
import com.example.marketmanagement.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @Operation(summary = "Get all cards", description = "Returns a list of all cards.")
    @GetMapping
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @Operation(summary = "Get card by ID", description = "Returns the card details for the given card ID.")
    @GetMapping("/{cardId}")
    public CardDto getCard(@PathVariable Long cardId) {
        return cardService.getCard(cardId);
    }
}
