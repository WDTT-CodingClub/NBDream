package kr.co.main.calendar.ui.diarytab.adddiary

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.main.calendar.model.WeatherForecastModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject


interface AddDiaryInput {
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
    fun onAddImage(imageUrl:String)
    fun onDeleteImage(imageUrl:String)

    fun onMemoInput(memo: String)
}

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
internal class AddDiaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddDiaryViewModel.State>(savedStateHandle), AddDiaryInput {
    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }

    val input = this@AddDiaryViewModel

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

    override fun onAddImage(imageUrl:String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteImage(imageUrl:String) {
        TODO("Not yet implemented")
    }

    override fun onMemoInput(memo: String) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    data class State(
        val diaryId: String,
        val registerDate: LocalDate = LocalDate.now(),
        val weatherForecast: WeatherForecastModel,
        val workLaborer: Int = 0,
        val workHour: Int = 0,
        val workArea: Int = 0,
        val workDescriptions: List<DiaryModel.WorkDescriptionModel> = emptyList(),
        val images: List<String> = emptyList(),
        val memo: String = ""
    ) : BaseViewModel.State
}