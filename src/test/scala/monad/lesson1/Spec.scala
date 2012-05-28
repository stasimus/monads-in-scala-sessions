package monad.lesson1

import org.specs2.mutable.Specification


/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */
class Spec extends Specification {
  "Box[A] (Container type)" should {
    "Hold and return value" in {
      val box = new Box("Testing")
      box.value must be equalTo "Testing"
    }
  }
}
