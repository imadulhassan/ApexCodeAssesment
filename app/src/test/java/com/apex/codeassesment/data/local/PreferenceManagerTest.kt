package com.apex.codeassesment.data.local

import android.content.Context
import android.icu.text.CaseMap.Title
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.main.MainActivity
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class PreferenceManagerTest {


    @Mock
    private lateinit var mockContext: Context
    lateinit var preferencesManager: PreferencesManager



    @Before
    fun setup() {
        mockContext = Mockito.mock(Context::class.java)
        MainActivity.sharedContext = mockContext
        preferencesManager = PreferencesManager()


    }

    @Test
    fun SaveUserInPref() {
        val user = User(name = Name("Alex", "Alex", "Martain"), gender = "male")
        preferencesManager.saveUser(user.toString())
        loadUserFromPrefNullCheck()

    }

    fun loadUserFromPrefNullCheck() {
        TestCase.assertNotNull(preferencesManager.loadUser())

    }

//    @Test
//    fun loadUserFromPref() {
//      TestCase.assertNotNull(preferencesManager.loadUser())
//      TestCase.assertTrue(preferencesManager.loadUser()?.contains("Alex") ?: false)
//    }


}