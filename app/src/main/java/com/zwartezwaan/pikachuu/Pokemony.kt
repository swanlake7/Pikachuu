package com.zwartezwaan.pikachuu
import android.location.Location

class Pokemony {
    var name:String?=null
    var desc:String?=null
    var img:Int?= null
    var impact:Double?= null
    var caught:Boolean?=false
    var locatie:Location?=null

    constructor(imgy:Int, namey:String, descc:String, impact:Double, laty:Double, longy:Double){
        this.img = imgy
        this.name = namey
        this.desc = descc
        this.impact = impact
        this.locatie = Location(name)
        this.locatie!!.latitude = laty
        this.locatie!!.longitude = longy
        this.caught = false
    }
}