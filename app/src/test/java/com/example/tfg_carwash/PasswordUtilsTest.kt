package com.example.tfg_carwash

import com.example.tfg_carwash.utils.PasswordUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PasswordUtilsTest {
    @Test
    fun hash_isStableAndNotPlainText() {
        val password = "CarWash2026!"

        val hashed = PasswordUtils.hash(password)

        assertEquals(64, hashed.length)
        assertNotEquals(password, hashed)
        assertEquals(PasswordUtils.hash(password), hashed)
    }
}
