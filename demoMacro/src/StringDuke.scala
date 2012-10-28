

object StringDuke  {
  
  case class Personne(nom : String, age: Int = 20)

  def hello( personne:Personne ) = {
    s"Ahoy ${personne.nom.toUpperCase} !"
  }

}
