package edu.unq.vainilla.core.utils

import scala.collection.mutable.ListBuffer

trait TreeLike[T] {

  def flatten: List[T] = {
    val buffer = ListBuffer.empty[T]
    inOrder(buffer)
    buffer.toList
  }

  def inOrder(buffer: ListBuffer[T]): Unit

}

trait Leaf[T] extends TreeLike[T] {
  def inOrder(buffer: ListBuffer[T]) = buffer.+=(this.asInstanceOf[T])
}

trait Node[T] extends TreeLike[T] {
  var childs = List.empty[T]

  def inOrder(buffer: ListBuffer[T]) = {
    buffer.++=(childs)
  }

  def +=(elem: T): this.type = {
    childs = childs.+:(elem)
    this
  }

  def ++=(elems: TraversableOnce[T]): this.type = {
    elems.foreach(+=)
    this
  }

}

trait SortedNode[T <: Ordered[T]] extends Node[T] {

  private def sort_insert(item: T, items: List[T]): List[T] = items match {
    case x :: xs if x < item => x :: sort_insert(item, xs)
    case xs => item :: xs
  }

  override def +=(elem: T): this.type = {
    childs = sort_insert(elem, childs)
    this
  }

}
