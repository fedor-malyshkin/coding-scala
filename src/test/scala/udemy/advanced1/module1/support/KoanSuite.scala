
/* Copyright (C) 2010-2017 Escalate Software, LLC All rights reserved. */

package udemy.advanced1.module1.support

import org.scalatest.funsuite.AnyFunSuite

abstract class KoanSuite extends AnyFunSuite {
  def koan(name: String)(fun: => Unit) = test(name)(fun)
}

