package be.wegenenverkeer.atom

import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer

/**
 * An in memory feedstore. This implementation is very inefficient and should only be used for demo or test purposes
 *
 * @param feedName the name of the feed
 * @param urlBuilder responsible for creating URL's in an Atom feed
 * @param title the optional title of the feed
 * @param contentType the content type of the entries in the feed
 * @tparam T the type for the content of the generated feed
 */
class MemoryFeedStore[T](feedName: String,
                         urlBuilder: UrlBuilder,
                         title : Option[String],
                         contentType: String)
  extends AbstractFeedStore[T](feedName, title, urlBuilder) {

  def this(feedName: String,
           baseUrl: Url,
           title: Option[String],
           contentType: String = "text/plain") =
    this(feedName, MemoryFeedStore.newUrlBuilder(baseUrl, feedName), title, contentType)

  val entries: ListBuffer[Entry[T]] = new ListBuffer[Entry[T]]

  override val minId = 0L

  override def maxId = entries.size + 1

  override def push(it: Iterable[T]) = {
    val timestamp: DateTime = new DateTime()
    it foreach { entry =>
      push(generateEntryID(), entry, new DateTime())
    }
  }

  override def push(uuid: String, entry: T): Unit = {
    push(uuid, entry, new DateTime())
  }

  private def push(uuid: String, entry: T, timestamp: DateTime): Unit = {
    entries append Entry(uuid, timestamp, Content(entry, ""), Nil)
  }


  /**
   * @param start the start entry
   * @param pageSize the number of entries to return
   * @return pageSize entries starting from start
   */
  override def getFeedEntries(start: Long, pageSize: Int, forward: Boolean): List[FeedEntry] = {
    if (forward)
      entriesWithIndex.dropWhile(_.sequenceNr < start).take(pageSize).toList
    else
      entriesWithIndex.takeWhile(_.sequenceNr <= start).toList.reverse.take(pageSize)
  }

  def entriesWithIndex: ListBuffer[FeedEntry] = {
    entries.zipWithIndex.map { case (entry, index) =>
      FeedEntry(index + 1.toLong, entry)
    }
  }

  /**
   * @return the total number of entries in the feed
   */
  override def getNumberOfEntriesLowerThan(sequenceNr: Long, inclusive: Boolean = true): Long = {
    if (inclusive)
      entriesWithIndex.count(_.sequenceNr <= sequenceNr)
    else
      entriesWithIndex.count(_.sequenceNr < sequenceNr)
  }

  //select * from entries order by id desc limit L
  override def getMostRecentFeedEntries(count: Int): List[FeedEntry] = {
    entriesWithIndex.toList.reverse.take(count)
  }



}

object MemoryFeedStore {

  def newUrlBuilder(baseUrl:Url, feedName:String) = new UrlBuilder {

    override def base: Url = baseUrl

    override def collectionLink: Url = ???
  }
}
