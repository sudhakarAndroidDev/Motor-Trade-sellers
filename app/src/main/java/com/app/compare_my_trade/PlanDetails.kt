package com.app.compare_my_trade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class PlanDetails : AppCompatActivity() {
    lateinit var  recyclerview: RecyclerView
    val data1 = ArrayList<PlanModel>()

    lateinit var price: TextView
    lateinit var type: TextView

    lateinit var progresstext:TextView
    lateinit var spinKitView: SpinKitView

    lateinit var payment:LinearLayout

    var Price:String? = null
    var Type:String? = null
    var Name:String? = null
    var Id:String? = null
    var Limit:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_details)

        recyclerview = findViewById(R.id.plan_recyclerview)

        price = findViewById(R.id.plan_price)
        type = findViewById(R.id.plan_type)

        progresstext = findViewById(R.id.progressText)
        spinKitView = findViewById(R.id.progressBar)

        payment = findViewById(R.id.proceed_payment)

        var back = findViewById(R.id.back) as ImageView

        back.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java).apply {



            }
            startActivity(intent)
        }

        data()

        payment.setOnClickListener {

            if(Id != null){
                val intent = Intent(this,PaymentGateway::class.java).apply {

                    putExtra("Name",Name)
                    putExtra("Price",Price)
                    putExtra("Type",Type)
                    putExtra("Id",Id)
                    putExtra("Limit",Limit)


                }
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Choose your plan", Toast.LENGTH_LONG).show()
            }




        }
    }


    private fun data() {

        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/sellers/subscription-plans"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = object : StringRequest(
            Method.GET, URL,
            { response ->

                spinKitView.visibility = View.GONE


                    progresstext.visibility = View.VISIBLE



                val res = JSONArray(response)

                recyclerview.layoutManager = LinearLayoutManager(this)

//                val data1 = ArrayList<HomeViewModel>()

                for (i in 0 until res.length()) {
                    val data: JSONObject = res.getJSONObject(i)
                    progresstext.visibility = View.GONE

                    var id =data.getString("id")
                    var limit= data.getString("limit")
                    var type= data.getString("type")
                    var cost=data.getString("cost")
                    var name = data.getString("name")

//                    Log.i("LOG_VOLLEY", published_at.toString())
//                    Toast.makeText(activity, published_at.toString(), Toast.LENGTH_LONG).show()




                    data1.add(PlanModel(id,"$ "+cost,type,"*"+limit+" Bids For "+type,name))



                }


                val adapter = PlanAdapter(data1)


                recyclerview.adapter = adapter

                adapter.setOnItemClickListener(object : PlanAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val model: PlanModel = data1.get(position)

                        Price = model.price
                        Type  = model.type
                        Name = model.name
                        Id = model.id
                        Limit = model.limit



//                        Toast.makeText(applicationContext,Name.toString(), Toast.LENGTH_LONG).show()


                    }

                })








            },{ error ->
                spinKitView.visibility = View.GONE
                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                }
            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@PlanDetails))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


    }

    override fun onBackPressed() {

            val intent = Intent(this,HomeActivity::class.java).apply {



            }
            startActivity(intent)
    }
}


data class PlanModel (val id: String,val price: String,val type: String,val limit: String,val name: String  ){
}









class  PlanAdapter (private val mList: List<PlanModel>) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {


    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }

    private var lastChecked: CheckBox? = null
    private var lastCheckedPos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plan_items, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val PlanModel = mList[position]


        holder.plan_name.text = PlanModel.name
        holder.plan_price.text = PlanModel.price
        holder.plan_count.text = PlanModel.limit
        holder.plan_type.text = PlanModel.type


        holder.checkBox.isChecked
        holder.checkBox.tag = position

        //for default check in first item

        //for default check in first item
        if (position == 0 ) {
            lastChecked = holder.checkBox
            lastCheckedPos = 0
        }

//        holder.checkBox.setOnClickListener { v ->
//            val cb = v as CheckBox
//            val clickedPos = (cb.tag as Int).toInt()
//            if (cb.isChecked) {
//                lastChecked?.setChecked(false)
//                lastChecked = cb
//                lastCheckedPos = clickedPos
//
//
//
//            } else lastChecked = null
//
//        }

        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(arg0: CompoundButton?, isChecked: Boolean) {
                if(isChecked == true){
                    val cb = arg0 as CheckBox

                    val clickedPos = (cb.tag as Int).toInt()
                    if (cb.isChecked) {
                        lastChecked?.setChecked(false)
                        lastChecked = cb
                        lastCheckedPos = clickedPos
                        holder.checkBox.isChecked = true


                    } else lastChecked = null
                }

            }
        })




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, listener: PlanAdapter.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {

        var imageView:ImageView = itemView.findViewById(R.id.image)
        var checkBox:CheckBox =  itemView.findViewById(R.id.plan_check)
        var plan_name: TextView = itemView.findViewById(R.id.plan_name)
        var plan_price: TextView = itemView.findViewById(R.id.plan_price)
        var plan_count: TextView = itemView.findViewById(R.id.plan_count)
        var plan_type: TextView = itemView.findViewById(R.id.plan_type)









        init {



            checkBox.setOnClickListener {

                    listener.onItemClick(adapterPosition)


            }







        }

    }
}