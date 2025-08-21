package com.innovator.journal.Entity;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_cache")
@Getter
public class AppCacheConfig {
    private String key;
    private String value;
}
