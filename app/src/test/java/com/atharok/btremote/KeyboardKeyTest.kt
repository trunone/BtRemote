package com.atharok.btremote

import com.atharok.btremote.domain.entities.remoteInput.keyboard.KeyboardKey
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
        assertEquals(0x49.toByte(), KeyboardKey.KEY_INSERT.byte)
        assertEquals(0x4A.toByte(), KeyboardKey.KEY_HOME.byte)
        assertEquals(0x4D.toByte(), KeyboardKey.KEY_END.byte)
        assertEquals(0x4C.toByte(), KeyboardKey.KEY_FORWARD_DELETE.byte)
        assertEquals(0x39.toByte(), KeyboardKey.KEY_CAPS_LOCK.byte)
    }
}
