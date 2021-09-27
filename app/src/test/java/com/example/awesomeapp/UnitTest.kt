package com.example.awesomeapp

import com.example.awesomeapp.base.BaseViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
     @Test
    fun test_is_online_state(){
        val baseViewModel = BaseViewModel()
        assertTrue(baseViewModel.internetIsConnected())
    }

    @Test
    fun test_compare_connection(){
        val baseViewModel= BaseViewModel()
        assertEquals(baseViewModel.internetIsConnected(), Runtime.getRuntime().exec("ping -c 1 192.168.18.1").waitFor() == 0)
    }
}