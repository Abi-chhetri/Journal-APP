package com.innovator.journal.PojoForApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Quote {
    @JsonProperty("quote")
    private String quote;

    @JsonProperty("author")
    private String auth;

    private String category;
}
