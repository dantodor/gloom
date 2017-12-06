package lorcatech.gloom

/**
  * *********************************************************************
  * Copyright (c) 2017, Lorca Technologies                             *
  * Distributed under the MIT software license, see the accompanying   *
  * file COPYING or http://www.opensource.org/licenses/mit-license.php.*
  * *********************************************************************
  */

import sun.misc.Unsafe

import scala.language.postfixOps
import scala.util.Try

object MemAlloc {

  val unsafe: Unsafe = Try {
    classOf[Unsafe]
      .getDeclaredFields
      .find { field =>
        field.getType == classOf[Unsafe]
      }
      .map { field =>
        field.setAccessible(true)
        field.get(null).asInstanceOf[Unsafe]
      }
      .getOrElse(throw new IllegalStateException("Can't find instance of sun.misc.Unsafe"))
  } recover {
    case th: Throwable => throw new ExceptionInInitializerError(th)
  } get

}

