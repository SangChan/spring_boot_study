package cities

import grails.persistence.*
import grails.mongodb.geo.*
import org.bson.types.ObjectId

@Entity
class City {
    ObjectId id
    String name
    Point location

    static constraints = {
        name blank:false
        location nullable:false
    }

    static mapping = {
        location geoIndex:'2dsphere'
    }
}
