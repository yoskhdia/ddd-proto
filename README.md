This prototype is based https://github.com/ehalpern/sandbox

```
brew install sbt
mkdir ddd-proto
cd ddd-proto
git clone https://github.com/juan62/ddd-proto.git
sbt run
curl 'http://localhost:9080/echo?msg=foo'
curl 'http://localhost:9080/routing?id=123'
```

# Clean Architecture

http://qiita.com/kondei/items/41c28674c1bfd4156186
