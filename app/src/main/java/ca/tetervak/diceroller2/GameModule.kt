package ca.tetervak.diceroller2

import ca.tetervak.diceroller2.model.DiceGame
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GameModule {

    @Provides
    fun provideDiceGame(): DiceGame = DiceGame()
}