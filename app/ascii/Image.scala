package ascii

import scala.collection.mutable.ArrayBuffer

case class Image(sha256: String, size: Int, chunkSize: Int, chunks: ArrayBuffer[Chunk])

object Image {
  def from(imageDTO: ImageDTO): Image = Image(imageDTO.sha256, imageDTO.size, imageDTO.chunkSize, ArrayBuffer())
}

case class Chunk(id: Int, size: Int, data: String)

object Chunk {
  def from(imageChunkDTO: ChunkDTO) = Chunk(imageChunkDTO.id, imageChunkDTO.size, imageChunkDTO.data)
}
