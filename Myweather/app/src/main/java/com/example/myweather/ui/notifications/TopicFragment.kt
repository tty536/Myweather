package com.example.myweather.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.Database.GetService
import com.example.myweather.Database.Topic
import com.example.myweather.R
import com.example.myweather.logic.model.TopicAdapter

class TopicFragment : Fragment() {
    private lateinit var topicListView:ListView
    private lateinit var topic :String
    private lateinit var topicList:ArrayList<Topic>
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_community, container, false)
        topicListView = root.findViewById(R.id.List_topic)

        val  getDate=GetService()
        topicList = getDate.returnContent()!!
        val topicAdapter = this.activity?.let { TopicAdapter(it,topicList) }
        topicListView.adapter = topicAdapter

        return root
    }
}