package collection

class SortedUniqArray[E <: Comparable[E]](capacity: Int) extends datastruct.array.SortedUniqArray[E](capacity) {
  def +(e: E): E = insert(e)

  /**
   * @param e
   * @return 如果找到了就返回 e 所在的下标，否则就返回第一个大于 e 的位置
   */
  def find(e: E): (Boolean, Int) = {
    val index = binarySearch(e)
    (index.isExist, index.index())
  }
}
