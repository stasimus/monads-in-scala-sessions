package monad.lesson3

import org.specs2.mutable.Specification

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/29/12
 */

class Spec extends Specification {
  "Response Monad" should {
    "Answer works as Container" in {
      val box = new Answer(10) map {
        _ - 5
      }

      box.value must be equalTo 5
    }
    "Answer works as chain (Second Functor Law: Composition)" in {
      val box = new Answer(5)
      val func1: Int => Int = {
        _ + 10
      }
      val func2: Int => String = {
        _.toString
      }
      val res = box map func1 map func2

      res must be equalTo (box map (x => func2(func1(x))))
    }
    "Answer can be boxed to Answer" in {
      val toMath = (in: String) => new Answer(in.toInt)
      val box = new Answer("5") map toMath

      box.value.value must be equalTo 5
    }
    "Anwser supports The Functor/Monad Connection Law" in {
      val box: Answer[Int] = new Answer(5)
      val func1: Int => Int = {
        _ - 4
      }

      val res = box map func1

      val expectedResult = box flatMap {
        x => new Answer(func1(x))
      }
      res.value must be equalTo expectedResult.value
    }
    "Answer isn't Marvelous" in {
      val box = new Answer[Any](null)
      val func: (Any) => Int = (in) => 5
      val res = box map func

      res.value must be equalTo 5
    }
    "flatMap f ≡ flatten(m map f)" in {
      val box: Answer[Int] = new Answer(0)
      val func1: Int => Answer[Int] = (x) => new Answer(x + 10)

      val res = box flatMap func1
      val flatten = new Answer(1).flatten[Int] _

      res.value must be equalTo (flatten(box map func1).value)
    }
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
  }
}
