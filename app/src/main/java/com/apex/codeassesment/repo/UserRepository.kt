package com.apex.codeassesment.repo

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.Resource

interface UserRepository {

    suspend fun  getUserList():Resource<List<User>>

    fun  getSavedUser():User

    suspend fun  getUserForcefullyUpdate(update:Boolean):Resource<User>


}