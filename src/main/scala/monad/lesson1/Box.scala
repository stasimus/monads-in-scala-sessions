package monad.lesson1

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/28/12
 */

class Box[T](val value: T)

object Box {
  def map[A, B](func: A => B): Box[A] => Box[B] = (a: Box[A]) => new Box(func(a.value))

  def flatMap[A, B](func: A => Box[B]): Box[A] => Box[B] = (a: Box[A]) => func(a.value)

  def apply[A, B](func: Box[A => B]): Box[A] => Box[B] = (box: Box[A]) => new Box(func.value(box.value))
}
