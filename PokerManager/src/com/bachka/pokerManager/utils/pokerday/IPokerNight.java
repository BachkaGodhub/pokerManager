package com.bachka.pokerManager.utils.pokerday;

import com.bachka.pokerManager.utils.pokerRound.IPokerRound;

public interface IPokerNight {

	void calcTotalChipsPerPerson();

	IPokerNight addRound(IPokerRound newRound, IPokerRound... values);
}
