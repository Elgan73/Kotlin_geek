package com.aisgorod.kotlin_geek.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisgorod.kotlin_geek.data.Note
import com.aisgorod.kotlin_geek.data.NotesRepository
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"
const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider : DatabaseProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)
    private val result = MutableLiveData<List<Note>>()
    private var subscribedOnDb = false


    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribedOnDb) subscribeForDbChanging()
        return result
    }

    override fun addOrReplace(newNote: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()

        notesReference.document(newNote.id.toString())
            .set(newNote)
            .addOnSuccessListener {
                Log.d(TAG, "Note $newNote is saved")
                result.value = Result.success(newNote)
            }
            .addOnFailureListener {
                Log.d(TAG, "Error saving note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            }

        return result

    }

    override fun removeNote(note: Note) {
        notesReference.document(note.id.toString())
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Note $note is deleted")
            }
            .addOnFailureListener {
                Log.d(TAG, "Something wrong with delete process", it)
            }
    }



    private fun subscribeForDbChanging() {
        notesReference.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d(TAG, "Observe note exception: $e")
            } else if (snapshot != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in snapshot) {
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = notes
            }
        }
        subscribedOnDb = true
    }
}