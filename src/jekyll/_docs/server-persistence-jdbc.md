---
layout: docs
title: Relational database
page_title: Server - JDBC
prev_section: server-scala
next_section: server-persistence-mongo
permalink: /docs/server-persistence-jdbc/
---

The `atomium-server-jdbc` module provides a feed store implementation that stores the feeds in a relational database.

## Add dependency

### Maven

{% highlight xml %}
<dependency>
    <groupId>be.wegenenverkeer</groupId>
    <artifactId>atomium-server-jdbc</artifactId>
    <version>{{site.version}}</version>
</dependency>
{% endhighlight %}

### SBT

{% highlight scala %}
libraryDependencies += "be.wegenenverkeer" % "atomium-server-jdbc" % "{{site.version}}"
{% endhighlight %}

## TODO

- configuration
- database structure

The JDBC feedstore uses the following tables:

1 shared table for all feeds, called FEED which stores information about each feed provided by
the system/application. This table has three columns:
* "id" => an auto-incrementing unique id of each feed
* "name" => the name of the feed
* "title" => the optional title of the feed

a table containing all the entries for each feed provided by the system. Each feed entries table is automatically created
 when a new feed is started. The table name is "FEED_ENTRIES_%id" where %id is the unique id of the feed.
Each entries table has the following columns:
* "id" => an auto-incrementing unique id of each entry in a specific feed
* "uuid" => a UUID generated by the server for uniquely referencing an entry
* "value" => a string containing the serialized feed entry
* "timestamp" => timestamp when the entry was added to the feed



