package com.apex.codeassesment.repo

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.data.remote.Resource
import com.apex.codeassesment.data.remote.Status
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource
) : UserRepository {

    private val savedUser = AtomicReference(User())


    override suspend fun getUserList(): Resource<List<User>> {
        val response = remoteDataSource.loadUsers()
        return if (response.isSuccessful) {
            Resource(Status.SUCCESS, response.body()?.results, "Success")
        } else {
            Resource(Status.ERROR, null, "Failed")
        }
    }

    override fun getSavedUser(): User = localDataSource.loadUserfromPreferences()


    override suspend fun getUserForcefullyUpdate(update: Boolean): Resource<User> {
        if (update) {
            val userReponsee = remoteDataSource.LoadUser()
            if (userReponsee.isSuccessful) {
                val newUser = userReponsee.body()?.results?.get(0) ?: savedUser.get()
                localDataSource.saveUserInPreferences(newUser)
                savedUser.set(newUser)
            }
        }
        return Resource.success(savedUser.get())
    }
}
