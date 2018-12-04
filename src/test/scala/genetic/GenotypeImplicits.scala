package genetic

import genetic.genotype._
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

import scala.util.Random

trait GenotypeImplicits[G] {
  implicit val fitness: Fitness[G]
  implicit val join: Join[G]
  implicit val modification: Modification[G]
  implicit val scheme: Scheme[G]
}

object GenotypeImplicits {
  /**
    * Summoner method
    */
  def apply[G](implicit implicits: GenotypeImplicits[G]): GenotypeImplicits[G] = implicits

  /**
    * Implicits set for type Int
    */
  implicit val intImplicits: GenotypeImplicits[Int] = new GenotypeImplicits[Int] {
    val fitness: Fitness[Int] = x => math.abs(x.toDouble)
    val join: Join[Int] = Join.commutative { case (x, y) => (x + y) / 2 }
    val modification: Modification[Int] = _ + 1
    val scheme: Scheme[Int] = () => arbitrary[Int].sample.get
  }

  /**
    * Implicits set for type String
    */
  implicit val stringImplicits: GenotypeImplicits[String] = new GenotypeImplicits[String] {
    val fitness: Fitness[String] = _.length
    val join: Join[String] = Join.singlePoint[String](x => x.splitAt(x.length / 2)) { case (x, y) => x + y }

    private val buffer = "The quick brown fox jumps over the lazy dog".replaceAll(" ", "")
    val modification: Modification[String] = { x =>
      val randomChar = buffer(Random.nextInt(buffer.length))
      if (x.isEmpty) randomChar.toString
      else x.updated(Random.nextInt(x.length), randomChar)
    }

    val scheme: Scheme[String] = () => arbitrary[String].sample.get
  }
}
