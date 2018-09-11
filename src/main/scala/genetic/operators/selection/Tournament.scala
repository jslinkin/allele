package genetic.operators.selection

import genetic.operators.Selection
import genetic.{Fitness, Population}

import scala.util.Random

case class Tournament[A: Fitness](size: Int) extends Selection[A] {
  def apply(population: Population[A]): Population[A] = population.map(_ =>
    Random.shuffle(population).take(size).minBy(Fitness(_)))

  override def toString: String = s"Tournament selection with tournament size of $size"
}
