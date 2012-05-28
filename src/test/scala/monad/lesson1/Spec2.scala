package monad.lesson1

import org.specs2.mutable.Specification

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */

class Spec2 extends Specification {
  "Functor, Monad, Applicative models" should {
    "map (Functor) methods works for String => Int" in {
      val in = new Box("123456789")
      val rawFunc = (str: String) => str.size
      val mapFunc = Box.map(rawFunc)
      val res = mapFunc(in)

      res.value must be equalTo 9
    }
    "map (Functor) method works for String => boolean" in {
      val res = Box.map((str: String) => str.contains('4'))(new Box("abc123"))

      res.value must be equalTo false
    }
    "flatMap (Monad) method works for String => Box[Int] in {" in {
      val in = new Box("12345678")
      val rawFunc = (value: String) => new Box(value.size)
      val flatMapFunc = Box.flatMap(rawFunc)
      val res = flatMapFunc(in)

      res.value must be equalTo 8
    }
    "apply (Applicative) method works for Box[Int] => String" in {
      val in = new Box(12345678)
      val rawFunc = new Box((value: Int) => value.toString)
      val applyFunc = Box.apply(rawFunc)
      val res = applyFunc(in)

      res.value must be equalTo "12345678"
    }
  }
}
