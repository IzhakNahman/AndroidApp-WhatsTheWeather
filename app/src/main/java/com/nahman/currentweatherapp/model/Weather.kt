package com.nahman.currentweatherapp.model

class Weather {

    var city : String
    var weather : String
    var icon : String

    constructor(weather: String, icon: String, city: String) {
        this.weather = weather
        this.icon = icon
        this.city = city
    }
}