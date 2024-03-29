package com.architectcoders.aacboard.ui.fragments.stateholder

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.architectcoders.aacboard.R
import java.util.Locale

fun Fragment.buildMainDashboardState(): MainDashboardState = MainDashboardState(
    requireContext(),
    findNavController()
)

class MainDashboardState(
    context: Context,
    private val navController: NavController
) {

    private lateinit var textToSpeech: TextToSpeech

    init {
        textToSpeech = TextToSpeech(context) { textToSpeech.language = Locale.getDefault() }
    }

    fun onDashboardListIconClicked() {
        navController.navigate(R.id.action_mainDashboard_to_listDashboards)
    }

    fun onTextToSpeechRequired(selection: List<String>, onStart: () -> Unit, onDone: () -> Unit) {
        textToSpeech.speak(selection.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                onStart()
            }
            override fun onDone(utteranceId: String?) {
                onDone()
            }
            override fun onError(utteranceId: String?) {
                onDone()
            }
        })
    }

    fun onDestroyView() {
        textToSpeech.stop()
    }
}
