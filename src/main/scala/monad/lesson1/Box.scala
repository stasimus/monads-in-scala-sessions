package monad.lesson1

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */

class Box[T](val value: T)

object Box {
  def map[A, B](func: A => B): Box[A] => Box[B] =
    (box: Box[A]) => new Box(func(box.value))

  def flatMap[A, B](func: A => Box[B]): Box[A] => Box[B] =
    (box: Box[A]) => func(box.value)

  def apply[A, B](func: Box[A => B]): Box[A] => Box[B] =
    (box: Box[A]) => new Box(func.value(box.value))
}
