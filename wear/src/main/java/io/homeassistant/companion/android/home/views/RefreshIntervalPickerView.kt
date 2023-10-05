package io.homeassistant.companion.android.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.rememberPickerState
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.composables.picker.toRotaryScrollAdapter
import com.google.android.horologist.compose.rotaryinput.rotaryWithSnap
import io.homeassistant.companion.android.theme.wearColorScheme
import io.homeassistant.companion.android.util.intervalToString
import io.homeassistant.companion.android.views.ListHeader
import io.homeassistant.companion.android.common.R as R

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun RefreshIntervalPickerView(
    currentInterval: Int,
    onSelectInterval: (Int) -> Unit
) {
    val options = listOf(0, 60, 2 * 60, 5 * 60, 10 * 60, 15 * 60, 30 * 60, 60 * 60, 2 * 60 * 60, 5 * 60 * 60, 10 * 60 * 60, 24 * 60 * 60)
    val initialIndex = options.indexOf(currentInterval)
    val state = rememberPickerState(
        initialNumberOfOptions = options.size,
        initiallySelectedOption = if (initialIndex != -1) initialIndex else 0,
        repeatItems = true
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListHeader(R.string.refresh_interval)
        Picker(
            state = state,
            contentDescription = stringResource(R.string.refresh_interval),
            modifier = Modifier
                .weight(1f)
                .padding(all = 8.dp)
                .rotaryWithSnap(state.toRotaryScrollAdapter())
        ) {
            Text(
                intervalToString(LocalContext.current, options[it]),
                fontSize = 24.sp,
                color = if (it != this.selectedOption) wearColorScheme.onBackground else wearColorScheme.primary
            )
        }
        Button(
            onClick = { onSelectInterval(options[state.selectedOption]) },
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier,
            icon = {
                Icon(Icons.Filled.Check, stringResource(id = R.string.save))
            }
        ) { }
    }
}

@Preview(device = WearDevices.LARGE_ROUND)
@Composable
private fun PreviewRefreshIntervalPickerView() {
    CompositionLocalProvider {
        RefreshIntervalPickerView(currentInterval = 10) {}
    }
}
