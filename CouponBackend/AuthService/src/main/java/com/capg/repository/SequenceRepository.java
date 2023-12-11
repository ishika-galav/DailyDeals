package com.capg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capg.entity.Sequence;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, String> {

}
