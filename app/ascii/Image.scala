package ascii

import scala.collection.mutable.ArrayBuffer

case class Image(sha256: String, size: Int, chunkSize: Int) {

  var chunks: ArrayBuffer[Chunk] = ArrayBuffer()

  // TODO: choose more idomatic approach by introducing immutability?
  def insertChunk(chunk: Chunk): Image = {
    chunks += chunk
    chunks = chunks.sortBy(chunk => chunk.id)
    this
  }

  def findChunk(id: Int): Option[Chunk] = chunks.find(chunk => chunk.id == id)
}

object Image {
  def from(imageDTO: ImageDTO): Image = Image(imageDTO.sha256, imageDTO.size, imageDTO.chunkSize)
}

case class Chunk(id: Int, size: Int, data: String)

object Chunk {
  def from(imageChunkDTO: ChunkDTO) = Chunk(imageChunkDTO.id, imageChunkDTO.size, imageChunkDTO.data)
}
