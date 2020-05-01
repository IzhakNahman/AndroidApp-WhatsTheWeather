package com.nahman.currentweatherapp.model

import org.json.JSONException
import org.json.JSONObject

object ApiManager {
    const val API_KEY = "3871f5fb2099a5edff4d48e6f664b7d6"

    @Throws(JSONException::class)
    fun convertJsonToWeather(json: String?): Weather {

        val jsonObject = JSONObject(json)
        val weatherArray = jsonObject.getJSONArray("weather")
        println(weatherArray.toString())
        val weatherObject = weatherArray.getJSONObject(0)
        println(weatherObject.toString())
        val weather = weatherObject.getString("description")
        val icon = weatherObject.getString("icon")
        var city = jsonObject.getString("name")
        return Weather(weather, icon, city)

    }

    fun getWeatherUrl(city: String): String {
        return "http://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY"
    }

    fun getImageUrl(imageId: String): String {
        return "http://openweathermap.org/img/w/$imageId.png"
    }
}