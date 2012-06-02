package monad.lessons4

import org.specs2.mutable.Specification

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/29/12
 */

class Spec extends Specification {
  "Response Monad" should {
    "Marvelous isn't readable" in {
      val box = Marvelous
      val res = box map {
        _.toString
      }

      res.isStable must be equalTo false
      box must be equalTo Marvelous
    }
    "Marvelous isn't readable, even twice" in {
      val box = Marvelous
      val res = box map {
        _.toString
      } map {
        _ == Marvelous
      }

      res.isStable must be equalTo false
      box must be equalTo Marvelous
    }
    "m flatMap {x => mzero} ≡ mzero" in {
      val m = new Answer(1)
      val res = m flatMap {
        x => Marvelous
      }

      res must be equalTo Marvelous
    }
    "mzero plus m ≡ m" in {
      val mZero = Marvelous
      val m = new Answer("a")
      val res = mZero orElse m

      res must be equalTo m
    }
    "m plus mzero ≡ m" in {
      val mZero = Marvelous
      val m = new Answer("a")
      val res = m orElse mZero

      res must be equalTo m
    }
  }
}
