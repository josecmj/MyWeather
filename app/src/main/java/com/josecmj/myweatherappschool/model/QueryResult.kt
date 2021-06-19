package com.josecmj.myweatherappschool.model

data class QueryResult(val message:String, val cod:String, val list:List<Place>) {

}

data class Place (val name:String, val main:Temperatura) {
}

data class Temperatura (val temp: String, val humidity: String) {

}
