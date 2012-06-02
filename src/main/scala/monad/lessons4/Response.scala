package monad.lessons4

/**
 * 3 Lang Dojo
 * User: stas
 * Date: 5/29/12
 */

sealed abstract class Response[+A](val isStable: Boolean) {

  def value: A

  def map[B](func: A => B): Response[B]

  def flatten[B](outer: Response[Response[B]]): Response[B]

  final def flatMap[B](f: A => Response[B]): Response[B] = flatten(map(f))

  def orElse[B >: A](alternative: => Response[B]): Response[B]
}

final case class Answer[+A](value: A) extends Response[A](true) {

  def map[B](func: A => B) = new Answer(func(value))

  def flatten[B](outer: Response[Response[B]]) = outer.value

  def orElse[B >: A](alternative: => Response[B]) = this
}

case object Marvelous extends Response[Nothing](false) {

  def value = throw new NoSuchElementException("Something is wrong inside")

  def map[B](func: Nothing => B) = Marvelous

  def flatten[B](outer: Response[Response[B]]) = Marvelous

  def orElse[B >: Nothing](alternative: => Response[B]) = alternative
}