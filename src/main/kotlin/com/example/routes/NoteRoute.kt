@file:Suppress("DEPRECATION")

package com.example.routes

import com.example.data.model.Notes
import com.example.data.model.SimpleResponse
import com.example.data.model.User
import com.example.repository.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Route.NoteRoute(
    db: Repo,
    hashFunction : (String)-> String
){
    authenticate("jwt"){
        post("notes/create"){
            val note = call.receiveOrNull<Notes>()
            if(note == null){
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false,"Missing Field"))
                return@post
            }

            try{
                val email = call.principal<User>()!!.email
                db.addNote(note,email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note Added Successfully"))
            }catch (e:Exception){
                call.respond(HttpStatusCode.Conflict , SimpleResponse(false, e.message ?: "Some Problem Occured"))
            }

        }

        get("notes/get"){
            try {
                val email = call.principal<User>()!!.email
                val notes = db.getAllNotes(email)
                call.respond(HttpStatusCode.OK, notes)
            }catch (e:Exception){
                call.respond(HttpStatusCode.Conflict, emptyList<Notes>())
            }
        }

        put("notes/update"){
            val note = try{
                call.receive<Notes>()
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false,"Missing Field"))
                return@put
            }

            try{
                val email = call.principal<User>()!!.email
                db.updateNote(note,email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note Updated Successfully"))
            }catch (e:Exception){
                call.respond(HttpStatusCode.Conflict , SimpleResponse(false, e.message ?: "Some Problem Occured"))
            }
        }

        delete("notes/delete"){
            val noteId = try {
                call.request.queryParameters["id"]!!
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse(false, "QueryParameter: Id is not present"))
                return@delete
            }

            try {
                val email = call.principal<User>()!!.email
                db.deleteNote(noteId,email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true,"Note Deleted Successfully"))
            }catch (e:Exception){
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problem Occured"))
            }
        }

    }
}