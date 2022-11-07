package ca.tetervak.diceroller2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.tetervak.diceroller2.model.RollData
import ca.tetervak.diceroller2.ui.theme.DiceRoller2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRoller2Theme {
                DiceRollerScreen()
            }
        }
    }
}

@Composable
fun DiceRollerScreen(viewModel: MainViewModel = viewModel()) {
    val rollData: RollData? by viewModel.liveRollData.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.roll_the_dice),
            color = colorResource(R.color.pink_500),
            fontSize = 34.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        rollData?.let { rollData ->
            val list: List<Int> = rollData.values
            DiceImagesRow(list)
            DiceValuesRow(list)
        }
        TotalRow(rollData?.total ?: 0)
        Button(
            onClick = { viewModel.roll() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = stringResource(R.string.roll_button_label))
        }
        if (rollData is RollData) {
            Button(
                onClick = { viewModel.reset() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset_button_label))
            }
        }
    }
}

@Composable
private fun TotalRow(total: Int) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.total_label),
            fontSize = 34.sp
        )
        Text(
            text = total.toString(),
            fontSize = 34.sp,
            color = colorResource(R.color.green_500)
        )
    }
}

@Composable
private fun DiceValuesRow(list: List<Int>) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (value in list) {
            Text(
                text = value.toString(),
                color = colorResource(R.color.deep_purple_500),
                fontSize = 56.sp
            )
        }
    }
}

@Composable
private fun DiceImagesRow(list: List<Int>) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 16.dp)
    ) {
        for (value in list) {
            DiceImage(value)
        }
    }
}

@Composable
private fun DiceImage(value: Int) {
    Image(
        painter = painterResource(diceImageResourceId(value)),
        contentDescription = value.toString(),
        modifier = Modifier.size(80.dp)
    )
}

private fun diceImageResourceId(value: Int) =
    when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

@Preview
@Composable
fun DefaultPreview() {
    DiceRoller2Theme {
        DiceRollerScreen()
    }
}