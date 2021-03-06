package ascii

import scala.collection.mutable.ArrayBuffer

case class Image(sha256: String, size: Int, chunkSize: Int) {

  private var chunksInternal: ArrayBuffer[Chunk] = new ArrayBuffer((size / chunkSize) + 1)

  def insertChunk(chunk: Chunk): Image = {
    chunksInternal += chunk
    chunksInternal = chunksInternal.sortBy(chunk => chunk.id)
    this
  }

  def findChunk(id: Int): Option[Chunk] = chunksInternal.find(chunk => chunk.id == id)

  def download: String = chunksInternal.map(chunk => chunk.data).mkString("")

  def chunks: List[Chunk] = chunksInternal.toList
}

object Image {
  def from(imageDTO: ImageDTO): Image = Image(imageDTO.sha256, imageDTO.size, imageDTO.chunkSize)
}

case class Chunk(id: Int, size: Int, data: String)

object Chunk {
  def from(imageChunkDTO: ChunkDTO) = Chunk(imageChunkDTO.id, imageChunkDTO.size, imageChunkDTO.data)
}
