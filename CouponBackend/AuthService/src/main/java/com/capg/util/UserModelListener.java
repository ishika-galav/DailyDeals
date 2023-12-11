package com.capg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.capg.dto.CouponDto;

@Component
public class UserModelListener extends AbstractMongoEventListener<CouponDto>{
	
	private SequenceGeneratorService sequenceGenerator;
	
	@Autowired
	public UserModelListener(SequenceGeneratorService seGeneratorService) {
		this.sequenceGenerator = sequenceGenerator;
	}
	
//	@Override
//	public void onBeforeConvert(BeforeConvertEvent<CouponDto> event) {
//		if(event.getSource().getCouponId()< 1) {
//			event.getSource().setCouponId(sequenceGenerator.generateSequence(CouponDto.SEQUENCE_NAME));
//		}
//	}
	

}
