package com.zwartezwaan.pikachuu

class Pokemony {
    var name:String?=null
    var desc:String?=null
    var img:Int?= null
    var impact:Double?= null
    var lat:Double?=null
    var long:Double?=null
    var caught:Boolean?=false

    constructor(imgy:Int, namey:String, descc:String, impact:Double, laty:Double, longy:Double){
        this.img = imgy
        this.name = namey
        this.desc = descc
        this.impact = impact
        this.lat = laty
        this.long = longy
        this.caught = false
    }
}