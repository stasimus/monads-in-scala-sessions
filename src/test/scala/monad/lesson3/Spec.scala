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
    "m flatMap unit ≡ m" in {
      val box: Answer[Char] = new Answer('a')
      val unit: Char => Answer[Char] = (x) => new Answer(x)

      val res = box flatMap unit

      res must be equalTo (box flatMap {
        x => unit(x)
      })
    }
    "unit(x) flatMap f ≡ f(x)" in {
      val value = 'b'
      def unit[B]: B => Response[B] = new Answer(_)
      val func: Char => Response[String] = {
        (in) => new Answer(in.toString)
      }

      val res = unit(value) flatMap func

      res must be equalTo func(value)
    }
    "m flatMap g flatMap f ≡ m flatMap {x => g(x) flatMap f}" in {
      val m = new Answer(1)
      val g: Int => Response[Int] = {
        (in) => new Answer(in + 1)
      }
      val f: Int => Response[Int] = {
        (in) => new Answer(in + 2)
      }

      val res = m flatMap g flatMap f
      val expected = m flatMap {
        x => g(x) flatMap (f)
      }

      res must be equalTo expected
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
    "m flatMap {x => mzero} ≡ mzero" in {
      val m = new Answer(1)
      val res = m flatMap {
        x => Marvelous
      }

      res must be equalTo Marvelous
    }
  }
}
