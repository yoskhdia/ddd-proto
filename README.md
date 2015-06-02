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

上の層は下の層のみに依存するよう実装する（単方向の依存）。
usecase層辺りから、インスタンス化をDIに委ねる必要が出てくる。

* external  
外部と相互作用するための層。ドライバやフレームワーク
* adapter  
外部との具体的なつながりを実装する。DBへのアクセスやデータ変換（俗にいうコントローラ）
* usecase  
ビジネスロジックを実現し、ハブの役割を果たす。ユースケースの実装（具象）
* contract  
ドメインモデルをどう扱うかを定める。ユースケースやリポジトリのインタフェースを定義（抽象）
* domain  
ビジネスドメインモデル

* util  
上記のような層に属さないユーティリティ

# DI

クリーンアーキテクチャの各層において、単方向への依存を守るためにDIを用いる。

## 代表的な使い方

https://github.com/codingwell/scala-guice

http://qiita.com/opengl-8080/items/6fb69cd2493e149cac3a

インタフェースに対して、どの具象クラスをバインドするかは、ScalaModuleをMix-Inしたモジュールを定義して設定する。

```scala
class MyModule extends ScalaModule
{
  def configure
  {
    bind[UserRepository].to[UserRepositoryImpl]
  }
}
```

* コンストラクタインジェクション  
```scala
import javax.inject.Inject
class MyClass @Inject()(userReporsitory: UserRepository)
{
  // ...
}
```

* フィールドインジェクション  
```scala
import javax.inject.Inject
class MyClass
{
  @Inject var userReporsitory: UserRepository = _
}
```

* メソッドインジェクション  
多くの場合、上記で事足りるので、使う機会はほぼ無いと思う。

## 注意点

* valはJavaだとfinal扱いなので、インジェクションを行うことができない
```scala
class MyClass
{
  @inject val userRepository: UserRepository = null
}
```
* フィールドにInjectしたオブジェクトを使ってフィールドを初期化してはいけない  
```scala
class MyClass
{
  @Inject var userRepository: UserRepository = _
  val defaultUser = userRepository.find(0)
}
```

