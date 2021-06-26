package com.example.myweather.logic.model

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.Database.Topic
import com.example.myweather.R

class TopicAdapter(context:Context,topiclist:List<Topic>) : BaseAdapter() {

    private  var context:Context = context
    private  var topicList: List<Topic> = topiclist

    override fun getCount(): Int {
        //TODO("Not yet implemented")
        return topicList.size
    }

    override fun getItem(position: Int): Any {
        //TODO("Not yet implemented")
        return topicList
    }

    override fun getItemId(position: Int): Long {
        //TODO("Not yet implemented")
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //TODO("Not yet implemented")

        var v:View
        var holder:ViewHolder
        if(convertView == null){
            holder  = ViewHolder()
            v = LayoutInflater.from(context).inflate(R.layout.topic, null, false)
            holder.content = v.findViewById(R.id.title)
            holder.date = v.findViewById(R.id.date)
            holder.discuss = v.findViewById(R.id.discuss)
        }else{
            v=convertView
            holder = v.tag as ViewHolder
        }
        holder.content.text = ((position+1)?.toString()+"." + topicList[position].topicContent)
        holder.date.text = ("发表时间：" + topicList[position].topicDate)
        if ("" == topicList[position].discussTime || null == topicList[position].discussTime){
            holder.discuss.text = "暂无回复"
        }else{
            val discuss : List<String> = topicList[position].discuss.split("#")
            val discussTime :List<String> = topicList[position].discussTime.split("#")
            var answer:String = "";
            for (i in 0 until discuss.size){
                answer  = answer + "<p><big>"+discuss[i] + "</big></p><i><small>评论时间：" + discussTime[i] + "</small></i>"
            }
            holder.discuss.text = Html.fromHtml(answer)
        }
        return v
    }


     class ViewHolder{
         lateinit var content : TextView
         lateinit var date:TextView
         lateinit var discuss:TextView
     }
}