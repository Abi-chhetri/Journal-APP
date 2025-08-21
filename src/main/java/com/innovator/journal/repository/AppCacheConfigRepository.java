package com.innovator.journal.repository;

import com.innovator.journal.Entity.AppCacheConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppCacheConfigRepository extends MongoRepository<AppCacheConfig,Object> {
}
