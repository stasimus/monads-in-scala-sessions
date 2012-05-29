package monad.lesson2

import org.specs2.mutable.Specification

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */

class Spec extends Specification {
  "map method" should {
    "map function with new signature" in {
      val mapFunc = Box.map[String, Int](new Box("12345"))
      val res = mapFunc(_.size)

      res.value must be equalTo 5
    }
    "map support currying" in {
      val box = Box.map(new Box("1")) {
        _.size
      }

      box.value must be equalTo 1
    }
    "map embedded into container" in {
      val box = new Box("1") map (_.charAt(0))

      box.value must be equalTo '1'
    }
    "map via for" in {
      val box = new Box(1)

      val res = for {
        b <- box
      } yield b * 2

      res.value must be equalTo 2
    }
  }
}
