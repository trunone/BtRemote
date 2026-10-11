package com.atharok.btremote.domain.entities.remoteInput

import androidx.annotation.StringRes
import com.atharok.btremote.R
import com.atharok.btremote.domain.entities.remoteInput.keyboard.KeyboardKey

enum class RemoteButtonProperties(
    val input: ByteArray,
    @param:StringRes val stringRes: Int
) {

    // Navigation

    MenuButton(
        input = byteArrayOf(0x40, 0x00),
        stringRes = R.string.menu
    ),

    MenuPickButton(
        input = byteArrayOf(0x41, 0x00),
        stringRes = R.string.pick
    ),

    MenuUpButton(
        input = byteArrayOf(0x42, 0x00),
        stringRes = R.string.up
    ),

    MenuDownButton(
        input = byteArrayOf(0x43, 0x00),
        stringRes = R.string.down
    ),

    MenuLeftButton(
        input = byteArrayOf(0x44, 0x00),
        stringRes = R.string.left
    ),

    MenuRightButton(
        input = byteArrayOf(0x45, 0x00),
        stringRes = R.string.right
    ),

    // Multimedia

    PlayPauseButton(
        input = byteArrayOf(0xCD.toByte(), 0x00),
        stringRes = R.string.play_pause
    ),

    PlayButton(
        input = byteArrayOf(0xB0.toByte(), 0x00),
        stringRes = R.string.play
    ),

    PauseButton(
        input = byteArrayOf(0xB1.toByte(), 0x00),
        stringRes = R.string.pause
    ),

    StopButton(
        input = byteArrayOf(0xB7.toByte(), 0x00),
        stringRes = R.string.stop
    ),

    RepeatButton(
        input = byteArrayOf(0xBC.toByte(), 0x00),
        stringRes = R.string.repeat
    ),

    PreviousTrackButton(
        input = byteArrayOf(0xB6.toByte(), 0x00),
        stringRes = R.string.previous_track
    ),

    NextTrackButton(
        input = byteArrayOf(0xB5.toByte(), 0x00),
        stringRes = R.string.next_track
    ),

    RewindButton(
        input = byteArrayOf(0xB4.toByte(), 0x00),
        stringRes = R.string.rewind
    ),

    ForwardButton(
        input = byteArrayOf(0xB3.toByte(), 0x00),
        stringRes = R.string.forward
    ),

    ClosedCaptionsButton(
        input = byteArrayOf(0x61, 0x00),
        stringRes = R.string.closed_captions
    ),

    // Volume

    VolumeUpButton(
        input = byteArrayOf(0xE9.toByte(), 0x00),
        stringRes = R.string.volume_up
    ),

    VolumeDownButton(
        input = byteArrayOf(0xEA.toByte(), 0x00),
        stringRes = R.string.volume_down
    ),

    VolumeMuteButton(
        input = byteArrayOf(0xE2.toByte(), 0x00),
        stringRes = R.string.mute
    ),

    // Brightness

    BrightnessUpButton(
        input = byteArrayOf(0x6F.toByte(), 0x00),
        stringRes = R.string.brightness_up
    ),

    BrightnessDownButton(
        input = byteArrayOf(0x70.toByte(), 0x00),
        stringRes = R.string.brightness_down
    ),

    // Channel

    ChannelUpButton(
        input = byteArrayOf(0x9C.toByte(), 0x00),
        stringRes = R.string.next_channel
    ),

    ChannelDownButton(
        input = byteArrayOf(0x9D.toByte(), 0x00),
        stringRes = R.string.previous_channel
    ),

    // Others

    HomeButton(
        input = byteArrayOf(0x23, 0x02),
        stringRes = R.string.home
    ),

    BackButton(
        input = byteArrayOf(0x24, 0x02),
        stringRes = R.string.back
    ),

    PowerButton(
        input = byteArrayOf(0x30, 0x00),
        stringRes = R.string.power
    ),

    RedMenuButton(
        input = byteArrayOf(0x69, 0x00),
        stringRes = R.string.red_menu_button
    ),

    GreenMenuButton(
        input = byteArrayOf(0x6A, 0x00),
        stringRes = R.string.green_menu_button
    ),

    BlueMenuButton(
        input = byteArrayOf(0x6B, 0x00),
        stringRes = R.string.blue_menu_button
    ),

    YellowMenuButton(
        input = byteArrayOf(0x6C, 0x00),
        stringRes = R.string.yellow_menu_button
    ),

    // Keyboard

    KeyboardEnterButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_ENTER.byte),
        stringRes = R.string.enter
    ),

    KeyboardBackspaceButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_DELETE.byte),
        stringRes = R.string.delete
    ),

    KeyboardSpaceBarButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_SPACE_BAR.byte),
        stringRes = R.string.space_bar
    ),

    KeyboardUpButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_UP_ARROW.byte),
        stringRes = R.string.up
    ),

    KeyboardDownButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_DOWN_ARROW.byte),
        stringRes = R.string.down
    ),

    KeyboardLeftButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_LEFT_ARROW.byte),
        stringRes = R.string.left
    ),

    KeyboardRightButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_RIGHT_ARROW.byte),
        stringRes = R.string.right
    ),

    KeyboardPrintScreenButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_PRINT_SCREEN.byte),
        stringRes = R.string.screenshot
    ),

    KeyboardTabButton(
        input = byteArrayOf(0x00, KeyboardKey.KEY_TAB.byte),
        stringRes = R.string.keyboard_tab
    ),

    KeyboardPageUp(
        input = byteArrayOf(0x00, KeyboardKey.KEY_PAGE_UP.byte),
        stringRes = R.string.page_up
    ),

    KeyboardPageDown(
        input = byteArrayOf(0x00, KeyboardKey.KEY_PAGE_DOWN.byte),
        stringRes = R.string.page_down
    )
}