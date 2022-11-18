package com.wagarcdev.der

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class ExampleUnitTest {
    @Test
    fun `this is a dummy test`() {
        val expected = 10
        val actual = 5 + 5

        assertEquals(expected, actual)
    }
}