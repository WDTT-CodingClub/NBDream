package kr.co.main.calendar.ui.calendar_screen.add_diary_screen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject


interface AddDiaryScreenEvent {
    fun onBackClick()
    fun onPostClick()

    fun onRegisterDateInput(date: LocalDate)

    fun onWorkLaborerInput(workLaborer: Int)
    fun onWorkHourInput(workHour: Int)
    fun onWorkAreaInput(workArea: Int)

    fun onAddWorkDescription(
        workCategory: DiaryModel.WorkDescriptionModel.TypeId,
        workDescription: String
    )
    fun onDeleteWorkDescription(workDescriptionId: String)

    fun onAddImage(imageUrl: String)
    fun onDeleteImage(imageUrl: String)

    fun onMemoInput(memo: String)
}

@HiltViewModel
internal class AddDiaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddDiaryViewModel.AddDiaryScreenState>(savedStateHandle), AddDiaryScreenEvent {

    val event: AddDiaryScreenEvent = this@AddDiaryViewModel

    data class AddDiaryScreenState(
        val diaryId: String = "",
        val registerDate: LocalDate = LocalDate.now(),
        val weatherForecast: String = "",
        val workLaborer: Int = 0,
        val workHour: Int = 0,
        val workArea: Int = 0,
        val workDescriptions: List<DiaryModel.WorkDescriptionModel> = emptyList(),
        val images: List<String> = emptyList(),
        val memo: String = ""
    ) : BaseViewModel.State{
        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
    }

    override fun createInitialState(savedState: Parcelable?): AddDiaryScreenState =
        savedState?.let {
            // TODO("deserialize")
            AddDiaryScreenState()
        }?: AddDiaryScreenState()

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

    override fun onPostClick() {
        TODO("Not yet implemented")
    }

    override fun onRegisterDateInput(date: LocalDate) {
        TODO("Not yet implemented")
    }

    override fun onWorkLaborerInput(workLaborer: Int) {
        TODO("Not yet implemented")
    }

    override fun onWorkHourInput(workHour: Int) {
        TODO("Not yet implemented")
    }

    override fun onWorkAreaInput(workArea: Int) {
        TODO("Not yet implemented")
    }

    override fun onAddWorkDescription(
        workCategory: DiaryModel.WorkDescriptionModel.TypeId,
        workDescription: String
    ) {
        TODO("Not yet implemented")
    }

    override fun onDeleteWorkDescription(workDescriptionId: String) {
        TODO("Not yet implemented")
    }

    override fun onAddImage(imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteImage(imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onMemoInput(memo: String) {
        TODO("Not yet implemented")
    }

}