package com.aisgorod.kotlin_geek.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisgorod.kotlin_geek.data.NotesRepository
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.model.User
import io.mockk.*
import org.junit.*
import org.junit.rules.TestRule


class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>(relaxed = true)
    private lateinit var viewModel: NoteViewModel

    private var _resultLiveData = MutableLiveData(Result.success(Note()))

    private val resultLiveData: LiveData<Result<Note>> get() = _resultLiveData

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.getCurrentUser() } returns User("ghn", "uytfjutyf")
        every { notesRepositoryMock.addOrReplace(any()) } returns MutableLiveData(Result.success(
            Note()
        ))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `LiveData contains nothing after save`() {
        val currentNote = Note(title = "Hello!")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `LiveData contains value after save`() {
        every { notesRepositoryMock.addOrReplace(any()) } returns MutableLiveData(
            Result.failure(
                IllegalAccessError()
            )
        )
        val currentNote = Note(title = "Hello!")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.saveNote()

        Assert.assertTrue(viewModel.showError().value != null)
    }

    @Test
    fun `ViewModel note color changed`() {
        val currentNote = Note(_color = 0x000)
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)

        viewModel.updateColor(0x000)

        Assert.assertEquals(0x000, viewModel.note?._color)
    }

    @Test
    fun `NotesRepository addOrReplaceNote called with correct argument and title` () {
        viewModel = NoteViewModel(notesRepositoryMock, null)

        viewModel.updateTitle("Hello!")
        viewModel.saveNote()

        val slot = slot<Note>()
        verify(exactly = 1) {
            notesRepositoryMock.addOrReplace(match { it.title == "Hello!" })
        }
    }
}