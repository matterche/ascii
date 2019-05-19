package ascii

trait TestData {
  val imageRaw = """{"sha256":"09569552730cb0628e0bc1f5d8039f8e6abefec920be8f88fc8f9fb5b9fcf0b5","size":664,"chunk_size":256}"""

  val chunk0Raw =
    """{"id":0,"size":800,"data":"                                                                                                                                  8\"\"\"\"8\neeee eeeee eeeee eeeee eeeee  eeeee eeeee eeeee  eeee eeeee e  eeee eeeee eeee eeee      eeee e   e eeee eeeee  eeee eeeeeee      8    8 eeeee e   e eeee eeee eeeee eeeee eeeee      eeee e   e eeeee  eeee e   e eeeee       eeeee  eeee eeeee eeeee  eeee eeeee eeeee e  ee   e eeee\n8  8 8  88 8   8   8   8   8  8   8 8   8 8   8  8    8   8 8  8    8   8 8  8 8         8  8 8   8 8    8   8  8    8  8  8      8eeee8   8   8   8 8    8  8 8   8   8   8   8      8    8   8 8   8  8    8   8 8   8       8   8  8      8   8   8  8    8   8   8   8  88   8 8\n8e   8   8 8e  8   8e  8eee8e 8eee8 8e    8eee8e 8eee 8e  8 8e 8eee 8e  8 8e   8eee eeee 8e   8eee8 8eee 8"}"""

  val chunk1Raw =
    """{"id":1,"size":732,"data":"eee8e 8eee 8e 8  8 eeee 88   8   8e  8eee8 8eee 8e   8eee8   8e  8eee8 eeee 8eee 8e  8 8eee8e 8eee 8e  8 8eee8e eeee 8eee8e 8eee   8e  8eee8e 8eee 8eee8   8e  8e 88  e8 8eee\n88   8   8 88  8   88  88   8 88  8 88 \"8 88   8 88   88  8 88 88   88  8 88   88        88   88  8 88   88   8 88   88 8  8      88   8   88  88  8 88   88   88  8   88  88  8      88   88  8 88   8 88   88  8 88   8      88   8 88     88  88   8 88   88  8   88  88  8  8  88\n88e8 8eee8 88  8   88  88   8 88  8 88ee8 88   8 88ee 88ee8 88 88ee 88  8 88e8 88ee      88e8 88  8 88ee 88   8 88ee 88 8  8      88   8   88  88  8 88ee 88e8 88  8   88  88  8      88   88ee8 88   8 88   88ee8 88   8      88   8 88ee   88  88   8 88ee 88  8   88  88  8ee8  88ee\n"}"""
}

object TestData extends TestData
