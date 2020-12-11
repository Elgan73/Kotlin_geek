package com.aisgorod.kotlin_geek.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisgorod.kotlin_geek.errors.NoAuthException
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"

const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider : DatabaseProvider {

    private val db = FirebaseFirestore.getInstance()
    private val result = MutableLiveData<List<Note>>()

    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private var subscribedOnDb = false

    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribedOnDb) subscribeForDbChanging()
        return result
    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }


    override fun addOrReplace(newNote: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()

        handleNotesReference(
            {
                getUserNotesCollection()
                    .document(newNote.id.toString())
                    .set(newNote)
                    .addOnSuccessListener {
                        Log.d(TAG, "Note $newNote is saved")
                        result.value = Result.success(newNote)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                        result.value = Result.failure(it)
                    }
            }, {
                Log.e(TAG, "Error getting reference note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            }
        )

        return result
    }

    override fun deleteNote(noteId: String): LiveData<Result<Unit>> =
        MutableLiveData<Result<Unit>>().apply {
            getUserNotesCollection().document(noteId).delete()
                .addOnSuccessListener {
                    value = Result.success(Unit)
                }.addOnFailureListener {
                    value = Result.failure(it)
                }
        }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    private fun subscribeForDbChanging() {

        handleNotesReference(
            {
                getUserNotesCollection().addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Observe note exception: $e")
                    } else if (snapshot != null) {
                        val notes = mutableListOf<Note>()

                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        result.value = notes
                    }
                }

                subscribedOnDb = true
            },
            {
                Log.e(TAG, "Error getting reference while subscribed for notes")
            }
        )
    }

    private inline fun handleNotesReference(
        referenceHandler: (CollectionReference) -> Unit,
        exceptionHandler: (Throwable) -> Unit = {}
    ) {
        kotlin.runCatching {
            getUserNotesCollection()
        }
            .fold(referenceHandler, exceptionHandler)
    }
}