package com.apex.codeassesment.repo

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.BaseResponseModel
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.data.remote.Resource
import com.apex.codeassesment.data.remote.Status
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    // The class under test
    private lateinit var userRepository: UserRepository

    // Mocked dependencies
    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Initialize the class under test with the mocked dependencies
        userRepository = UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `test getUserList when remote data source returns success`() = runBlocking {
        // Mock the behavior of the remote data source
        val userList = arrayListOf(User.createRandom(), User.createRandom())

        //`when`(remoteDataSource.loadUsers()).thenReturn(BaseResponseModel(userList))

        // Test the function
        val result = userRepository.getUserList()

        // Verify that the returned Resource has the correct status, data, and message
        assertEquals(Status.SUCCESS, result.status)
        assertEquals(userList, result.data)
        assertEquals("Success", result.message)
    }


    @Test
    fun `test getUserForcefullyUpdate when update is false`() = runBlocking {
        // Test the function with update set to false
        val result = userRepository.getUserForcefullyUpdate(false)

        // Verify that the returned Resource has the correct status and data
        assertEquals(Status.SUCCESS, result.status)
        assertEquals(userRepository.getSavedUser(), result.data)
        // Verify that neither the remote nor local data sources are called to update the user
    }
}