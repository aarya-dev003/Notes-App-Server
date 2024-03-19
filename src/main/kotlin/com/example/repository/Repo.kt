package com.example.repository

import com.example.data.Table.NoteTable
import com.example.data.model.User
import com.example.data.Table.UserTable
import com.example.data.model.Notes
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

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

    // Note CRUD operations

    suspend fun addNote(note:Notes, email: String){
        dbQuery {
            NoteTable.insert {nt ->
                nt[NoteTable.id] = note.id
                nt[NoteTable.userEmail] = email
                nt[NoteTable.description] = note.description
                nt[NoteTable.noteTitle] = note.noteTitle
                nt[NoteTable.date] = note.date
            }
        }
    }

    suspend fun getAllNotes(email: String): List<Notes> = dbQuery {
        NoteTable.select{
            NoteTable.userEmail.eq(email)

        }.mapNotNull { rowToNote(it) }
    }

    suspend fun updateNote(note : Notes, email: String){
        dbQuery {
            NoteTable.update(
                where = {
                    NoteTable.userEmail.eq(email) and NoteTable.id.eq(note.id)
                }
            ) {nt->
                nt[NoteTable.description] = note.description
                nt[NoteTable.noteTitle] = note.noteTitle
                nt[NoteTable.date] = note.date
            }
        }
    }

    suspend fun deleteNote(id : String){
        dbQuery {
            NoteTable.deleteWhere {
                NoteTable.id.eq(id)
            }
        }
    }

    private fun rowToNote(row:ResultRow?): Notes?{
        if(row == null){
            return  null
        }
        return Notes(
            id = row[NoteTable.id],
            noteTitle = row[NoteTable.noteTitle],
            description = row[NoteTable.description],
            date = row[NoteTable.date]
            )
    }
}