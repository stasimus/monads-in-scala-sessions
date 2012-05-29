package monad.lesson2

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */

class Box[A](val value: A) {
  // F(A => B): M[B]
  def map[B](func: A => B) = new Box(func(value))
}

object Box {
  // F(M[A])(A=>B):M[B]
  def map[A, B](box: Box[A]): (A => B) => Box[B] = (func: A => B) => new Box(func(box.value))
}
