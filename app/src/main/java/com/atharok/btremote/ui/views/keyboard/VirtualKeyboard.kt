package com.atharok.btremote.ui.views.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.atharok.btremote.R
import com.atharok.btremote.common.utils.AppIcons
import com.atharok.btremote.common.utils.REMOTE_INPUT_NONE
import com.atharok.btremote.domain.entities.remoteInput.RemoteButtonProperties
import com.atharok.btremote.domain.entities.remoteInput.keyboard.KeyboardKey
import com.atharok.btremote.ui.components.BasicIconButton
import com.atharok.btremote.ui.components.TextNormal
import com.atharok.btremote.ui.components.customButtons.RemoteIconSurfaceButton
import com.atharok.btremote.ui.components.customButtons.TextKeyboardKey
import com.atharok.btremote.ui.theme.surfaceElevationHigh

@Composable
fun VirtualKeyboardModalBottomSheet(
    mustClearInputField: Boolean,
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    sendTextReport: (String, Boolean) -> Unit,
    onShowKeyboardBottomSheetChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    KeyboardModalBottomSheet(
        onShowKeyboardBottomSheetChanged = onShowKeyboardBottomSheetChanged,
        windowInsets = WindowInsets.ime,
        modifier = modifier
    ) {
        val focusRequester = remember { FocusRequester() }
        val textState = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        StatelessKeyboardView(
            mustClearInputField = mustClearInputField,
            focusRequester = focusRequester,
            text = textState.value,
            onTextChange = {
                textState.value = it
            },
            sendKeyboardKeyReport = sendKeyboardKeyReport,
            sendTextReport = sendTextReport,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .padding(bottom = dimensionResource(id = R.dimen.padding_max))
        )
    }
}

@Composable
private fun StatelessKeyboardView(
    mustClearInputField: Boolean,
    focusRequester: FocusRequester,
    text: String,
    onTextChange: (String) -> Unit,
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    sendTextReport: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(4f)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        sendTextReport(text, true)
                        if(mustClearInputField) {
                            onTextChange("")
                        }
                    }
                )
            )

            BasicIconButton(
                onClick = {
                    sendTextReport(text, false)
                    if(mustClearInputField) {
                        onTextChange("")
                    }
                },
                icon = AppIcons.Send,
                contentDescription = stringResource(id = R.string.send),
                modifier = Modifier.weight(1f).fillMaxWidth(),
                shape = CircleShape
            )

        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            AdditionalKeyboardKeysLayout(
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdditionalKeyboardKeysLayout(
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    modifier: Modifier = Modifier,
    spaceBetweenButtons: Dp = dimensionResource(id = R.dimen.padding_small)
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { TextNormal(text = stringResource(id = R.string.special_keys)) }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { TextNormal(text = stringResource(id = R.string.function_keys)) }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = { TextNormal(text = stringResource(id = R.string.combination_keys)) }
            )
        }

        when (selectedTabIndex) {
            0 -> SpecialKeysLayout(
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                spaceBetweenButtons = spaceBetweenButtons
            )
            1 -> FunctionKeysLayout(
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                spaceBetweenButtons = spaceBetweenButtons
            )
            2 -> CombinationKeysLayout(
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                spaceBetweenButtons = spaceBetweenButtons
            )
        }
    }
}

@Composable
private fun SpecialKeysLayout(
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    spaceBetweenButtons: Dp
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton(
                text = stringResource(id = R.string.escape),
                input = byteArrayOf(0x00, KeyboardKey.KEY_ESCAPE.byte),
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardTabButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardPageUp,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardPageDown,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardPrintScreenButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardSpaceBarButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(3f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardBackspaceButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1.5f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardEnterButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1.5f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardLeftButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardUpButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardDownButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )

            VirtualKeyboardButton(
                properties = RemoteButtonProperties.KeyboardRightButton,
                sendKeyboardKeyReport = sendKeyboardKeyReport,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FunctionKeysLayout(
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    spaceBetweenButtons: Dp
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton("F1", byteArrayOf(0x00, KeyboardKey.KEY_F1.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F2", byteArrayOf(0x00, KeyboardKey.KEY_F2.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F3", byteArrayOf(0x00, KeyboardKey.KEY_F3.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F4", byteArrayOf(0x00, KeyboardKey.KEY_F4.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F5", byteArrayOf(0x00, KeyboardKey.KEY_F5.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F6", byteArrayOf(0x00, KeyboardKey.KEY_F6.byte), sendKeyboardKeyReport, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton("F7", byteArrayOf(0x00, KeyboardKey.KEY_F7.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F8", byteArrayOf(0x00, KeyboardKey.KEY_F8.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F9", byteArrayOf(0x00, KeyboardKey.KEY_F9.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F10", byteArrayOf(0x00, KeyboardKey.KEY_F10.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F11", byteArrayOf(0x00, KeyboardKey.KEY_F11.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("F12", byteArrayOf(0x00, KeyboardKey.KEY_F12.byte), sendKeyboardKeyReport, Modifier.weight(1f))
        }
    }
}

@Composable
private fun CombinationKeysLayout(
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    spaceBetweenButtons: Dp
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton("Ctrl + A", byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_3_KEY_00.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Ctrl + C", byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_03.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Ctrl + V", byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_04.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Ctrl + X", byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_02.byte), sendKeyboardKeyReport, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton("Ctrl + Z", byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_01.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Alt + Tab", byteArrayOf(KeyboardKey.KEY_ALT.byte, KeyboardKey.KEY_TAB.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Alt + F4", byteArrayOf(KeyboardKey.KEY_ALT.byte, KeyboardKey.KEY_F4.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Win", byteArrayOf(KeyboardKey.KEY_META_LEFT.byte, 0x00), sendKeyboardKeyReport, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            VirtualTextKeyboardButton("Win + D", byteArrayOf(KeyboardKey.KEY_META_LEFT.byte, KeyboardKey.ROW_3_KEY_02.byte), sendKeyboardKeyReport, Modifier.weight(1f))
            VirtualTextKeyboardButton("Ctrl + Alt + Del", byteArrayOf((KeyboardKey.KEY_CTRL_LEFT.byte + KeyboardKey.KEY_ALT.byte).toByte(), KeyboardKey.KEY_DELETE.byte), sendKeyboardKeyReport, Modifier.weight(1.5f))
        }
    }
}

@Composable
private fun VirtualKeyboardButton(
    properties: RemoteButtonProperties,
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    RemoteIconSurfaceButton(
        properties = properties,
        sendKeyReport = sendKeyboardKeyReport,
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.keyboard_key_corner_radius)),
        elevation = surfaceElevationHigh(),
        iconFillFraction = 1f,
        iconPadding = dimensionResource(id = R.dimen.padding_medium)
    )
}

@Composable
private fun VirtualTextKeyboardButton(
    text: String,
    input: ByteArray,
    sendKeyboardKeyReport: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    TextKeyboardKey(
        text = text,
        touchDown = { sendKeyboardKeyReport(input) },
        touchUp = { sendKeyboardKeyReport(REMOTE_INPUT_NONE) },
        modifier = modifier,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.keyboard_key_corner_radius)),
        elevation = surfaceElevationHigh()
    )
}
