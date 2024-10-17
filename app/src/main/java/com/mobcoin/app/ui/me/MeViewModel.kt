package com.mobcoin.app.ui.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random

class MeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is me Fragment"
    }
    val text: LiveData<String> = _text


    val chart1EntryModelProducer = ChartEntryModelProducer()


    fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }
    fun updateChart1(){
        chart1EntryModelProducer.setEntries(getRandomEntries())
    }


}