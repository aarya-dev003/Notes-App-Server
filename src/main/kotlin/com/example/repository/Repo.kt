package com.example.repository

import com.example.data.model.User
import com.example.data.Table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {
    suspend fun addUser(user: User){
        dbQuery{
            UserTable.insert { ut->
                ut[name] = user.name
                ut[email] = user.email
                ut[hashPassword] = user.hashPassword
            }
        }
    }

    suspend fun findUserByEmail(email : String) = dbQuery {
        UserTable.select{ UserTable.email.eq(email)}
            .map{
                rowToUser(it)
            }
            .singleOrNull()
    }

    private fun rowToUser(row : ResultRow? ): User?{
        if(row == null)
            return null

        return User(
            email = row[UserTable.email],
            name = row[UserTable.name],
            hashPassword = row[UserTable.hashPassword]
        )
    }
}