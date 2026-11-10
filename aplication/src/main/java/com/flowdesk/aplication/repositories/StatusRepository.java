package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusRepository extends MongoRepository<Status, String> {
}
