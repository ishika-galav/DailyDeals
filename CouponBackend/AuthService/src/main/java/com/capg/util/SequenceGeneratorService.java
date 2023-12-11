package com.capg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.capg.entity.Sequence;

@Service
public class SequenceGeneratorService {
	
	private MongoOperations mongoOperations;
	
	@Autowired
	public SequenceGeneratorService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
	
	public int generateSequence(String seqName) {
	    Query query = new Query();
	    query.addCriteria(Criteria.where("_id").is(seqName));

	    Update update = new Update().inc("seq", 1);

	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);
	    options.upsert(true);

	    Sequence counter = mongoOperations.findAndModify(query, update, options, Sequence.class);

	    return (counter != null) ? counter.getSeq() : 1;
	}

}
