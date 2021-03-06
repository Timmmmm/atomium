package controllers

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}

import be.wegenenverkeer.atomium.format.jaxb.{xmlElement, xmlAttribute, xmlTypeAdapter}
import be.wegenenverkeer.atomium.format.jaxb.Adapters.StringOptionAdapter
import play.api.libs.json._

object EventFormat {
  implicit val eventFormat = Json.format[Event]
}

@XmlRootElement
@XmlAccessorType (XmlAccessType.NONE)
case class Event(@xmlElement value: Double,
                 @xmlElement @xmlTypeAdapter(classOf[StringOptionAdapter]) description: Option[String],
                 @xmlAttribute version: Int = 1) {
    def this() = this(0.0, None, 0)

}
