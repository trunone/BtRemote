package com.atharok.btremote.domain.entities.remoteInput.keyboard.advancedKeyboard

import com.atharok.btremote.domain.entities.remoteInput.RemoteButtonProperties

sealed class AdvancedKeyboardKey {
    abstract val byte: Byte
    abstract val weight: Float
}

data class CharacterAdvancedKeyboardKey(
    override val byte: Byte,
    override val weight: Float,
    val text: String,
    val textSecondary: String? = null,
    val textTertiary: String? = null
): AdvancedKeyboardKey()

data class TextAdvancedKeyboardKey(
    override val byte: Byte,
    override val weight: Float,
    val text: String
): AdvancedKeyboardKey()

data class IconAdvancedKeyboardKey(
    val properties: RemoteButtonProperties,
    override val weight: Float
): AdvancedKeyboardKey() {
    override val byte: Byte = properties.input[1]
}