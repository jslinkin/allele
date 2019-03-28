package genetic.genotype.standard.seq

import genetic.genotype.Variation
import genetic.genotype.syntax._
import genetic.toolset.RRandom

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom

object Variations {

  /**
    * Makes sense only for individuals of size greater than 1
    */
  def swap[A, Coll <: SeqLike[A, Coll]](implicit cbf: CanBuildFrom[Coll, A, Coll]): Variation[Coll] =
    (ind: Coll) =>
      if (ind.size < 2) ind
      else {
        val i1 = RRandom.nextInt(ind.length)
        var i2 = RRandom.nextInt(ind.length - 1)
        if (i1 == i2) i2 = ind.length - 1
        ind.updated(i1, ind(i2)).updated(i2, ind(i1))
      }

  def elementVariation[A, Coll <: SeqLike[A, Coll]]
  (implicit cbf: CanBuildFrom[Coll, A, Coll], v: Variation[A]): Variation[Coll] =
    (ind: Coll) => {
      val i = RRandom.nextInt(ind.length)
      ind.updated(i, ind(i).modified)
    }
}