package com.josecmj.myweatherappschool.model.repo

data class Repo(val id:String, val name:String, val description:String, val owner:RepoOwner) {

}

data class RepoOwner(val login:String, val id: String) {

}
