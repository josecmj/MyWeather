package com.josecmj.myweatherappschool.model

data class QueryResult(val message:String, val cod:String, val list:List<Place>) {

}

data class Place (val id:String, val name:String, val main:Temperatura, val weather:List<Weather>) {

}

data class Weather(val main:String, val icon:String) {

}

data class Temperatura (val temp: String, val humidity: String) {

}
