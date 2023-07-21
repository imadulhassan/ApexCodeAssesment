package com.apex.codeassesment.data.local

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.main.MainActivity
import com.squareup.moshi.Moshi
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class LocalDataSourceTest {

    @Mock
    private  var context: Context?=null

    var localDataSourceTest: LocalDataSource?=null


    @Before
    fun setup() {
        context = Mockito.mock(Context::class.java)
        MainActivity.sharedContext = context
        localDataSourceTest = LocalDataSource( PreferencesManager(), Moshi.Builder().build())
    }

    @Test
    fun saveUserInPref() {
        val user= User.createRandom()
        localDataSourceTest?.saveUserInPreferences(user)

    }

    @Test
    fun loadUserfromPreferences(){
        localDataSourceTest?.saveUserInPreferences(User(  "Test",))
        TestCase.assertNotNull(localDataSourceTest?.loadUserfromPreferences())

    }

    @After
    fun  tearDown(){
         localDataSourceTest=null
         context=null
    }

}