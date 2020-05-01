package com.nahman.currentweatherapp.controller

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.nahman.currentweatherapp.R
import com.nahman.currentweatherapp.model.ApiManager
import com.nahman.currentweatherapp.model.IOK
import com.nahman.currentweatherapp.model.Weather
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var tvCityName : TextView
    lateinit var tvWeather : TextView
    lateinit var ivWeatherIcon : ImageView
    lateinit var etCityName : EditText
    lateinit var linearLayout : LinearLayout
    lateinit var progressBar : ProgressBar
    lateinit var tvCityNotExist : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCityName = findViewById(R.id.tvCityName)
        tvWeather = findViewById(R.id.tvWeather)
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon)
        etCityName = findViewById(R.id.etCityName)
        linearLayout = findViewById(R.id.linearLayout)
        progressBar = findViewById(R.id.progressBar)
        tvCityNotExist = findViewById(R.id.tvCityNotExist)




        etCityName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (etCityName.text.toString().length>0 || etCityName.text == null){
                    var loadWeatherTask = LoadWeatherTask()
                    loadWeatherTask.execute(ApiManager.getWeatherUrl(etCityName.text.toString()))


                    linearLayout.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE


                    try {
                        Picasso.get()
                            .load("")
                            .error(R.drawable.ic_error_red)
                            .into(ivWeatherIcon);
                    }catch (e : Exception){

                    }

                }else{
                    tvCityNotExist.visibility = View.GONE
                }
            }
        })


/*        etCityName.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if (event?.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
                    var loadWeatherTask = LoadWeatherTask()
                    loadWeatherTask.execute(ApiManager.getWeatherUrl(etCityName.text.toString()))

                    return true;
                }
                return false

            }

        })*/

    }


    inner class LoadWeatherTask : AsyncTask<String, Void, Weather>() {

        override fun doInBackground(vararg params: String?): Weather? {

            val url : String? = params[0]

            println(params[0])

            try{
                val weather : Weather = ApiManager.convertJsonToWeather(IOK.readURL(url))
                return weather;

            }catch(e: Exception){
                println(e.toString())
            }
            return null
        }

        override fun onPostExecute(result: Weather?) {
            super.onPostExecute(result)

            if (result==null) {

                linearLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvCityNotExist.visibility = View.VISIBLE

                try {
                    Picasso.get()
                        .load("")
                        .error(R.drawable.ic_error_red)
                        .into(ivWeatherIcon);
                }catch (e : Exception){

                }
                return
            }

            tvCityName.text = result.city
            tvWeather.text = result.weather

            println(ApiManager.getImageUrl(result.icon))
            runOnUiThread{
                linearLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                tvCityNotExist.visibility = View.GONE
                Picasso.get()
                    .load(ApiManager.getImageUrl(result.icon))
                    .error(R.drawable.ic_error_red)
                    .into(ivWeatherIcon);
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.topMenu_about-> {
                startActivity(Intent(this, AboutActivity::class.java))
                //Toast.makeText(this@MainActivity, "Its toast!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
