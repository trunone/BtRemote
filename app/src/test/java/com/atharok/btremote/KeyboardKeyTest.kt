package com.atharok.btremote

import com.atharok.btremote.domain.entities.remoteInput.keyboard.KeyboardKey
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class KeyboardKeyTest {

    @Test
    fun testFunctionKeysByteValues() {
        assertEquals(0x3A.toByte(), KeyboardKey.KEY_F1.byte)
        assertEquals(0x3B.toByte(), KeyboardKey.KEY_F2.byte)
        assertEquals(0x3C.toByte(), KeyboardKey.KEY_F3.byte)
        assertEquals(0x3D.toByte(), KeyboardKey.KEY_F4.byte)
        assertEquals(0x3E.toByte(), KeyboardKey.KEY_F5.byte)
        assertEquals(0x3F.toByte(), KeyboardKey.KEY_F6.byte)
        assertEquals(0x40.toByte(), KeyboardKey.KEY_F7.byte)
        assertEquals(0x41.toByte(), KeyboardKey.KEY_F8.byte)
        assertEquals(0x42.toByte(), KeyboardKey.KEY_F9.byte)
        assertEquals(0x43.toByte(), KeyboardKey.KEY_F10.byte)
        assertEquals(0x44.toByte(), KeyboardKey.KEY_F11.byte)
        assertEquals(0x45.toByte(), KeyboardKey.KEY_F12.byte)
    }

    @Test
    fun testSpecialKeysByteValues() {
        assertEquals(0x29.toByte(), KeyboardKey.KEY_ESCAPE.byte)
        assertEquals(0x2B.toByte(), KeyboardKey.KEY_TAB.byte)
        assertEquals(0x2A.toByte(), KeyboardKey.KEY_DELETE.byte)
        assertEquals(0x28.toByte(), KeyboardKey.KEY_ENTER.byte)
        assertEquals(0x2C.toByte(), KeyboardKey.KEY_SPACE_BAR.byte)
        assertEquals(0x46.toByte(), KeyboardKey.KEY_PRINT_SCREEN.byte)
        assertEquals(0x4B.toByte(), KeyboardKey.KEY_PAGE_UP.byte)
        assertEquals(0x4E.toByte(), KeyboardKey.KEY_PAGE_DOWN.byte)
    }

    @Test
    fun testCombinationKeyInputs() {
        val ctrlA = byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_3_KEY_00.byte)
        assertArrayEquals(byteArrayOf(0x01, 0x04), ctrlA)

        val ctrlC = byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_03.byte)
        assertArrayEquals(byteArrayOf(0x01, 0x06), ctrlC)

        val ctrlV = byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_04.byte)
        assertArrayEquals(byteArrayOf(0x01, 0x19), ctrlV)

        val ctrlX = byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_02.byte)
        assertArrayEquals(byteArrayOf(0x01, 0x1B), ctrlX)

        val ctrlZ = byteArrayOf(KeyboardKey.KEY_CTRL_LEFT.byte, KeyboardKey.ROW_4_KEY_01.byte)
        assertArrayEquals(byteArrayOf(0x01, 0x1D), ctrlZ)

        val altTab = byteArrayOf(KeyboardKey.KEY_ALT.byte, KeyboardKey.KEY_TAB.byte)
        assertArrayEquals(byteArrayOf(0x04, 0x2B), altTab)

        val altF4 = byteArrayOf(KeyboardKey.KEY_ALT.byte, KeyboardKey.KEY_F4.byte)
        assertArrayEquals(byteArrayOf(0x04, 0x3D), altF4)

        val win = byteArrayOf(KeyboardKey.KEY_META_LEFT.byte, 0x00)
        assertArrayEquals(byteArrayOf(0x08, 0x00), win)

        val winD = byteArrayOf(KeyboardKey.KEY_META_LEFT.byte, KeyboardKey.ROW_3_KEY_02.byte)
        assertArrayEquals(byteArrayOf(0x08, 0x07), winD)

        val ctrlAltDel = byteArrayOf((KeyboardKey.KEY_CTRL_LEFT.byte + KeyboardKey.KEY_ALT.byte).toByte(), KeyboardKey.KEY_DELETE.byte)
        assertArrayEquals(byteArrayOf(0x05, 0x2A), ctrlAltDel)
    }
}
