package ca.tetervak.diceroller2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.tetervak.diceroller2.model.DiceGame
import ca.tetervak.diceroller2.model.RollData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val game: DiceGame
): ViewModel() {

    // in a perfect app, this game object should be in the data layer
    private val game = DiceGame(numberOfDice = 3)

    private val _liveRollData = MutableLiveData<RollData?>(game.rollData)
    val liveRollData: LiveData<RollData?> = _liveRollData

    fun roll() {
        game.roll()
        _liveRollData.value = game.rollData
    }

    fun reset() {
        game.reset()
        _liveRollData.value = game.rollData
    }
}