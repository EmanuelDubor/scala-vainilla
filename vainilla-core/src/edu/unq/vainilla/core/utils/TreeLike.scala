package edu.unq.vainilla.core.utils

import scala.collection.mutable.ListBuffer

trait TreeLike[T <: TreeLike[T]] {

  var parent: Option[T] = None

  def flatten: List[T] = {
    val buffer = ListBuffer.empty[T]
    inOrder(buffer)
    buffer.toList
  }

  def inOrder(buffer: ListBuffer[T]): Unit

}

trait TreeLeaf[T <: TreeLike[T]] extends TreeLike[T] {
  def inOrder(buffer: ListBuffer[T]) = buffer.+=(this.asInstanceOf[T])
}

trait TreeNode[T <: TreeLike[T]] extends TreeLike[T] {
  var childs = List.empty[T]

  def inOrder(buffer: ListBuffer[T]) = {
    buffer.++=(childs)
  }

  def +=(elem: T): this.type = {
    elem.parent = Some(this.asInstanceOf[T])
    childs = childs.+:(elem)
    this
  }

  def ++=(elems: Traversable[T]): this.type = {
    elems.foreach(+=)
    this
  }

}

trait SortedTreeNode[T <: Ordered[T] with TreeLike[T]] extends TreeNode[T] {

  private def sort_insert(item: T, items: List[T]): List[T] = items match {
    case x :: xs if x < item => x :: sort_insert(item, xs)
    case xs => item :: xs
  }

  def resort = childs = childs.sorted

  override def +=(elem: T): this.type = {
    childs = sort_insert(elem, childs)
    this
  }

  override def ++=(elems: Traversable[T]): this.type = {
    childs = childs.++:(elems)
    resort
    this
  }
}
