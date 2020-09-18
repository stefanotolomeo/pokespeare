package com.company.pokespeare.pokemon.logic;

import com.company.pokespeare.pokemon.dto.ShakespeareDTO;
import org.springframework.stereotype.Service;

@Service
public class ShakespeareTranslationSelector {

	public String selectTranslation(ShakespeareDTO shakespeareDTO){
		try{
			return shakespeareDTO.getContents().getTranslated();
		} catch (Exception e){
			throw new RuntimeException("Cannot select a shakespeare translation from ShakespeareDTO", e);
		}
	}
}
