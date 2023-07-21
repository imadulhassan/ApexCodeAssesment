package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.User
import javax.inject.Inject

// TODO (2 points): Add tests
class RemoteDataSource @Inject constructor( private  val apiEndPpoint:UserApis) {

  // TODO (5 points): Load data from endpoint https://randomuser.me/api
   suspend fun LoadUser() = apiEndPpoint.getRandomUser()

  // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
  // TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints
   suspend fun loadUsers() = apiEndPpoint.getUserList("10")
}
