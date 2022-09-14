package layout

import com.example.a20220913_oscarhinojosa_nycschools.SchoolsItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a20220913_oscarhinojosa_nycschools.R


class SchoolsRecyclerViewAdapter(
    private val clickListener:(SchoolsItem)->Unit
): RecyclerView.Adapter<SchoolViewHolder>() {

    private val schoolList = ArrayList<SchoolsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)
        return SchoolViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(schoolList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }

    fun setList(schools:List<SchoolsItem>){
        schoolList.clear()
        schoolList.addAll(schools)
    }

}

class SchoolViewHolder(val view: View):RecyclerView.ViewHolder(view){
    fun bind(schools: SchoolsItem, clickListener:(SchoolsItem)->Unit){
        val nameTextView = view.findViewById<TextView>(R.id.tvSchool)
        val cityTextView = view.findViewById<TextView>(R.id.tvCityZip)
        nameTextView.text = schools.school_name
        cityTextView.text = "${schools.city}, ${schools.zip}"
        view.setOnClickListener{
            clickListener(schools)
        }
    }

}