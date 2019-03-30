package alleles.environment

import alleles.{Epoch, Population}
import alleles.genotype.Scheme

trait Epic[A] {
  def initialPopulation: Population[A]

  def operators: Epoch[A]
}

object Epic {
  def apply[A](initialPopulation: Population[A], operators: Epoch[A]): Epic[A] =
    new Strict(initialPopulation, operators)

  def apply[A: Scheme](populationSize: Int, operators: Epoch[A]): Epic[A] =
    new Lazy(populationSize, operators)
}

private final class Strict[A](val initialPopulation: Population[A],
                              val operators: Epoch[A]) extends Epic[A]

private final class Lazy[A: Scheme](populationSize: Int, val operators: Epoch[A]) extends Epic[A] {
  def initialPopulation: Population[A] = Scheme.make(populationSize)
}
