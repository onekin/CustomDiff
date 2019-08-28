package com.onekin.customdiff.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.customdiff.model.Customization;

@Transactional
public interface CustomizationDao extends CrudRepository <Customization, Long>{
	
	//List <Customization> findCustomizationBy SPL_idSPL(String sPL_idSPL);
	
}
