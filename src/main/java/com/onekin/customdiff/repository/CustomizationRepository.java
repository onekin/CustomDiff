package com.onekin.customdiff.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.customdiff.model.Customization;

@Repository
public interface CustomizationRepository extends CrudRepository <Customization, Integer>{
	
	//List <Customization> findCustomizationBy SPL_idSPL(String sPL_idSPL);
	
	List <Customization> findByIdproductrelease(int idProductRelease);
	
	
	
}
